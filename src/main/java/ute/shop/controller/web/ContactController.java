package ute.shop.controller.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ute.shop.entity.Contact;
import ute.shop.entity.StoreSettings;
import ute.shop.entity.User;
import ute.shop.service.IContactService;
import ute.shop.service.IStoreSettingsService;
import ute.shop.service.impl.ContactServiceImpl;
import ute.shop.service.impl.StoreSettingsServiceImpl;

@WebServlet(urlPatterns = { "/web/contact" })
public class ContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	IContactService contactService = new ContactServiceImpl();
	IStoreSettingsService settingService = new StoreSettingsServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		StoreSettings store = settingService.getSettings();
		req.setAttribute("store", store);

		String address = (store != null && store.getAddress() != null && !store.getAddress().trim().isEmpty())
				? store.getAddress().trim()
				: "Ho Chi Minh City, Vietnam";

		String encoded = URLEncoder.encode(address, StandardCharsets.UTF_8.name());
		String mapEmbedUrl = "https://www.google.com/maps?q=" + encoded + "&output=embed";
		req.setAttribute("mapEmbedUrl", mapEmbedUrl);

		req.getRequestDispatcher("/views/web/contact.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession(false);
		User user = (session != null) ? (User) session.getAttribute("account") : null;

		if (user == null) {
			// Nếu chưa login thì redirect sang login
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		// Nếu đã login thì xử lý lưu contact
		String fullname = req.getParameter("fullname");
		String email = req.getParameter("email");
		String content = req.getParameter("message");

		Contact c = new Contact();
		c.setUser(user); // set user đã login
		c.setFullName(fullname);
		c.setEmail(email);
		c.setContent(content);

		boolean saved = contactService.insert(c);

		if (saved) {
			req.setAttribute("success", "Cảm ơn bạn đã liên hệ. Chúng tôi sẽ phản hồi sớm!");
		} else {
			req.setAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
		}

		StoreSettings store = settingService.getSettings();
		req.setAttribute("store", store);

		String address = (store != null && store.getAddress() != null && !store.getAddress().trim().isEmpty())
				? store.getAddress().trim()
				: "Ho Chi Minh City, Vietnam";

		String encoded = URLEncoder.encode(address, StandardCharsets.UTF_8.name());
		String mapEmbedUrl = "https://www.google.com/maps?q=" + encoded + "&output=embed";
		req.setAttribute("mapEmbedUrl", mapEmbedUrl);

		req.getRequestDispatcher("/views/web/contact.jsp").forward(req, resp);
	}
}
