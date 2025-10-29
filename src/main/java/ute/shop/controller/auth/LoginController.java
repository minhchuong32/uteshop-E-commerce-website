package ute.shop.controller.auth;

import java.io.IOException;
import java.util.regex.Pattern;

import ute.shop.utils.Constant;
import ute.shop.entity.User;
import ute.shop.service.IUserService;
import ute.shop.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.utils.JwtUtil;

@WebServlet(urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IUserService service = new UserServiceImpl();

	// Regex kiểm tra đúng định dạng Gmail
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String alert = request.getParameter("alert");
		if (alert != null) {
			String message;
			switch (alert) {
			case "session_expired":
				message = "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại!";
				break;
			case "user_not_found":
				message = "Người dùng không tồn tại trong hệ thống.";
				break;
			case "invalid_token":
				message = "Có lỗi xác thực. Vui lòng đăng nhập lại.";
				break;
			default:
				message = "Đã xảy ra lỗi không mong muốn.";
				break;
			}
			request.setAttribute("alert", message);
		}

		// Lấy cookie nếu có
		Cookie[] cookies = request.getCookies();
		String savedEmail = "";
		String savedPassword = "";

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				switch (cookie.getName()) {
				case "rememberEmail":
					savedEmail = cookie.getValue();
					break;
				case "rememberPassword":
					savedPassword = cookie.getValue();
					break;
				}
			}
		}

		request.setAttribute("savedEmail", savedEmail);
		request.setAttribute("savedPassword", savedPassword);

		request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// ==== Kiểm tra dữ liệu đầu vào ====
		if (email == null || email.trim().isEmpty()) {
			request.setAttribute("alert", "Email không được để trống");
			request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
			return;
		}

		if (!EMAIL_PATTERN.matcher(email).matches()) {
			request.setAttribute("alert", "Vui lòng nhập đúng định dạng Gmail (ví dụ: example@gmail.com)");
			request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
			return;
		}

		if (password == null || password.trim().isEmpty()) {
			request.setAttribute("alert", "Mật khẩu không được để trống");
			request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
			return;
		}

		// ==== Xử lý đăng nhập ====
		User user = service.login(email, password);

		if (user == null) {
			request.setAttribute("alert", "Email hoặc mật khẩu không đúng");
			request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
			return;
		}

		String status = user.getStatus();
		if ("banned".equals(status)) {
			request.setAttribute("alert", "Tài khoản của bạn đã bị khóa. Vui lòng liên hệ quản trị viên!");
			request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
			return;
		}
		
		// Lấy giá trị của checkbox "Ghi nhớ tôi"
	    String remember = request.getParameter("remember");

	    // ===== TẠO COOKIE GHI NHỚ TÀI KHOẢN  =====
	    if ("true".equals(remember)) {
	        // Nếu người dùng chọn "Ghi nhớ", tạo cookie cho email và password
	        Cookie emailCookie = new Cookie("rememberEmail", user.getEmail());
	        Cookie passwordCookie = new Cookie("rememberPassword", password);
	        
	        // Đặt thời gian sống cho cookie (1 ngày)
	        emailCookie.setMaxAge(24 * 60 * 60);
	        passwordCookie.setMaxAge(24 * 60 * 60);
	        
	        response.addCookie(emailCookie);
	        response.addCookie(passwordCookie);
	    } else {
	        // Nếu người dùng không chọn, xóa cookie cũ (nếu có)
	        Cookie emailCookie = new Cookie("rememberEmail", "");
	        Cookie passwordCookie = new Cookie("rememberPassword", "");
	        
	        emailCookie.setMaxAge(0); // Set max age = 0 để xóa cookie
	        passwordCookie.setMaxAge(0);
	        
	        response.addCookie(emailCookie);
	        response.addCookie(passwordCookie);
	    }

		// ===== Tạo mới JWT token và cookie =====
		Cookie oldJwtCookie = new Cookie("jwt_token", "");
		oldJwtCookie.setMaxAge(0);
		oldJwtCookie.setPath("/");
		oldJwtCookie.setHttpOnly(true);
		response.addCookie(oldJwtCookie);

		String jwt = JwtUtil.generateToken(user);
		Cookie jwtCookie = new Cookie("jwt_token", jwt);
		jwtCookie.setMaxAge(24 * 60 * 60);
		jwtCookie.setPath("/");
		jwtCookie.setHttpOnly(true);
		response.addCookie(jwtCookie);

		// ===== Điều hướng theo vai trò =====
		String context = request.getContextPath();
		String role = user.getRole() != null ? user.getRole().toLowerCase() : "";

		switch (role) {
		case "admin" -> response.sendRedirect(context + "/admin/home");
		case "shipper" -> response.sendRedirect(context + "/shipper/home");
		case "vendor", "user" -> response.sendRedirect(context + "/user/home");
		default -> response.sendRedirect(context + "/web/home");
		}
		return;
	}
}
