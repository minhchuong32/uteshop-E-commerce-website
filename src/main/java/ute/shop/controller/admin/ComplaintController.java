package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.impl.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/admin/complaints", "/admin/complaints/edit", "/admin/complaints/delete",
		"/admin/complaints/chat" })
public class ComplaintController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ComplaintAnalyticsServiceImpl service = new ComplaintAnalyticsServiceImpl();
	private final ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
	private final ComplaintMessageServiceImpl msgService = new ComplaintMessageServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("totalComplaints", service.countAll());
		req.setAttribute("statusData", service.countByStatus());
		req.setAttribute("monthData", service.countByMonth());
		req.setAttribute("topUsers", service.topUsers(5));
		String uri = req.getRequestURI();

		if (uri.endsWith("/complaints")) {
			// Danh sách khiếu nại
			List<Complaint> list = complaintService.findAll();
			req.setAttribute("complaints", list);
			req.setAttribute("page", "complaints");
			req.setAttribute("view", "/views/admin/complaints/dashboard.jsp");
			req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
		} else if (uri.endsWith("/edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			Complaint c = complaintService.findById(id);
			req.setAttribute("complaint", c);
			req.setAttribute("page", "complaints");
			req.setAttribute("view", "/views/admin/complaints/edit.jsp");
			req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
		} else if (uri.endsWith("/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			complaintService.delete(id);
			resp.sendRedirect(req.getContextPath() + "/admin/complaints");
		} else if (uri.endsWith("/chat")) {
			// Trang chat khiếu nại
			int complaintId = Integer.parseInt(req.getParameter("id"));
			Complaint complaint = complaintService.findById(complaintId);
			List<ComplaintMessage> messages = msgService.findByComplaintId(complaintId);

			req.setAttribute("complaint", complaint);
			req.setAttribute("messages", messages);
			req.setAttribute("page", "complaints");
			req.setAttribute("view", "/views/admin/complaints/chat.jsp");
			req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI();

		// ==================== Cập nhật trạng thái khiếu nại ====================
		if (uri.endsWith("/complaints")) {
			int id = Integer.parseInt(req.getParameter("complaintId"));
			String status = req.getParameter("status");

			Complaint c = complaintService.findById(id);
			c.setStatus(status);
			complaintService.update(c);

			resp.sendRedirect(req.getContextPath() + "/admin/complaints");
		}

		// ==================== Gửi tin nhắn chat ====================
		else if (uri.endsWith("/chat")) {
			int complaintId = Integer.parseInt(req.getParameter("complaintId"));
			String content = req.getParameter("content");

			HttpSession session = req.getSession();
			User admin = (User) session.getAttribute("account");

			if (admin == null) {
				resp.sendRedirect(req.getContextPath() + "/login");
				return;
			}

			// Lấy complaint trước khi dùng
			Complaint complaint = complaintService.findById(complaintId);
			if (complaint == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy khiếu nại");
				return;
			}

			// Lưu tin nhắn mới
			ComplaintMessage msg = ComplaintMessage.builder().complaint(complaint).sender(admin).content(content)
					.build();
			msgService.insert(msg);

			// Gửi thông báo cho User
			Notification noti = Notification.builder().user(complaint.getUser()) // người gửi complaint sẽ nhận thông
																					// báo
					.message("Admin đã phản hồi khiếu nại #" + complaintId).relatedComplaint(complaint).build();

			new NotificationServiceImpl().insert(noti);

			resp.sendRedirect(req.getContextPath() + "/admin/complaints/chat?id=" + complaintId);
		}
	}
}
