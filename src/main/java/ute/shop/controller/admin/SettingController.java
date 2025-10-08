	package ute.shop.controller.admin;

import ute.shop.entity.StoreSettings;
import ute.shop.entity.User;
import ute.shop.service.IStoreSettingsService;
import ute.shop.service.IUserService;
import ute.shop.service.impl.StoreSettingsServiceImpl;
import ute.shop.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/settings")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class SettingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IStoreSettingsService storeService = new StoreSettingsServiceImpl();
	private IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User admin = (User) session.getAttribute("account");

		if (admin == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		// Load store settings từ DB
		StoreSettings store = storeService.getSettings();

		// Đặt attribute cho JSP
		request.setAttribute("store", store);

		// Forward sang settings.jsp
		request.setAttribute("page", "settings"); // css
		request.setAttribute("view", "/views/admin/settings.jsp"); // render -> main
		request.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(request, response); // sitemesh

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User admin = (User) session.getAttribute("account");

		if (admin == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		// --- Store settings ---
		StoreSettings store = storeService.getSettings();
		store.setStoreName(request.getParameter("store_name"));
		store.setEmail(request.getParameter("store_email"));
		store.setHotline(request.getParameter("hotline"));
		store.setAddress(request.getParameter("address"));
		store.setTheme(request.getParameter("theme"));
		store.setCodEnabled(request.getParameter("cod_enabled") != null);
		store.setMomoEnabled(request.getParameter("momo_enabled") != null);
		store.setVnpayEnabled(request.getParameter("vnpay_enabled") != null);

		Part logoPart = request.getPart("logo");
		if (logoPart != null && logoPart.getSize() > 0) {
			String fileName = logoPart.getSubmittedFileName();
			String uploadPath = request.getServletContext().getRealPath("/uploads");
			logoPart.write(uploadPath + "/" + fileName);
			store.setLogo(fileName); // chỉ lưu tên file
		}

		// --- User profile (admin) ---
		// Lấy dữ liệu form
		String email = request.getParameter("email");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		String username = request.getParameter("username");

		// Upload avatar nếu có
		Part avatarPart = request.getPart("avatarFile");
		if (avatarPart != null && avatarPart.getSize() > 0) {
			String fileName = avatarPart.getSubmittedFileName();
			String uploadPath = request.getServletContext().getRealPath("/uploads");
			avatarPart.write(uploadPath + "/" + fileName);
			admin.setAvatar(fileName); // chỉ lưu tên file
		}

		// Gán lại username, email nếu có
		if (username != null && !username.trim().isEmpty()) {
			admin.setUsername(username);
		}
		if (email != null && !email.trim().isEmpty()) {
			admin.setEmail(email);
		}
		// Đổi mật khẩu nếu có
		boolean changePwd = false;
		if (oldPassword != null && !oldPassword.isEmpty()) {
			String oldPasswordHash = userService.hashPassword(oldPassword);
			if (!oldPasswordHash.equals(admin.getPassword())) {
				request.setAttribute("error", "Mật khẩu hiện tại không đúng!");
				// Forward sang settings.jsp
				request.setAttribute("page", "settings"); // css
				request.setAttribute("view", "/views/admin/settings.jsp"); // render -> main
				request.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(request, response); // sitemesh
				return;
			}
			if (!newPassword.equals(confirmPassword)) {
				request.setAttribute("error", "Xác nhận mật khẩu mới không khớp!");
				// Forward sang settings.jsp
				request.setAttribute("page", "settings"); // css
				request.setAttribute("view", "/views/admin/settings.jsp"); // render -> main
				request.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(request, response); // sitemesh
				return;
			}
			admin.setPassword(newPassword);
			changePwd = true; // xác nhận thay đôỉ pass
		}

		// Update DB
		boolean updatedprofile = userService.updatePwd(admin, changePwd);
		// Gọi service update
		boolean updatesetting = storeService.updateSettings(store);

		if (updatedprofile && updatesetting) {
			session.setAttribute("account", admin);
			request.setAttribute("message", "Cập nhật thành công!");
			request.setAttribute("messageType", "success");
		} else {
			request.setAttribute("message", "Có lỗi khi cập nhật!");
			request.setAttribute("messageType", "error");
		}

		// Luôn load lại dữ liệu từ DB để hiển thị
		StoreSettings updatedStore = storeService.getSettings();
		request.setAttribute("store", updatedStore);

		// Forward sang settings.jsp
		request.setAttribute("page", "settings"); // css
		request.setAttribute("view", "/views/admin/settings.jsp"); // render -> main
		request.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(request, response); // sitemesh
	}
}
