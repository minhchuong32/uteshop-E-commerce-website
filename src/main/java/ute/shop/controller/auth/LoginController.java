package ute.shop.controller.auth;

import java.io.IOException;

import ute.shop.utils.Constant;
import ute.shop.models.User;
import ute.shop.service.IUserService;
import ute.shop.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IUserService service = new UserServiceImpl();

	/**
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward tới login.jsp trong WEB-INF
		request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");

		boolean isRememberMe = "on".equals(remember);

		if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
			request.setAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng");
			request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
			return;
		}

		User user = service.login(email, password);
		if (user != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("account", user);

			if (isRememberMe) {
				saveRememberMe(response, email);
			}
			response.sendRedirect(request.getContextPath() + "/waiting");
		} else {
			request.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
			request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
		}
	
	}

	private void saveRememberMe(HttpServletResponse response, String email) {
		// TODO Auto-generated method stub
		Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, email); // set cookie (name, val)
		cookie.setMaxAge(30 * 60); // 30min
		response.addCookie(cookie);
	}

}
