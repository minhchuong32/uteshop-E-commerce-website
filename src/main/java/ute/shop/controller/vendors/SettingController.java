package ute.shop.controller.vendors;

import ute.shop.entity.Shop;
import ute.shop.entity.StoreSettings;
import ute.shop.entity.User;
import ute.shop.service.IShopService;
import ute.shop.service.IStoreSettingsService;
import ute.shop.service.IUserService;
import ute.shop.service.impl.ShopServiceImpl;
import ute.shop.service.impl.StoreSettingsServiceImpl;
import ute.shop.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/vendor/settings")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class SettingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IShopService shopService = new ShopServiceImpl();
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

		Shop shop = shopService.findByUserId(admin.getUserId());
		if (shop == null) {
		    request.setAttribute("error", "Không tìm thấy cửa hàng của bạn!");
		} else {
		    request.setAttribute("shop", shop); 
		}

		// Forward sang settings.jsp
		request.setAttribute("page", "settings"); // css
		request.setAttribute("view", "/views/vendor/settings.jsp"); // render -> main
		request.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(request, response); // sitemesh

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
		// --- Shop ---
		Shop shop = shopService.findByUserId(admin.getUserId()); 
	    if (shop == null) {
	        request.setAttribute("error", "Không tìm thấy cửa hàng của bạn!");
	        doGet(request, response);
	        return;
	    }

	    shop.setName(request.getParameter("store_name"));
	    shop.setDescription(request.getParameter("description"));
	    shop.setCreatedAt(shop.getCreatedAt()); 

	    Part logoPart = request.getPart("logoFile");
	    if (logoPart != null && logoPart.getSize() > 0) {
	        String fileName = logoPart.getSubmittedFileName();
	        String uploadPath = request.getServletContext().getRealPath("/uploads");
	        logoPart.write(uploadPath + "/" + fileName);
	        shop.setLogo(fileName);
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
				request.setAttribute("view", "/views/vendor/settings.jsp"); // render -> main
				request.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(request, response); // sitemesh
				return;
			}
			admin.setPassword(newPassword);
			changePwd = true; // xác nhận thay đôỉ pass
		}

		// Update DB
		boolean updatedprofile = userService.updatePwd(admin, changePwd);
		// Gọi service update
		shopService.update(shop);

		if (updatedprofile) {
	        session.setAttribute("account", admin);
	        request.setAttribute("message", "Cập nhật thành công!");
	        request.setAttribute("messageType", "success");
	    } else {
	        request.setAttribute("message", "Có lỗi khi cập nhật!");
	        request.setAttribute("messageType", "error");
	    }

		Shop updatedShop = shopService.findByUserId(admin.getUserId());
	    request.setAttribute("shop", updatedShop);

		// Forward sang settings.jsp
		request.setAttribute("page", "settings"); // css
		request.setAttribute("view", "/views/vendor/settings.jsp"); // render -> main
		request.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(request, response); // sitemesh
	}
}
