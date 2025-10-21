package ute.shop.controller.users;

import ute.shop.entity.User;
import ute.shop.service.IUserService;
import ute.shop.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(urlPatterns = { "/user/profile" })
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User account = (User) request.getAttribute("account");

		if (account == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		request.getRequestDispatcher("/views/user/profile.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		User account = (User) request.getAttribute("account");

		if (account == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		// Thư mục upload thực tế
		String uploadPath = request.getServletContext().getRealPath("/assets/images/avatars");
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdirs();

		// Lấy dữ liệu form
		String email = request.getParameter("email");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		String username = request.getParameter("username");
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String address = request.getParameter("address");

		// Upload avatar
		Part filePart = request.getPart("avatar");
		String avatarFileName = null;

		if (filePart != null && filePart.getSize() > 0) {
			String originalFileName = java.nio.file.Path.of(filePart.getSubmittedFileName()).getFileName().toString();
			avatarFileName = originalFileName;

			// Lưu file vào thư mục thực tế
			filePart.write(uploadPath + File.separator + avatarFileName);
		}

		// Gán lại username, email nếu có
		if (username != null && !username.trim().isEmpty()) {
			account.setUsername(username);
		}
		if (email != null && !email.trim().isEmpty()) {
			account.setEmail(email);
		}
		if (phone != null && !phone.trim().isEmpty()) {
			account.setPhone(phone);
		}
		if (address != null && !address.trim().isEmpty()) {
			account.setAddress(address);
		}
		if (name != null && !name.trim().isEmpty()) {
			account.setName(name);
		}

		// Lưu đường dẫn tương đối đúng format DB
		if (avatarFileName != null) {
			account.setAvatar("/avatars/" + avatarFileName);
		} else {
			account.setAvatar("/avatars/default.jpg"); // ảnh mặc định
		}

		// Đổi mật khẩu nếu có
		boolean changePwd = false;
		if (oldPassword != null && !oldPassword.isEmpty()) {
			String oldPasswordHash = service.hashPassword(oldPassword);
			if (!oldPasswordHash.equals(account.getPassword())) {
				request.setAttribute("error", "Mật khẩu hiện tại không đúng!");
				request.getRequestDispatcher("/views/user/profile.jsp").forward(request, response);
				return;
			}
			if (!newPassword.equals(confirmPassword)) {
				request.setAttribute("error", "Xác nhận mật khẩu mới không khớp!");
				request.getRequestDispatcher("/views/user/profile.jsp").forward(request, response);
				return;
			}
			account.setPassword(newPassword);
			changePwd = true; // xác nhận thay đôỉ pass
		}

		// Update DB
		boolean updated = service.updatePwd(account, changePwd);

		if (updated) {
			request.setAttribute("account", account);
			request.setAttribute("success", "Cập nhật hồ sơ thành công!");
		} else {
			request.setAttribute("error", "Có lỗi xảy ra khi cập nhật hồ sơ!");
		}

		request.getRequestDispatcher("/views/user/profile.jsp").forward(request, response);
	}

}
