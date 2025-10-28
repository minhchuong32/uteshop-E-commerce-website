package ute.shop.websocket;

import com.google.gson.Gson;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import ute.shop.dto.MessageDTO;
import ute.shop.entity.Complaint;
import ute.shop.entity.ComplaintMessage;
import ute.shop.entity.Notification;
import ute.shop.entity.User;
import ute.shop.service.*;
import ute.shop.service.impl.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/chat/{complaintId}/{userId}")
public class ChatServerEndpoint {

	private static final Map<Integer, Set<Session>> rooms = new ConcurrentHashMap<>();
	private static final IComplaintMessageService msgService = new ComplaintMessageServiceImpl();
	private static final IComplaintService complaintService = new ComplaintServiceImpl();
	private static final IUserService userService = new UserServiceImpl();
	private static final INotificationService notiService = new NotificationServiceImpl();
	private static final Gson gson = new Gson();

	@OnOpen
	public void onOpen(Session session, @PathParam("complaintId") int complaintId, @PathParam("userId") int userId) throws IOException {
		Complaint complaint = complaintService.findById(complaintId);
		Optional<User> userOptional = userService.getUserById(userId);

		boolean isAuthorized = false;
		if (complaint != null && userOptional.isPresent()) {
			User user = userOptional.get();
			if ("Admin".equalsIgnoreCase(user.getRole()) || complaint.getUser().getUserId() == userId) {
				isAuthorized = true;
			}
		}

		if (!isAuthorized) {
			System.err.println("Unauthorized WebSocket access attempt by userId: " + userId + " for complaintId: " + complaintId);
			session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Unauthorized"));
			return;	
		}

		rooms.computeIfAbsent(complaintId, k -> new CopyOnWriteArraySet<>()).add(session);
		session.getUserProperties().put("complaintId", complaintId);
		session.getUserProperties().put("userId", userId);
		System.out.println("WebSocket opened for user " + userId + " in complaint " + complaintId);
	}

	@OnMessage
	public void onMessage(String jsonMessage, Session session) {
		int complaintId = (int) session.getUserProperties().get("complaintId");
		int senderId = (int) session.getUserProperties().get("userId");

		Complaint complaint = complaintService.findById(complaintId);
		Optional<User> senderOptional = userService.getUserById(senderId);

		if (complaint == null || senderOptional.isEmpty()) {
			System.err.println("Complaint or Sender not found!");
			return;
		}

		User sender = senderOptional.get();
		MessageDTO receivedMsg = gson.fromJson(jsonMessage, MessageDTO.class);

		ComplaintMessage newMessage = new ComplaintMessage();
		newMessage.setComplaint(complaint);
		newMessage.setSender(sender);
		newMessage.setContent(receivedMsg.getContent());
		newMessage.setMessageType(ComplaintMessage.MessageType.valueOf(receivedMsg.getType().toUpperCase())); 
		newMessage.setOriginalFilename(receivedMsg.getOriginalFilename());

		newMessage = msgService.insert(newMessage);

		createAndSaveNotification(sender, complaint);

		//  Tạo đối tượng DTO để broadcast, đảm bảo cấu trúc nhất quán
		MessageDTO messageToSend = new MessageDTO(
				(long) sender.getUserId(),
				sender.getUsername(),
				sender.getAvatar(),
				newMessage.getMessageType().name(), // Lấy lại từ Entity đã lưu để đảm bảo đúng
				newMessage.getContent(),
				newMessage.getOriginalFilename(),
				new SimpleDateFormat("dd/MM/yyyy HH:mm").format(newMessage.getCreatedAt())
		);
		String finalJsonMessage = gson.toJson(messageToSend);

		broadcast(complaintId, finalJsonMessage);
	}

	@OnClose
	public void onClose(Session session) {
		Integer complaintId = (Integer) session.getUserProperties().get("complaintId");
		if (complaintId != null) {
			Set<Session> roomSessions = rooms.get(complaintId);
			if (roomSessions != null) {
				roomSessions.remove(session);
				if (roomSessions.isEmpty()) {
					rooms.remove(complaintId);
				}
			}
		}
		System.out.println("WebSocket closed for session: " + session.getId());
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		System.err.println("Error on session " + session.getId());
		throwable.printStackTrace();
	}

	private void broadcast(int complaintId, String message) {
		Set<Session> roomSessions = rooms.get(complaintId);
		if (roomSessions != null) {
			roomSessions.forEach(session -> {
				if (session.isOpen()) {
					try {
						session.getBasicRemote().sendText(message);
					} catch (IOException e) {
						System.err.println("Failed to send message to session " + session.getId());
						e.printStackTrace();
					}
				}
			});
		}
	}

	/**
	 * Tạo và lưu thông báo vào CSDL cho người nhận. - Nếu Admin gửi, thông báo sẽ
	 * được gửi đến người dùng tạo khiếu nại. - Nếu người dùng gửi, thông báo sẽ
	 * được gửi đến tất cả Admin.
	 * 
	 * @param sender    Người gửi tin nhắn
	 * @param complaint Khiếu nại liên quan
	 */
	private void createAndSaveNotification(User sender, Complaint complaint) {
		List<User> recipients = new ArrayList<>();
		String notificationMessage;

		// Xác định người nhận và nội dung thông báo
		if ("Admin".equalsIgnoreCase(sender.getRole())) {
			// Admin gửi -> thông báo cho người dùng
			recipients.add(complaint.getUser());
			notificationMessage = "Admin đã phản hồi khiếu nại #" + complaint.getComplaintId() + " của bạn.";
		} else {
			// Người dùng gửi -> thông báo cho tất cả Admin
			recipients.addAll(userService.getUsersByRole("ADMIN"));
			notificationMessage = "Người dùng '" + sender.getUsername() + "' đã gửi tin nhắn trong khiếu nại #"
					+ complaint.getComplaintId() + ".";
		}

		// Tạo và lưu thông báo cho từng người nhận
		for (User recipient : recipients) {
			// Đảm bảo không tự gửi thông báo cho chính mình
			if (recipient.getUserId() == sender.getUserId()) {
				continue;
			}

			Notification notification = Notification.builder().user(recipient) // Người sẽ nhận thông báo
					.message(notificationMessage).relatedComplaint(complaint).read(false) // Mặc định là chưa đọc
					.build();
			notiService.insert(notification);
		}
	}
}