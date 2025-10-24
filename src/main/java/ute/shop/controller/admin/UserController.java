//package ute.shop.controller.admin;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.Part;
//import ute.shop.entity.User;
//import ute.shop.service.IUserService;
//import ute.shop.service.impl.UserServiceImpl;
//
//@WebServlet(urlPatterns = { "/admin/users", "/admin/users/add", "/admin/users/edit", "/admin/users/delete",
//		"/admin/users/lock", "/admin/users/unlock" })
//@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
//		maxFileSize = 1024 * 1024 * 5, // 5MB
//		maxRequestSize = 1024 * 1024 * 10 // 10MB tổng thể
//)
//public class UserController extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	private final IUserService userService = new UserServiceImpl();
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//		String uri = req.getRequestURI();
//
//		try {
//			if (uri.endsWith("/users")) {
//				List<User> allUsers = userService.getAllUsers();
//				req.setAttribute("users", allUsers);
//				req.setAttribute("page", "users");
//				req.setAttribute("view", "/views/admin/users/list.jsp");
//				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
//
//			} else if (uri.endsWith("/add")) {
//				req.setAttribute("page", "users");
//				req.setAttribute("view", "/views/admin/users/add.jsp");
//				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
//
//			} else if (uri.endsWith("/edit")) {
//				int id = Integer.parseInt(req.getParameter("id"));
//				Optional<User> optUser = userService.getUserById(id);
//
//				if (optUser.isPresent()) {
//					req.setAttribute("user", optUser.get());
//				} else {
//					resp.sendRedirect(req.getContextPath() + "/admin/users?message=errorNotfound");
//					return;
//				}
//
//				req.setAttribute("page", "users");
//				req.setAttribute("view", "/views/admin/users/edit.jsp");
//				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
//
//			} else if (uri.endsWith("/delete")) {
//				int id = Integer.parseInt(req.getParameter("id"));
//				userService.delete(id);
//				resp.sendRedirect(req.getContextPath() + "/admin/users?message=successDel");
//
//			} else if (uri.endsWith("/lock")) {
//				int id = Integer.parseInt(req.getParameter("id"));
//				userService.updateStatus(id, "banned");
//				resp.sendRedirect(req.getContextPath() + "/admin/users?message=successLock");
//
//			} else if (uri.endsWith("/unlock")) {
//				int id = Integer.parseInt(req.getParameter("id"));
//				userService.updateStatus(id, "active");
//				resp.sendRedirect(req.getContextPath() + "/admin/users?message=successUnlock");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			resp.sendRedirect(req.getContextPath() + "/admin/users?error");
//		}
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String uri = req.getRequestURI();
//
//		try {
//			// Thư mục upload thực tế
//			String uploadPath = req.getServletContext().getRealPath("/assets/images/avatars");
//			File uploadDir = new File(uploadPath);
//			if (!uploadDir.exists())
//				uploadDir.mkdirs();
//
//			// ===== Xử lý thêm người dùng =====
//			if (uri.endsWith("/add")) {
//				String username = req.getParameter("username");
//				String email = req.getParameter("email");
//				String password = req.getParameter("password");
//				String role = req.getParameter("role");
//				String name = req.getParameter("name");
//				String phone = req.getParameter("phone");
//				String address = req.getParameter("address");
//
//				// Kiểm tra email trùng
//				Optional<User> existing = userService.findByEmail(email);
//				if (existing.isPresent()) {
//					// Lưu thông báo lỗi vào request attribute
//					req.setAttribute("error", "Email đã tồn tại, vui lòng chọn email khác!");
//					req.setAttribute("page", "users");
//					req.setAttribute("view", "/views/admin/users/add.jsp");
//					// Forward để giữ lại dữ liệu form
//					req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
//					return;
//				}
//
//				// Upload avatar
//				Part filePart = req.getPart("avatar");
//				String avatarFileName = null;
//
//				if (filePart != null && filePart.getSize() > 0) {
//					String originalFileName = java.nio.file.Path.of(filePart.getSubmittedFileName()).getFileName()
//							.toString();
//					avatarFileName = originalFileName;
//
//					// Lưu file vào thư mục thực tế
//					filePart.write(uploadPath + File.separator + avatarFileName);
//				}
//
//				// Tạo đối tượng User
//				User u = new User();
//				u.setUsername(username);
//				u.setEmail(email);
//				u.setPassword(password);
//				u.setRole(role);
//				u.setStatus("active");
//				u.setName(name);
//				u.setPhone(phone);
//				u.setAddress(address);
//
//				// Lưu đường dẫn tương đối đúng format DB
//				if (avatarFileName != null) {
//					u.setAvatar("/avatars/" + avatarFileName);
//				} else {
//					u.setAvatar("/avatars/default.jpg"); // ảnh mặc định
//				}
//
//				userService.insert(u);
//				resp.sendRedirect(req.getContextPath() + "/admin/users?message=successAdd");
//			}
//
//			// ===== Xử lý cập nhật người dùng =====
//			else if (uri.endsWith("/edit")) {
//				int id = Integer.parseInt(req.getParameter("id"));
//				String username = req.getParameter("username");
//				String email = req.getParameter("email");
//				String role = req.getParameter("role");
//				String status = req.getParameter("status");
//				String name = req.getParameter("name");
//				String phone = req.getParameter("phone");
//				String address = req.getParameter("address");
//
//				Optional<User> optUser = userService.getUserById(id);
//				if (optUser.isPresent()) {
//					User user = optUser.get();
//
//					// Kiểm tra email trùng (chỉ khi email thay đổi)
//					if (!user.getEmail().equals(email)) {
//						Optional<User> existing = userService.findByEmail(email);
//						if (existing.isPresent()) {
//							resp.sendRedirect(req.getContextPath() + "/admin/users/edit?message=errorMail");
//							return;
//						}
//					}
//
//					Part filePart = req.getPart("avatar");
//					if (filePart != null && filePart.getSize() > 0) {
//						String originalFileName = java.nio.file.Path.of(filePart.getSubmittedFileName()).getFileName()
//								.toString();
//						String newFileName = originalFileName;
//
//						// Ghi file thật vào thư mục upload
//						filePart.write(uploadPath + File.separator + newFileName);
//
//						// Cập nhật đường dẫn tương đối trong DB
//						user.setAvatar("/avatars/" + newFileName);
//					}
//
//					// Cập nhật các thông tin khác
//					user.setUsername(username);
//					user.setEmail(email);
//					user.setRole(role);
//					user.setStatus(status);
//					user.setName(name);
//					user.setPhone(phone);
//					user.setAddress(address);
//
//					// Cập nhật database
//					userService.updatePwd(user, false);
//					resp.sendRedirect(req.getContextPath() + "/admin/users?message=successEdit");
//				} else {
//					req.setAttribute("error", "Không tìm thấy người dùng cần chỉnh sửa!");
//					req.setAttribute("page", "users");
//					req.setAttribute("view", "/views/admin/users/edit.jsp");
//					req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			resp.sendRedirect(req.getContextPath() + "/admin/users?error");
//		}
//	}
//
//}

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
				req.setAttribute("view", "/views/admin/users/list.jsp");
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
					resp.sendRedirect(req.getContextPath() + "/admin/users?error=errorNotfound");
					return;
				}

				req.setAttribute("page", "users");
				req.setAttribute("view", "/views/admin/users/edit.jsp");
				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

			} else if (uri.endsWith("/delete")) {
				int id = Integer.parseInt(req.getParameter("id"));
				userService.delete(id);
				resp.sendRedirect(req.getContextPath() + "/admin/users?message=successDel");

			} else if (uri.endsWith("/lock")) {
				int id = Integer.parseInt(req.getParameter("id"));
				userService.updateStatus(id, "banned");
				resp.sendRedirect(req.getContextPath() + "/admin/users?message=successLock");

			} else if (uri.endsWith("/unlock")) {
				int id = Integer.parseInt(req.getParameter("id"));
				userService.updateStatus(id, "active");
				resp.sendRedirect(req.getContextPath() + "/admin/users?message=successUnlock");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath() + "/admin/users?error=unknownError");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		try {
			String uploadPath = req.getServletContext().getRealPath("/assets/images/avatars");
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) uploadDir.mkdirs();

			if (uri.endsWith("/add")) {
				String username = req.getParameter("username");
				String email = req.getParameter("email");
				String password = req.getParameter("password");
				String role = req.getParameter("role");
				String name = req.getParameter("name");
				String phone = req.getParameter("phone");
				String address = req.getParameter("address");

				if (userService.findByEmail(email).isPresent()) {
					req.setAttribute("error", "Mail đã tồn tại");
					req.setAttribute("page", "users");
					req.setAttribute("view", "/views/admin/users/add.jsp");
					req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
					return;
				}

				Part filePart = req.getPart("avatar");
				String avatarFileName = null;
				if (filePart != null && filePart.getSize() > 0) {
					avatarFileName = java.nio.file.Path.of(filePart.getSubmittedFileName()).getFileName().toString();
					filePart.write(uploadPath + File.separator + avatarFileName);
				}

				User u = new User();
				u.setUsername(username);
				u.setEmail(email);
				u.setPassword(password); // Lưu ý: Nên mã hóa mật khẩu trong service trước khi lưu
				u.setRole(role);
				u.setStatus("active");
				u.setName(name);
				u.setPhone(phone);
				u.setAddress(address);
				if (avatarFileName != null) {
					u.setAvatar("/avatars/" + avatarFileName);
				}

				userService.insert(u);
				resp.sendRedirect(req.getContextPath() + "/admin/users?message=successAdd");

			} else if (uri.endsWith("/edit")) {
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

					if (!user.getEmail().equals(email) && userService.findByEmail(email).isPresent()) {
						req.setAttribute("error", "Mail đã tồn tại");
						req.setAttribute("user", user);
						req.setAttribute("page", "users");
						req.setAttribute("view", "/views/admin/users/edit.jsp");
						req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
						return;
					}

					Part filePart = req.getPart("avatar");
					if (filePart != null && filePart.getSize() > 0) {
						String fileName = java.nio.file.Path.of(filePart.getSubmittedFileName()).getFileName().toString();
						filePart.write(uploadPath + File.separator + fileName);
						user.setAvatar("/avatars/" + fileName);
					}

					user.setUsername(username);
					user.setEmail(email);
					user.setRole(role);
					user.setStatus(status);
					user.setName(name);
					user.setPhone(phone);
					user.setAddress(address);

					userService.update(user);
					resp.sendRedirect(req.getContextPath() + "/admin/users?message=successEdit");
				} else {
					resp.sendRedirect(req.getContextPath() + "/admin/users?error=errorNotfound");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath() + "/admin/users?error=unknownError");
		}
	}
}