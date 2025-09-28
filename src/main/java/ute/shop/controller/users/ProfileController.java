package ute.shop.controller.users;

import ute.shop.models.User;
import ute.shop.service.IUserService;
import ute.shop.service.impl.UserServiceImpl;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;

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

		HttpSession session = request.getSession();
		User account = (User) session.getAttribute("account");

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
		HttpSession session = request.getSession();
		User account = (User) session.getAttribute("account");

		if (account == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

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
	            account.setAvatar(fileName); // chỉ lưu tên file
	        }

		// Gán lại username, email nếu có
		if (username != null && !username.trim().isEmpty()) {
			account.setUsername(username);
		}
		if (email != null && !email.trim().isEmpty()) {
			account.setEmail(email);
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
        boolean updated = service.UpdatePwd(account, changePwd);

		
		if (updated) {
			session.setAttribute("account", account);
			request.setAttribute("success", "Cập nhật hồ sơ thành công!");
		} else {
			request.setAttribute("error", "Có lỗi xảy ra khi cập nhật hồ sơ!");
		}

		request.getRequestDispatcher("/views/user/profile.jsp").forward(request, response);
	}

}
