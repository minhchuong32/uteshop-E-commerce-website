package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.impl.*;
import ute.shop.service.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/admin/complaints", "/admin/complaints/edit", "/admin/complaints/delete",
		"/admin/complaints/chat" })
public class ComplaintController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IComplaintAnalyticsService service = new ComplaintAnalyticsServiceImpl();
	private final IComplaintService complaintService = new ComplaintServiceImpl();
	private final IComplaintMessageService msgService = new ComplaintMessageServiceImpl();

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
			req.setAttribute("view", "/views/admin/complaints/list.jsp");
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

	}
}
