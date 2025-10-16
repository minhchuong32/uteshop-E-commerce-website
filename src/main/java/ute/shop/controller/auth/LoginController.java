package ute.shop.controller.auth;

import java.io.IOException;
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
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService service = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===== Lấy cookie nếu có =====
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

        // ===== Gửi dữ liệu sang JSP =====
        request.setAttribute("savedEmail", savedEmail);
        request.setAttribute("savedPassword", savedPassword);

        // Forward đến login.jsp
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
        String remember = request.getParameter("remember");

        boolean isRememberMe = "on".equals(remember);

        // ===== Kiểm tra dữ liệu =====
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

        // ===== Xử lý login =====
        User user = service.login(email, password);

        if (user != null) {
            // Tạo session
            HttpSession session = request.getSession(true);
            session.setAttribute("account", user);

            // Ghi nhớ đăng nhập
            if (isRememberMe) {
                saveRememberMe(response, email, password);
            }

            // Điều hướng theo vai trò
            switch (user.getRole().toLowerCase()) {
                case "admin":
                    response.sendRedirect(request.getContextPath() + "/admin/home");
                    break;
                case "shipper":
                    response.sendRedirect(request.getContextPath() + "/shipper/home");
                    break;
                case "vendor":
                    response.sendRedirect(request.getContextPath() + "/vendor/home");
                    break;
                case "user":
                    response.sendRedirect(request.getContextPath() + "/user/home");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/web/home");
                    break;
            }

        } else {
            request.setAttribute("alert", "Email hoặc mật khẩu không đúng");
            request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
        }
    }

    // ===== Lưu cookie Remember Me =====
    private void saveRememberMe(HttpServletResponse response, String email, String password) {
        Cookie emailCookie = new Cookie("rememberEmail", email);
        emailCookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
        emailCookie.setPath("/");
        response.addCookie(emailCookie);

        Cookie passwordCookie = new Cookie("rememberPassword", password);
        passwordCookie.setMaxAge(7 * 24 * 60 * 60);
        passwordCookie.setPath("/");
        response.addCookie(passwordCookie);
    }
}
