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
    private IUserService service = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward tới login.jsp
        request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        boolean isRememberMe = "on".equals(remember);

        //  Kiểm tra dữ liệu nhập vào
        if ((email == null || email.trim().isEmpty()) && (password == null || password.trim().isEmpty())) {
            request.setAttribute("alert", "Vui lòng nhập Email và Mật khẩu");
            request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
            return;
        }

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("alert", "Email không được để trống");
            request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("alert", "Mật khẩu không được để trống");
            request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
            return;
        }

        //  Xử lý login
        User user = service.login(email, password);
        if (user != null) {
            // Login thành công
            HttpSession session = request.getSession(true);
            session.setAttribute("account", user);

            // Nếu chọn "Remember me" thì lưu cookie
            if (isRememberMe) {
                saveRememberMe(response, email);
            }

            // Điều hướng sau khi login thành công
            response.sendRedirect(request.getContextPath() + "/waiting");

        } else {
            // Login thất bại
            request.setAttribute("alert", "Email hoặc mật khẩu không đúng");
            request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
        }
    }

    //  Hàm lưu cookie remember me
    private void saveRememberMe(HttpServletResponse response, String email) {
        Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, email);
        cookie.setMaxAge(30 * 60); // 30 phút
        cookie.setPath("/");       // cookie dùng cho toàn bộ app
        response.addCookie(cookie);
    }
}
