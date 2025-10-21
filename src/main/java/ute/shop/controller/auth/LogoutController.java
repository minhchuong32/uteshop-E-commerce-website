package ute.shop.controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/logout" })
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Cookie[] cookies = request.getCookies();
        
        // Tìm cookie jwt_token
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt_token".equals(cookie.getName())) {
                    // "Vô hiệu hóa" cookie bằng cách đặt thời gian sống của nó về 0
                    cookie.setMaxAge(0);
                    cookie.setPath("/"); // Đảm bảo path khớp với lúc tạo cookie
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        
        // Chuyển hướng người dùng về trang đăng nhập
        response.sendRedirect(request.getContextPath() + "/login");
    }
}