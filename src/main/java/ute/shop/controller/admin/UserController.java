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
import ute.shop.entity.Contact;
import ute.shop.entity.User;
import ute.shop.service.IContactService;
import ute.shop.service.IUserService;
import ute.shop.service.impl.ContactServiceImpl;
import ute.shop.service.impl.UserServiceImpl;
import ute.shop.utils.SendMail;

@WebServlet(urlPatterns = { "/admin/users", "/admin/users/add", "/admin/users/edit", "/admin/users/delete",
		"/admin/users/lock", "/admin/users/unlock", "/admin/users/contact" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
		maxFileSize = 1024 * 1024 * 5, // 5MB
		maxRequestSize = 1024 * 1024 * 10 // 10MB tổng thể
)
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IUserService userService = new UserServiceImpl();
	private final IContactService contactService = new ContactServiceImpl();

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

			} else if (uri.endsWith("/contact")) {
				int userId = Integer.parseInt(req.getParameter("id"));
				Optional<User> userOpt = userService.getUserById(userId);

				if (userOpt.isPresent()) {
					req.setAttribute("user", userOpt.get());
					req.setAttribute("page", "users");
					req.setAttribute("view", "/views/admin/users/contact.jsp");
					req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
				} else {
					resp.sendRedirect(req.getContextPath() + "/admin/users?error=invalidId");
				}
			}

			else if (uri.endsWith("/add")) {
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
			resp.sendRedirect(req.getContextPath() + "/admin/users?error=errorGet");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		try {
			String uploadPath = req.getServletContext().getRealPath("/assets/images/avatars");
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists())
				uploadDir.mkdirs();

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
				u.setPassword(password);
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

			} // Giả sử URI của bạn là "/admin/users/contact" như trong form action
			else if (uri.endsWith("/admin/users/contact")) {
				// Lấy các tham số từ form
				int userId = Integer.parseInt(req.getParameter("id"));
				String subject = req.getParameter("subject");
				String messageContent = req.getParameter("message"); // Nội dung admin nhập vào

				// Tìm kiếm người dùng trong CSDL
				Optional<User> userOpt = userService.getUserById(userId);

				if (userOpt.isPresent()) {
					User user = userOpt.get();
					String recipientEmail = user.getEmail();

					// 1. Xây dựng URL cơ sở (Base URL) để lấy logo
					String scheme = req.getScheme(); // http
					String serverName = req.getServerName(); // localhost
					int serverPort = req.getServerPort(); // 8080
					String contextPath = req.getContextPath(); // /uteshop

					StringBuilder urlBuilder = new StringBuilder();
					urlBuilder.append(scheme).append("://").append(serverName);

					// Chỉ thêm port vào URL nếu nó không phải là port mặc định
					if (serverPort != 80 && serverPort != 443) {
						urlBuilder.append(":").append(serverPort);
					}
					urlBuilder.append(contextPath);
					// Xây dựng nội dung email theo mẫu dành cho quản trị viên
					StringBuilder emailBodyHtml = new StringBuilder();
					// =================================================================
					// == BẮT ĐẦU NỘI DUNG EMAIL HTML ĐÃ CẬP NHẬT ==
					// =================================================================
					emailBodyHtml.append("<!DOCTYPE html><html lang='vi'><head><meta charset='UTF-8'>")
							// --- PHẦN CSS ---
							.append("<style>")
							.append("body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f4f4f4; margin: 0; padding: 0; }")
							.append(".container { width: 100%; max-width: 600px; margin: 20px auto; padding: 25px; border-radius: 12px; background-color: #ffffff; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }")

							// CSS CHO HEADER BANNER MỚI
							// Biến header thành một banner màu xanh, chữ trắng và bo tròn góc trên.
							.append(".header { background-color: #00558D; color: #ffffff; padding: 25px; text-align: center; margin: -25px -25px 25px -25px; border-radius: 12px 12px 0 0; }")
							.append(".header h1 { margin: 0; font-size: 28px; letter-spacing: 1px; }") // Style cho chữ
																										// UteShop

							.append(".content { margin-bottom: 20px; }").append(".content p { margin-bottom: 15px; }")
							.append(".message-box { background-color: #f9f9f9; border-left: 4px solid #007bff; padding: 16px; margin: 20px 0; border-radius: 8px; }")
							.append(".footer { font-size: 0.9em; text-align: center; color: #777; margin-top: 25px; }")
							.append("</style></head><body>")

							// --- PHẦN HTML BODY ---
							.append("<div class='container'>")

							.append("<div class='header'><h1>UteShop</h1></div>")

							.append("<div class='content'>").append("<h3>Chào ").append(user.getName()).append(",</h3>") // Sử
																															// dụng
																															// tên
																															// người
																															// dùng
							.append("<p>Chúng tôi từ đội ngũ quản trị viên của <strong>UteShop</strong> gửi đến bạn thông báo sau:</p>")

							// Hộp tin nhắn từ admin
							.append("<div class='message-box'>").append(messageContent.replace("\n", "<br>"))
							.append("</div>")

							.append("<p>Nếu có bất kỳ thắc mắc nào, bạn có thể trả lời lại email này để được hỗ trợ.</p>")
							.append("</div>").append("<div class='footer'>")
							.append("<p>Trân trọng,<br><strong>Đội ngũ UteShop</strong></p>").append("</div>")
							.append("</div></body></html>");
					// =================================================================
					// == KẾT THÚC NỘI DUNG EMAIL HTML ==
					// =================================================================
					// Gửi email
					try {
						SendMail mailer = new SendMail();
						mailer.sendMail(recipientEmail, subject, emailBodyHtml.toString());
						// Chuyển hướng với thông báo thành công
						resp.sendRedirect(req.getContextPath() + "/admin/users?message=contactSent");
					} catch (Exception e) {
						e.printStackTrace();
						// Chuyển hướng với thông báo lỗi
						resp.sendRedirect(req.getContextPath() + "/admin/users?error=mailError");
					}
				} else {
					// Nếu không tìm thấy user, chuyển hướng với thông báo lỗi
					resp.sendRedirect(req.getContextPath() + "/admin/users?error=userNotFound");
				}
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
						String fileName = java.nio.file.Path.of(filePart.getSubmittedFileName()).getFileName()
								.toString();
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
			resp.sendRedirect(req.getContextPath() + "/admin/users?error=errorPost");
		}
	}
}