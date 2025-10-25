package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.impl.*;
import ute.shop.service.*;

import java.io.IOException;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(urlPatterns = { "/admin/complaints", "/admin/complaints/edit", "/admin/complaints/delete",
		"/admin/complaints/chat" })
public class ComplaintController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IComplaintAnalyticsService service = new ComplaintAnalyticsServiceImpl();
	private final IComplaintService complaintService = new ComplaintServiceImpl();
	private final IComplaintMessageService msgService = new ComplaintMessageServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		try {
			req.setAttribute("totalComplaints", service.countAll());
			req.setAttribute("statusData", service.countByStatus());
			req.setAttribute("monthData", service.countByMonth());
			req.setAttribute("topUsers", service.topUsers(5));

			if (uri.endsWith("/complaints")) {
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
				 resp.sendRedirect(req.getContextPath() + "/admin/complaints?message=DelSuccess");
			} else if (uri.endsWith("/chat")) {
				int complaintId = Integer.parseInt(req.getParameter("id"));
				Complaint complaint = complaintService.findById(complaintId);
				List<ComplaintMessage> messages = msgService.findByComplaintId(complaintId);

				req.setAttribute("complaint", complaint);
				req.setAttribute("messages", messages);
				req.setAttribute("page", "complaints");
				req.setAttribute("view", "/views/admin/complaints/chat.jsp");
				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath() + "/admin/complaints?error=errorGet");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();

		if (uri.endsWith("/edit")) {
			try {
				int id = Integer.parseInt(req.getParameter("complaintId"));
				String status = req.getParameter("status");

				Complaint c = complaintService.findById(id);
				c.setStatus(status);

				complaintService.update(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
			resp.sendRedirect(req.getContextPath() + "/admin/complaints?message=EditSuccess");
		}
	}
}