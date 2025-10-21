package ute.shop.controller.error;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie; // Import Cookie
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.utils.JwtUtil; // Import JwtUtil

@WebServlet("/access-denied")
public class AccessDeniedController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Phương thức này chỉ hiển thị trang, không cần thay đổi
        request.getRequestDispatcher("/views/error/access-denied.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = null;
        Cookie[] cookies = req.getCookies();
        String redirectUrl = req.getContextPath() + "/login"; // Mặc định về trang login

        // 1. Tìm JWT token trong cookie
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        
        //  Nếu có token hợp lệ, đọc vai trò từ token
        if (token != null && JwtUtil.validateToken(token)) {
            String role = JwtUtil.extractRole(token); // Lấy vai trò trực tiếp từ token
            
            //  Chuyển hướng dựa trên vai trò
            if (role != null) {
                switch (role.toLowerCase()) {
                    case "admin":
                        redirectUrl = req.getContextPath() + "/admin/home";
                        break;
                    case "vendor":
                        redirectUrl = req.getContextPath() + "/vendor/home";
                        break;
                    case "user":
                        redirectUrl = req.getContextPath() + "/user/home";
                        break;
                    case "shipper":
                        redirectUrl = req.getContextPath() + "/shipper/home";
                        break;
                    // Nếu vai trò không khớp, vẫn về trang login
                }
            }
        }
        
        //  Thực hiện chuyển hướng
        resp.sendRedirect(redirectUrl);
    }
}