package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.dto.MessageDTO;
import ute.shop.entity.*;
import ute.shop.service.impl.*;
import ute.shop.service.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

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

				User admin = (User) req.getAttribute("account");
				if (admin == null) {
					admin = (User) req.getSession().getAttribute("account");
				}
				req.setAttribute("account", admin);

				// Lấy dữ liệu gốc từ database
				Complaint complaint = complaintService.findById(complaintId);
				List<ComplaintMessage> originalMessages = msgService.findByComplaintId(complaintId);

				// Chuyển đổi sang DTO
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				List<MessageDTO> messageDTOs = originalMessages.stream()
						.map(msg -> new MessageDTO((long) msg.getSender().getUserId(), msg.getSender().getUsername(),
								msg.getSender().getAvatar(), msg.getMessageType().name(), msg.getContent(),
								msg.getOriginalFilename(), sdf.format(msg.getCreatedAt())))
						.collect(Collectors.toList());

				req.setAttribute("complaint", complaint);
				req.setAttribute("messages", messageDTOs);
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

		try {
			if (uri.endsWith("/edit")) {
				int id = Integer.parseInt(req.getParameter("complaintId"));
				String status = req.getParameter("status");

				Complaint c = complaintService.findById(id);
				c.setStatus(status);

				complaintService.update(c);
				resp.sendRedirect(req.getContextPath() + "/admin/complaints?message=EditSuccess");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath() + "/admin/complaints?error=errorPost");
		}
	}
}