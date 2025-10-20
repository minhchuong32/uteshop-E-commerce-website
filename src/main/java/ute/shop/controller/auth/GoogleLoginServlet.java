package ute.shop.controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.utils.GoogleOAuthUtils;

@WebServlet(urlPatterns = "/login-google")
public class GoogleLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Lấy URL xác thực từ Google
            String authorizationUrl = GoogleOAuthUtils.getAuthorizationUrl();
            // Chuyển hướng người dùng đến URL đó
            response.sendRedirect(authorizationUrl);
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu có lỗi, chuyển về trang đăng nhập
            response.sendRedirect(request.getContextPath() + "/login"); 
        }
    }
}