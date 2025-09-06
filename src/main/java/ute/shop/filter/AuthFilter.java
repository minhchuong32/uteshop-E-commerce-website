//package ute.shop.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter("/admin/*")   // chặn tất cả URL bắt đầu bằng /admin/
//public class AuthFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse resp = (HttpServletResponse) response;
//
//        HttpSession session = req.getSession(false);
//
//        // Kiểm tra user trong session
//        if (session != null && session.getAttribute("user") != null) {
//            // Cho đi tiếp (đã login)
//            chain.doFilter(request, response);
//        } else {
//            // Nếu chưa login, chuyển về trang login
//            resp.sendRedirect(req.getContextPath() + "/login");
//        }
//    }
//}
