package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.impl.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/user/chat", "/user/chat/send" })
public class UserChatController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
	private final ComplaintMessageServiceImpl msgService = new ComplaintMessageServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		User user = (User) req.getAttribute("account");
		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		int complaintId = Integer.parseInt(req.getParameter("complaintId"));
		Complaint complaint = complaintService.findById(complaintId);
		List<ComplaintMessage> messages = msgService.findByComplaintId(complaintId);

		req.setAttribute("complaint", complaint);
		req.setAttribute("messages", messages);

		req.getRequestDispatcher("/views/user/complaints/chat.jsp").forward(req, resp);
	}

}
