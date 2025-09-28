package ute.shop.controller.users;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ute.shop.models.Contact;
import ute.shop.models.StoreSettings;
import ute.shop.models.User;
import ute.shop.service.IContactService;
import ute.shop.service.IStoreSettingsService;
import ute.shop.service.impl.ContactServiceImpl;
import ute.shop.service.impl.StoreSettingsServiceImpl;

@WebServlet(urlPatterns = { "/user/contact" })
public class ContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	IContactService contactService = new ContactServiceImpl();
	IStoreSettingsService settingService = new StoreSettingsServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy settings của cửa hàng
		StoreSettings store = settingService.getSettings();
		req.setAttribute("store", store);

		// Tạo URL cho Google Map theo địa chỉ trong StoreSettings
		String address = (store != null && store.getAddress() != null && !store.getAddress().trim().isEmpty())
				? store.getAddress().trim()
				: "Ho Chi Minh City, Vietnam"; // fallback nếu chưa có địa chỉ

		String encoded = URLEncoder.encode(address, StandardCharsets.UTF_8.name());
		String mapEmbedUrl = "https://www.google.com/maps?q=" + encoded + "&output=embed";
		req.setAttribute("mapEmbedUrl", mapEmbedUrl);
		req.getRequestDispatcher("/views/user/contact.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession(false);
		User user = (session != null) ? (User) session.getAttribute("account") : null;

		String fullname = req.getParameter("fullname");
		String email = req.getParameter("email");
		String content = req.getParameter("message");

		Contact c = new Contact();
		if (user != null) {
			c.setUserId(user.getUser_id());
		}
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

		String encoded = java.net.URLEncoder.encode(address, java.nio.charset.StandardCharsets.UTF_8);
		String mapEmbedUrl = "https://www.google.com/maps?q=" + encoded + "&output=embed";
		req.setAttribute("mapEmbedUrl", mapEmbedUrl);
		req.getRequestDispatcher("/views/user/contact.jsp").forward(req, resp);
	}
}
