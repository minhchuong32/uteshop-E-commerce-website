package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.impl.ComplaintServiceImpl;
import ute.shop.service.impl.OrderServiceImpl;


import java.io.File;
import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = { "/user/complaints", "/user/complaints/add" })
@MultipartConfig
public class UserComplaintController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
	private final OrderServiceImpl orderService = new OrderServiceImpl();


	private static final String UPLOAD_DIR = "/uploads/complaints";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI();

		// Danh sách khiếu nại của user
		if (uri.endsWith("/complaints")) {
			
			User user = (User) req.getAttribute("account");
			if (user == null) {
				resp.sendRedirect(req.getContextPath() + "/login");
				return;
			}

			req.setAttribute("complaints", complaintService.findByUserId(user.getUserId()));

			req.getRequestDispatcher("/views/user/complaints/dashboard.jsp").forward(req, resp);
		}

		// Form gửi khiếu nại
		else if (uri.endsWith("/add")) {
			int orderId = Integer.parseInt(req.getParameter("orderId"));
			Order order = orderService.getById(orderId);
			req.setAttribute("order", order);
			req.getRequestDispatcher("/views/user/complaints/add.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		User user = (User) req.getAttribute("account");
		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		int orderId = Integer.parseInt(req.getParameter("orderId"));
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		Part filePart = req.getPart("attachment");

		// Tạo thư mục upload nếu chưa có
		String realPath = getServletContext().getRealPath(UPLOAD_DIR);
		File uploadDir = new File(realPath);
		if (!uploadDir.exists())
			uploadDir.mkdirs();

		// Lưu file
		String fileName = null;
		if (filePart != null && filePart.getSize() > 0) {
			fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
			filePart.write(realPath + File.separator + fileName);
		}

		// Tạo complaint
		Complaint complaint = new Complaint();
		complaint.setUser(user);
		complaint.setOrder(orderService.getById(orderId));
		complaint.setTitle(title);
		complaint.setContent(content);
		complaint.setStatus("Chờ xử lý");
		complaint.setCreatedAt(new Date());
		complaint.setAttachment(fileName);

		complaintService.insert(complaint);

		resp.sendRedirect(req.getContextPath() + "/user/complaints");
	}
}
