
package ute.shop.websocket;

import com.google.gson.Gson; // THAY ĐỔI: Import Gson
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import ute.shop.entity.Complaint;
import ute.shop.entity.ComplaintMessage;
import ute.shop.entity.User;
import ute.shop.service.IComplaintMessageService;
import ute.shop.service.IComplaintService;
import ute.shop.service.IUserService;
import ute.shop.service.impl.ComplaintMessageServiceImpl;
import ute.shop.service.impl.ComplaintServiceImpl;
import ute.shop.service.impl.UserServiceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/chat/{complaintId}/{userId}")
public class ChatServerEndpoint {

	//  Sử dụng CopyOnWriteArraySet, một lựa chọn tốt cho kịch bản đọc  nhiều, ghi ít
	private static final Map<Integer, Set<Session>> rooms = new ConcurrentHashMap<>();

	//  Chuyển sang static final để chỉ khởi tạo 1 lần duy nhất
	private static final IComplaintMessageService msgService = new ComplaintMessageServiceImpl();
	private static final IComplaintService complaintService = new ComplaintServiceImpl();
	private static final IUserService userService = new UserServiceImpl();
	private static final Gson gson = new Gson(); // Khởi tạo Gson một lần

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
		//  Dùng CopyOnWriteArraySet cho thread-safe và hiệu năng tốt khi broadcast
		rooms.computeIfAbsent(complaintId, k -> new CopyOnWriteArraySet<>()).add(session);
		session.getUserProperties().put("complaintId", complaintId);
		session.getUserProperties().put("userId", userId);
		System.out.println("WebSocket opened for user " + userId + " in complaint " + complaintId);
	}

	@OnMessage
	public void onMessage(String messageContent, Session session) {
		// Lấy thông tin từ session
		int complaintId = (int) session.getUserProperties().get("complaintId");
		int senderId = (int) session.getUserProperties().get("userId");

		//  Lấy thông tin cần thiết từ DB
		Complaint complaint = complaintService.findById(complaintId);
		Optional<User> senderOptional = userService.getUserById(senderId); // Đổi tên biến cho rõ ràng

		// Kiểm tra đúng cách: complaint có tồn tại và Optional có chứa User bên trong
		if (complaint == null || !senderOptional.isPresent()) {
			System.err.println("Complaint or Sender not found!");
			return;
		}

		User sender = senderOptional.get();
		//  Lưu tin nhắn vào Database (giống logic trong doPost cũ)
		ComplaintMessage newMessage = ComplaintMessage.builder().complaint(complaint).sender(sender)
				.content(messageContent).build();
		newMessage = msgService.insert(newMessage); // insert và lấy lại object để có createdAt

		//  Dùng DTO và Gson để tạo JSON một cách an toàn và sạch sẽ
		MessageDTO messageDTO = new MessageDTO(sender.getUserId(), sender.getUsername(), newMessage.getContent(),
				new SimpleDateFormat("dd/MM/yyyy HH:mm").format(newMessage.getCreatedAt()));
		String jsonMessage = gson.toJson(messageDTO);

		broadcast(complaintId, jsonMessage);
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

	// Tạo một lớp nhỏ (DTO) để biểu diễn dữ liệu tin nhắn
	// Điều này giúp code trong sáng và dễ quản lý hơn là dùng chuỗi JSON thủ công
	private static class MessageDTO {
		private final int senderId;
		private final String senderUsername;
		private final String content;
		private final String createdAt;

		public MessageDTO(int senderId, String senderUsername, String content, String createdAt) {
			this.senderId = senderId;
			this.senderUsername = senderUsername;
			this.content = content;
			this.createdAt = createdAt;
		}
	}
}