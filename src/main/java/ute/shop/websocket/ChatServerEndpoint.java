package ute.shop.websocket;

import com.google.gson.Gson;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import ute.shop.entity.Complaint;
import ute.shop.entity.ComplaintMessage;
import ute.shop.entity.Notification;
import ute.shop.entity.User;
import ute.shop.service.IComplaintMessageService;
import ute.shop.service.IComplaintService;
import ute.shop.service.INotificationService;
import ute.shop.service.IUserService;
import ute.shop.service.impl.ComplaintMessageServiceImpl;
import ute.shop.service.impl.ComplaintServiceImpl;
import ute.shop.service.impl.NotificationServiceImpl;
import ute.shop.service.impl.UserServiceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/chat/{complaintId}/{userId}")
public class ChatServerEndpoint {

	// Sử dụng CopyOnWriteArraySet, một lựa chọn tốt cho kịch bản đọc nhiều, ghi ít
	private static final Map<Integer, Set<Session>> rooms = new ConcurrentHashMap<>();

	// Chuyển sang static final để chỉ khởi tạo 1 lần duy nhất
	private static final IComplaintMessageService msgService = new ComplaintMessageServiceImpl();
	private static final IComplaintService complaintService = new ComplaintServiceImpl();
	private static final IUserService userService = new UserServiceImpl();
	private static final Gson gson = new Gson();
	private static final INotificationService notiService = new NotificationServiceImpl();

	@OnOpen
	public void onOpen(Session session, @PathParam("complaintId") int complaintId, @PathParam("userId") int userId)
			throws IOException {
		// ================== BƯỚC KIỂM TRA BẢO MẬT VỚI JWT ==================
		Complaint complaint = complaintService.findById(complaintId);
		Optional<User> userOptional = userService.getUserById(userId);

		boolean isAuthorized = false;
		if (complaint != null && userOptional.isPresent()) {
			User user = userOptional.get();
			// Cho phép kết nối nếu:
			// 1. Người dùng có vai trò là "Admin"
			// 2. Hoặc, ID của người dùng trùng với ID của người tạo khiếu nại
			if ("Admin".equals(user.getRole()) || complaint.getUser().getUserId() == userId) {
				isAuthorized = true;
			}
		}

		// Nếu không có quyền, đóng kết nối ngay lập tức
		if (!isAuthorized) {
			System.err.println(
					"Unauthorized WebSocket access attempt by userId: " + userId + " for complaintId: " + complaintId);
			session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Unauthorized"));
			return;
		}
		// Dùng CopyOnWriteArraySet cho thread-safe và hiệu năng tốt khi broadcast
		rooms.computeIfAbsent(complaintId, k -> new CopyOnWriteArraySet<>()).add(session);
		session.getUserProperties().put("complaintId", complaintId);
		session.getUserProperties().put("userId", userId);
		System.out.println("WebSocket opened for user " + userId + " in complaint " + complaintId);
	}

	@OnMessage
	public void onMessage(String jsonMessage, Session session) {
		// Lấy thông tin từ session
		int complaintId = (int) session.getUserProperties().get("complaintId");
		int senderId = (int) session.getUserProperties().get("userId");

		// Lấy thông tin cần thiết từ DB
		Complaint complaint = complaintService.findById(complaintId);
		Optional<User> senderOptional = userService.getUserById(senderId); // Đổi tên biến cho rõ ràng

		// Kiểm tra đúng cách: complaint có tồn tại và Optional có chứa User bên trong
		if (complaint == null || !senderOptional.isPresent()) {
			System.err.println("Complaint or Sender not found!");
			return;
		}

		User sender = senderOptional.get();
		// Parse chuỗi JSON nhận được từ client thành object MessageDTO
		MessageDTO receivedMsg = gson.fromJson(jsonMessage, MessageDTO.class);

		// Lưu tin nhắn vào Database, bao gồm cả loại tin nhắn (TEXT/IMAGE)
		ComplaintMessage newMessage = ComplaintMessage.builder().complaint(complaint).sender(sender)
				.content(receivedMsg.getContent()) // Nội dung là text hoặc URL ảnh
				.messageType(receivedMsg.getType().toUpperCase()) // Lưu loại tin nhắn
				.originalFilename(receivedMsg.getOriginalFilename()).build();
		newMessage = msgService.insert(newMessage); // Lấy lại object để có timestamp

		// Tạo và lưu thông báo cho người nhận
		createAndSaveNotification(sender, complaint);

		// Chuẩn bị object DTO để gửi lại cho tất cả client trong phòng
		MessageDTO messageToSend = new MessageDTO(sender.getUserId(), sender.getUsername(), sender.getAvatar(),
				newMessage.getContent(), new SimpleDateFormat("dd/MM/yyyy HH:mm").format(newMessage.getCreatedAt()),
				newMessage.getMessageType(), // Gửi đi cả loại tin nhắn
				newMessage.getOriginalFilename());
		String finalJsonMessage = gson.toJson(messageToSend);

		// Gửi broadcast
		broadcast(complaintId, finalJsonMessage);
	}

	@OnClose
	public void onClose(Session session) {
		int complaintId = (int) session.getUserProperties().get("complaintId");
		Set<Session> roomSessions = rooms.get(complaintId);
		if (roomSessions != null) {
			roomSessions.remove(session);
			if (roomSessions.isEmpty()) {
				rooms.remove(complaintId);
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
			// Vì dùng CopyOnWriteArraySet, việc duyệt mảng rất nhanh và an toàn
			for (Session session : roomSessions) {
				if (session.isOpen()) {
					try {
						session.getBasicRemote().sendText(message);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
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

	// Tạo một lớp nhỏ (DTO) để biểu diễn dữ liệu tin nhắn
	// Điều này giúp code trong sáng và dễ quản lý hơn là dùng chuỗi JSON thủ công
	private static class MessageDTO {
		private int senderId;
		private String senderUsername;
		private String senderAvatar;
		private String content;
		private String createdAt;
		private String type;
		private String originalFilename;

		// Cập nhật constructor
		public MessageDTO(int senderId, String senderUsername, String senderAvatar, String content, String createdAt,
				String type, String originalFilename) {
			this.senderId = senderId;
			this.senderUsername = senderUsername;
			this.senderAvatar = senderAvatar;
			this.content = content;
			this.createdAt = createdAt;
			this.type = type;
			this.originalFilename = originalFilename;
		}

		public String getOriginalFilename() {
			return originalFilename;
		}

		// Getter cần thiết để Gson có thể parse từ JSON
		public String getContent() {
			return content;
		}

		public String getType() {
			return type;
		}
	}
}