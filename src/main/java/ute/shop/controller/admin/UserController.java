package ute.shop.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ute.shop.entity.User;
import ute.shop.service.IUserService;
import ute.shop.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = { "/admin/users", "/admin/users/add", "/admin/users/edit", "/admin/users/delete",
		"/admin/users/lock", "/admin/users/unlock" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
		maxFileSize = 1024 * 1024 * 5, // 5MB
		maxRequestSize = 1024 * 1024 * 10 // 10MB tổng thể
)
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI();

		try {
			if (uri.endsWith("/users")) {
				List<User> allUsers = userService.getAllUsers();
				req.setAttribute("users", allUsers);
				req.setAttribute("page", "users");
				req.setAttribute("view", "/views/admin/users/dashboard.jsp");
				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

			} else if (uri.endsWith("/add")) {
				req.setAttribute("page", "users");
				req.setAttribute("view", "/views/admin/users/add.jsp");
				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

			} else if (uri.endsWith("/edit")) {
				int id = Integer.parseInt(req.getParameter("id"));
				Optional<User> optUser = userService.getUserById(id);

				if (optUser.isPresent()) {
					req.setAttribute("user", optUser.get());
				} else {
					req.setAttribute("error", "Không tìm thấy người dùng!");
				}

				req.setAttribute("page", "users");
				req.setAttribute("view", "/views/admin/users/edit.jsp");
				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

			} else if (uri.endsWith("/delete")) {
				int id = Integer.parseInt(req.getParameter("id"));
				userService.delete(id);
				req.getSession().setAttribute("success", "Xóa người dùng thành công!");
				resp.sendRedirect(req.getContextPath() + "/admin/users");

			} else if (uri.endsWith("/lock")) {
				int id = Integer.parseInt(req.getParameter("id"));
				userService.updateStatus(id, "banned");
				req.getSession().setAttribute("success", "Đã khóa tài khoản người dùng!");
				resp.sendRedirect(req.getContextPath() + "/admin/users");

			} else if (uri.endsWith("/unlock")) {
				int id = Integer.parseInt(req.getParameter("id"));
				userService.updateStatus(id, "active");
				req.getSession().setAttribute("success", "Đã mở khóa tài khoản người dùng!");
				resp.sendRedirect(req.getContextPath() + "/admin/users");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
			req.setAttribute("page", "users");
			req.setAttribute("view", "/views/admin/users/dashboard.jsp");
			req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();

		try {
			// Thư mục upload thực tế
			String uploadPath = req.getServletContext().getRealPath("/assets/images/avatars");
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists())
				uploadDir.mkdirs();

			// ===== Xử lý thêm người dùng =====
			if (uri.endsWith("/add")) {
				String username = req.getParameter("username");
				String email = req.getParameter("email");
				String password = req.getParameter("password");
				String role = req.getParameter("role");
				String name = req.getParameter("name");
				String phone = req.getParameter("phone");
				String address = req.getParameter("address");

				// Upload avatar
				Part filePart = req.getPart("avatar");
				String avatarFileName = null;

				if (filePart != null && filePart.getSize() > 0) {
					String originalFileName = java.nio.file.Path.of(filePart.getSubmittedFileName()).getFileName()
							.toString();
					avatarFileName = originalFileName;

					// Lưu file vào thư mục thực tế
					filePart.write(uploadPath + File.separator + avatarFileName);
				}

				// Tạo đối tượng User
				User u = new User();
				u.setUsername(username);
				u.setEmail(email);
				u.setPassword(password);
				u.setRole(role);
				u.setStatus("active");
				u.setName(name);
				u.setPhone(phone);
				u.setAddress(address);

				// Lưu đường dẫn tương đối đúng format DB
				if (avatarFileName != null) {
					u.setAvatar("/avatars/" + avatarFileName);
				} else {
					u.setAvatar("/avatars/default.jpg"); // ảnh mặc định
				}

				userService.insert(u);
				req.getSession().setAttribute("success", "Thêm người dùng thành công!");
				resp.sendRedirect(req.getContextPath() + "/admin/users");
			}

			// ===== Xử lý cập nhật người dùng =====
			else if (uri.endsWith("/edit")) {
				int id = Integer.parseInt(req.getParameter("id"));
				String username = req.getParameter("username");
				String email = req.getParameter("email");
				String role = req.getParameter("role");
				String status = req.getParameter("status");
				String name = req.getParameter("name");
				String phone = req.getParameter("phone");
				String address = req.getParameter("address");

				Optional<User> optUser = userService.getUserById(id);

				if (optUser.isPresent()) {
					User user = optUser.get();

					Part filePart = req.getPart("avatar");
					if (filePart != null && filePart.getSize() > 0) {
						String originalFileName = java.nio.file.Path.of(filePart.getSubmittedFileName()).getFileName()
								.toString();
						String newFileName = originalFileName;

						// Ghi file thật vào thư mục upload
						filePart.write(uploadPath + File.separator + newFileName);

						// Cập nhật đường dẫn tương đối trong DB
						user.setAvatar("/avatars/" + newFileName);
					}

					// Cập nhật các thông tin khác
					user.setUsername(username);
					user.setEmail(email);
					user.setRole(role);
					user.setStatus(status);
					user.setName(name);
					user.setPhone(phone);
					user.setAddress(address);

					// Cập nhật database
					userService.updatePwd(user, false);
					req.getSession().setAttribute("success", "Cập nhật người dùng thành công!");
					resp.sendRedirect(req.getContextPath() + "/admin/users");
				} else {
					req.setAttribute("error", "Không tìm thấy người dùng cần chỉnh sửa!");
					req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Lỗi khi xử lý dữ liệu: " + e.getMessage());
			req.setAttribute("page", "users");
			req.setAttribute("view", "/views/admin/users/dashboard.jsp");
			req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
		}
	}

}
