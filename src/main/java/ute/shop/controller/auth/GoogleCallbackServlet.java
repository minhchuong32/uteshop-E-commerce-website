package ute.shop.controller.auth;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ute.shop.dao.IUserDao;
import ute.shop.dao.impl.UserDaoImpl;
import ute.shop.entity.User;
import ute.shop.utils.GoogleOAuthUtils;
import ute.shop.utils.GoogleOAuthUtils.GoogleUserInfo;

@WebServlet(urlPatterns = "/oauth2callback")
public class GoogleCallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserDao userDao = new UserDaoImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		try {
			// Dùng code để lấy thông tin người dùng từ Google
			GoogleUserInfo googleInfo = GoogleOAuthUtils.getUserInfo(code);

			// Tìm hoặc tạo người dùng trong database
			Optional<User> userOpt = userDao.findByGoogleId(googleInfo.getId());
			User user;
			if (userOpt.isPresent()) {
				user = userOpt.get();
			} else {
				Optional<User> userByEmailOpt = userDao.findByEmail(googleInfo.getEmail());
				if (userByEmailOpt.isPresent()) {
					user = userByEmailOpt.get();
					user.setGoogleId(googleInfo.getId());
					userDao.update(user);
				} else {
					user = new User();
					user.setGoogleId(googleInfo.getId());
					user.setEmail(googleInfo.getEmail());
					user.setName(googleInfo.getName());
					user.setAvatar("/avatars/default.jpg");
					// Lấy phần trước dấu '@' trong email làm username
					String email = googleInfo.getEmail();
					String username = email != null && email.contains("@") ? email.substring(0, email.indexOf("@"))
							: email;
					user.setUsername(username);
					user.setPassword(null);
					user.setRole("User");
					user.setStatus("active");
					user = userDao.insert(user);

				}
			}

			// Lưu người dùng vào session
			HttpSession session = request.getSession();
			session.setAttribute("account", user);

			// Điều hướng theo vai trò
			String redirectUrl;
			switch (user.getRole().toLowerCase()) {
			case "admin":
				redirectUrl = request.getContextPath() + "/admin/home";
				break;
			case "shipper":
				redirectUrl = request.getContextPath() + "/shipper/home";
				break;
			case "vendor":
				redirectUrl = request.getContextPath() + "/vendor/home";
				break;
			case "user":
			default:
				redirectUrl = request.getContextPath() + "/user/home";
				break;
			}
			response.sendRedirect(redirectUrl);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

}