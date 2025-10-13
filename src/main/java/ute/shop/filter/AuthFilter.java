package ute.shop.filter;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.shop.entity.User;

@WebFilter(urlPatterns = {"/admin/*", "/user/*", "/vendor/*", "/shipper/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        String uri = req.getRequestURI();
        
        // Cho phép AJAX lấy biến thể
        if (uri.endsWith("/login") 
                || uri.contains("/assets/") 
                || uri.endsWith("/variants")) { 
            chain.doFilter(request, response);
            return;
        }
        
        
        // Nếu chưa login → chuyển hướng về /login
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
                
        // Nếu đã login → kiểm tra quyền truy cập (role)
        User user = (User) session.getAttribute("account");
        String role = user.getRole();

        // Kiểm tra quyền truy cập phù hợp với role
        if (uri.startsWith(req.getContextPath() + "/admin") && !"Admin".equals(role)) {
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }

        if (uri.startsWith(req.getContextPath() + "/vendor") && !"Vendor".equals(role)) {
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }

        if (uri.startsWith(req.getContextPath() + "/shipper") && !"Shipper".equals(role)) {
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }

        if (uri.startsWith(req.getContextPath() + "/user") && !"User".equals(role)) {
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }

        // Nếu hợp lệ → cho phép đi tiếp
        chain.doFilter(request, response);
    }
}
