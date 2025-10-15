package ute.shop.controller.auth;

import java.io.IOException;

import ute.shop.service.IUserService;
import ute.shop.service.impl.UserServiceImpl;
import ute.shop.utils.Constant;
import ute.shop.utils.SendMail;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		// Lấy dữ liệu từ form
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");

		IUserService service = new UserServiceImpl();
		String alertMsg = "";

		// Kiểm tra pass khác nhau
		if (!password.equals(confirmPassword)) {
			alertMsg = "Mật khẩu xác nhận không khớp!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
			return;
		}

		// Kiểm tra email tồn tại
		if (service.checkExistEmail(email)) {
			alertMsg = "Email đã tồn tại!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
			return;
		}

		// Kiểm tra username tồn tại
		if (service.checkExistUsername(username)) {
			alertMsg = "Tài khoản đã tồn tại!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
			return;
		}

		// Thực hiện đăng ký
		boolean isSuccess = service.register(username, password, email, "User", "active");

		if (isSuccess) {
			SendMail sm = new SendMail();
			sm.sendMail(email, "Shopping.iotstar.vn - Đăng ký thành công",
					"<h3>Chào " + username + "!</h3>"
							+ "<p>Chúc mừng bạn đã đăng ký thành công tại <b>Shopping</b>.</p>"
							+ "<p>Vui lòng đăng nhập để sử dụng dịch vụ.</p>" + "<br><i>Thanks!</i>");
			resp.sendRedirect(req.getContextPath() + "/login");

		} else {
			alertMsg = "System error! Vui lòng thử lại sau.";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
		}
	}
}
