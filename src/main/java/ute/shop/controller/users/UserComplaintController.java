package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.impl.ComplaintServiceImpl;
import ute.shop.service.impl.ComplaintMessageServiceImpl;
import ute.shop.service.impl.OrderServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID; // Thêm import cho UUID

@WebServlet(urlPatterns = { "/user/complaints", "/user/complaints/add" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class UserComplaintController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
	private final ComplaintMessageServiceImpl msgService = new ComplaintMessageServiceImpl();
	private final OrderServiceImpl orderService = new OrderServiceImpl();

	private static final String UPLOAD_DIR = "assets/images/complaints";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI();
		User user = (User) req.getAttribute("account");
		if (user == null) {
			HttpSession session = req.getSession(false);
			if (session != null) {
				user = (User) session.getAttribute("account");
			}
			if (user == null) {
				resp.sendRedirect(req.getContextPath() + "/login");
				return;
			}
		}

		if (uri.endsWith("/complaints")) {
			req.setAttribute("complaints", complaintService.findByUserId(user.getUserId()));
			req.getRequestDispatcher("/views/user/complaints/dashboard.jsp").forward(req, resp);
		} else if (uri.endsWith("/add")) {
			int orderId = Integer.parseInt(req.getParameter("orderId"));
			Order order = orderService.getById(orderId);
			req.setAttribute("order", order);
			req.getRequestDispatcher("/views/user/complaints/add.jsp").forward(req, resp);
		} else if (uri.endsWith("/chat")) {

			int complaintId = Integer.parseInt(req.getParameter("id"));

			Complaint complaint = complaintService.findById(complaintId);

			// Kiểm tra quyền truy cập

			if (complaint == null) {

				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy khiếu nại");

				return;

			}

			// User chỉ được xem complaint của mình

			if (complaint.getUser().getUserId() != user.getUserId()) {

				resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập");

				return;

			}

			// Lấy danh sách tin nhắn

			List<ComplaintMessage> messages = msgService.findByComplaintId(complaintId);

			req.setAttribute("complaint", complaint);

			req.setAttribute("messages", messages);

			req.getRequestDispatcher("/views/user/complaints/chat.jsp").forward(req, resp);

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		User user = (User) req.getAttribute("account");

		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		try {
			int orderId = Integer.parseInt(req.getParameter("orderId"));
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			Part filePart = req.getPart("attachment");

			String attachmentPath = null; // Biến để lưu đường dẫn file vào CSDL
			String originalFileName = filePart.getSubmittedFileName();

			// Chỉ xử lý nếu người dùng có tải file lên
			if (originalFileName != null && !originalFileName.isEmpty()) {
				// Lấy đường dẫn vật lý trên server
				String realPath = getServletContext().getRealPath("/") + UPLOAD_DIR;
				File uploadDir = new File(realPath);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}

				// Tạo tên file duy nhất bằng UUID, giống hệt Admin
				String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
				String uniqueFileName = UUID.randomUUID().toString() + extension;

				// Lưu file vào đường dẫn vật lý
				String filePath = realPath + File.separator + uniqueFileName;
				filePart.write(filePath);

				// Tạo đường dẫn tương đối để lưu vào CSDL
				attachmentPath = UPLOAD_DIR + "/" + uniqueFileName;
			}

			// Tạo đối tượng complaint mới
			Complaint complaint = new Complaint();
			complaint.setUser(user);
			complaint.setOrder(orderService.getById(orderId));
			complaint.setTitle(title);	
			complaint.setContent(content);
			complaint.setStatus("Chờ xử lý");
			complaint.setCreatedAt(new Date());
			complaint.setAttachment(attachmentPath); // Lưu đường dẫn tương đối

			complaintService.insert(complaint);

			resp.sendRedirect(req.getContextPath() + "/user/complaints");

		} catch (Exception e) {
			e.printStackTrace();
			// Có thể chuyển hướng đến trang lỗi
			resp.sendRedirect(req.getContextPath() + "/user/complaints?error=true");
		}
	}
}