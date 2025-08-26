package ute.shop.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward tới register.jsp trong WEB-INF
		request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Lấy dữ liệu từ form
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// TODO: Thực hiện validate và lưu user vào DB
		// Ví dụ tạm thời: in ra console
		System.out.println("Register Info: " + username + " - " + email);

		// Sau khi đăng ký thành công thì chuyển hướng về login
		response.sendRedirect(request.getContextPath() + "/login");
	}
}
