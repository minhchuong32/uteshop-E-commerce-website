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

	    if (uri.endsWith("/add")) {
	        handleAddUser(req, resp);
	    } else if (uri.endsWith("/edit")) {
	        handleEditUser(req, resp);
	    } else if (uri.endsWith("/contact")) {
	        handleContactUser(req, resp);
	    } else {
	        // Xử lý trường hợp không có url nào khớp, ví dụ chuyển hướng về trang chính
	        resp.sendRedirect(req.getContextPath() + "/admin/users?error=invalidAction");
	    }
	}

	/**
	 * Xử lý logic thêm người dùng mới.
	 */
	private void handleAddUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    User userFromForm = new User(); // Dùng để giữ lại dữ liệu form khi có lỗi

	    try {
	        // 1. Lấy tất cả tham số từ form
	        String username = req.getParameter("username");
	        String email = req.getParameter("email");
	        String password = req.getParameter("password");
	        String role = req.getParameter("role");
	        String name = req.getParameter("name");
	        String phone = req.getParameter("phone");
	        String address = req.getParameter("address");

	        // Đưa dữ liệu vào đối tượng userFromForm để trả về form nếu có lỗi
	        userFromForm.setUsername(username);
	        userFromForm.setEmail(email);
	        userFromForm.setName(name);
	        userFromForm.setPhone(phone);
	        userFromForm.setAddress(address);
	        userFromForm.setRole(role);

	        // 2. Validation
	        if (userService.findByUsername(username).isPresent()) {
	            req.setAttribute("error", "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.");
	            req.setAttribute("user", userFromForm); // Trả lại dữ liệu đã nhập
	            forwardToView(req, resp, "/views/admin/users/add.jsp");
	            return;
	        }

	        if (userService.findByEmail(email).isPresent()) {
	            req.setAttribute("error", "Email đã được sử dụng. Vui lòng chọn email khác.");
	            req.setAttribute("user", userFromForm); // Trả lại dữ liệu đã nhập
	            forwardToView(req, resp, "/views/admin/users/add.jsp");
	            return;
	        }
	        
	        // 3. Xử lý upload file avatar
	        String uploadPath = req.getServletContext().getRealPath("/assets/images/avatars");
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) uploadDir.mkdirs();
	        
	        Part filePart = req.getPart("avatar");
	        String avatarFileName = null;
	        if (filePart != null && filePart.getSize() > 0) {
	            avatarFileName = java.nio.file.Path.of(filePart.getSubmittedFileName()).getFileName().toString();
	            filePart.write(uploadPath + File.separator + avatarFileName);
	        }

	        // 4. Tạo đối tượng User để lưu vào DB
	        User userToSave = new User();
	        userToSave.setUsername(username);
	        userToSave.setEmail(email);
	        
	        userToSave.setPassword(password); 
	        
	        userToSave.setRole(role);
	        userToSave.setStatus("active");
	        userToSave.setName(name);
	        userToSave.setPhone(phone);
	        userToSave.setAddress(address);
	        if (avatarFileName != null) {
	            userToSave.setAvatar("/avatars/" + avatarFileName);
	        }

	        // 5. Lưu vào DB và chuyển hướng
	        userService.insert(userToSave);
	        resp.sendRedirect(req.getContextPath() + "/admin/users?message=successAdd");

	    } catch (Exception e) {
	        e.printStackTrace();
	        req.setAttribute("error", "Đã xảy ra lỗi không mong muốn khi thêm người dùng. Chi tiết: " + e.getMessage());
	        req.setAttribute("user", userFromForm); // Trả lại dữ liệu đã nhập
	        forwardToView(req, resp, "/views/admin/users/add.jsp");
	    }
	}

	/**
	 * Xử lý logic chỉnh sửa thông tin người dùng.
	 */
	private void handleEditUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    try {
	        // 1. Lấy tham số và user hiện tại
	        int id = Integer.parseInt(req.getParameter("id"));
	        Optional<User> optUser = userService.getUserById(id);

	        if (!optUser.isPresent()) {
	            resp.sendRedirect(req.getContextPath() + "/admin/users?error=userNotFound");
	            return;
	        }
	        User user = optUser.get();

	        // 2. Lấy thông tin mới từ form
	        String username = req.getParameter("username");
	        String email = req.getParameter("email");
	        String role = req.getParameter("role");
	        String status = req.getParameter("status");
	        String name = req.getParameter("name");
	        String phone = req.getParameter("phone");
	        String address = req.getParameter("address");
	        
	        // 3. Validation: Kiểm tra email mới có bị trùng với người khác không
	        if (!user.getEmail().equalsIgnoreCase(email) && userService.findByEmail(email).isPresent()) {
	            req.setAttribute("error", "Email đã được sử dụng bởi một tài khoản khác.");
	            req.setAttribute("user", user); // Giữ lại thông tin cũ trên form
	            forwardToView(req, resp, "/views/admin/users/edit.jsp");
	            return;
	        }
	        
	        // 4. Xử lý upload file avatar mới
	        Part filePart = req.getPart("avatar");
	        if (filePart != null && filePart.getSize() > 0) {
	            String uploadPath = req.getServletContext().getRealPath("/assets/images/avatars");
	            String fileName = java.nio.file.Path.of(filePart.getSubmittedFileName()).getFileName().toString();
	            filePart.write(uploadPath + File.separator + fileName);
	            user.setAvatar("/avatars/" + fileName);
	        }

	        // 5. Cập nhật thông tin cho đối tượng User
	        user.setUsername(username);
	        user.setEmail(email);
	        user.setRole(role);
	        user.setStatus(status);
	        user.setName(name);
	        user.setPhone(phone);
	        user.setAddress(address);

	        // 6. Lưu thay đổi và chuyển hướng
	        userService.update(user);
	        resp.sendRedirect(req.getContextPath() + "/admin/users?message=successEdit");
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Chuyển hướng về trang danh sách với lỗi chung
	        resp.sendRedirect(req.getContextPath() + "/admin/users?error=errorPost");
	    }
	}


	/**
	 * Xử lý logic gửi email liên hệ tới người dùng.
	 */
	private void handleContactUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    try {
	        int userId = Integer.parseInt(req.getParameter("id"));
	        String subject = req.getParameter("subject");
	        String messageContent = req.getParameter("message");

	        Optional<User> userOpt = userService.getUserById(userId);

	        if (!userOpt.isPresent()) {
	            resp.sendRedirect(req.getContextPath() + "/admin/users?error=userNotFound");
	            return;
	        }
	        
	        User user = userOpt.get();
	        String recipientEmail = user.getEmail();

	        // Xây dựng nội dung email (giữ nguyên logic của bạn)
	        StringBuilder emailBodyHtml = new StringBuilder();
			emailBodyHtml.append("<!DOCTYPE html><html lang='vi'><head><meta charset='UTF-8'>")
					.append("<style>")
					.append("body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f4f4f4; margin: 0; padding: 0; }")
					.append(".container { width: 100%; max-width: 600px; margin: 20px auto; padding: 25px; border-radius: 12px; background-color: #ffffff; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }")
					.append(".header { background-color: #00558D; color: #ffffff; padding: 25px; text-align: center; margin: -25px -25px 25px -25px; border-radius: 12px 12px 0 0; }")
					.append(".header h1 { margin: 0; font-size: 28px; letter-spacing: 1px; }") 
					.append(".content { margin-bottom: 20px; }").append(".content p { margin-bottom: 15px; }")
					.append(".message-box { background-color: #f9f9f9; border-left: 4px solid #007bff; padding: 16px; margin: 20px 0; border-radius: 8px; }")
					.append(".footer { font-size: 0.9em; text-align: center; color: #777; margin-top: 25px; }")
					.append("</style></head><body>")
					.append("<div class='container'>")
					.append("<div class='header'><h1>UteShop</h1></div>")
					.append("<div class='content'>").append("<h3>Chào ").append(user.getName()).append(",</h3>")
					.append("<p>Chúng tôi từ đội ngũ quản trị viên của <strong>UteShop</strong> gửi đến bạn thông báo sau:</p>")
					.append("<div class='message-box'>").append(messageContent.replace("\n", "<br>"))
					.append("</div>")
					.append("<p>Nếu có bất kỳ thắc mắc nào, bạn có thể trả lời lại email này để được hỗ trợ.</p>")
					.append("</div>").append("<div class='footer'>")
					.append("<p>Trân trọng,<br><strong>Đội ngũ UteShop</strong></p>").append("</div>")
					.append("</div></body></html>");

	        SendMail mailer = new SendMail();
	        mailer.sendMail(recipientEmail, subject, emailBodyHtml.toString());
	        resp.sendRedirect(req.getContextPath() + "/admin/users?message=contactSent");

	    } catch (Exception e) {
	        e.printStackTrace();
	        resp.sendRedirect(req.getContextPath() + "/admin/users?error=mailError");
	    }
	}

	/**
	 * Phương thức trợ giúp để forward request tới một view cụ thể.
	 */
	private void forwardToView(HttpServletRequest req, HttpServletResponse resp, String viewPath) throws ServletException, IOException {
	    req.setAttribute("page", "users");
	    req.setAttribute("view", viewPath);
	    req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
	}
}