package ute.shop.controller.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.Notification;
import ute.shop.entity.User;
import ute.shop.service.impl.NotificationServiceImpl;

@WebServlet(urlPatterns = { "/admin/home" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		User admin = (User) req.getAttribute("account");
		if (admin == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		// set att
		List<Notification> notis = new NotificationServiceImpl().getUnreadByUserId(admin.getUserId());
		req.setAttribute("notifications", notis);

		// Lấy tham số page
		String page = req.getParameter("page");

		if (page != null) {
			switch (page) {

			case "users":
				resp.sendRedirect(req.getContextPath() + "/admin/users");
				return;
			case "products":
				resp.sendRedirect(req.getContextPath() + "/admin/products");
				return;
			case "categories":
				resp.sendRedirect(req.getContextPath() + "/admin/categories");
				return;
			case "shops":
				resp.sendRedirect(req.getContextPath() + "/admin/shops");
				return;
			case "orders":
				resp.sendRedirect(req.getContextPath() + "/admin/orders");
				return;
			case "carriers":
				resp.sendRedirect(req.getContextPath() + "/admin/carriers");
				return;
			case "complaints":
				resp.sendRedirect(req.getContextPath() + "/admin/complaints");
				return;
			case "revenue":
				resp.sendRedirect(req.getContextPath() + "/admin/revenue");
				return;
			case "promotions":
				resp.sendRedirect(req.getContextPath() + "/admin/promotions");
				return;
			case "stats":
				resp.sendRedirect(req.getContextPath() + "/admin/stats");
				return;
			case "contacts":
				resp.sendRedirect(req.getContextPath() + "/admin/contacts");
				return;
			case "settings":
				resp.sendRedirect(req.getContextPath() + "/admin/settings");
				return;
			default:
				resp.sendRedirect(req.getContextPath() + "/admin/home");
				return;
			}
		}

		// Nếu không có page → mặc định Dashboard
		resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
		return;
	}
}
