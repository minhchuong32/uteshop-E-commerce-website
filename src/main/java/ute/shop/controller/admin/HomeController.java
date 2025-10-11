package ute.shop.controller.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.Notification;
import ute.shop.entity.User;
import ute.shop.service.impl.NotificationServiceImpl;

@WebServlet(urlPatterns = {"/admin/home"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Kiểm tra đăng nhập
        HttpSession session = req.getSession(false);
        User admin = (session != null) ? (User) session.getAttribute("account") : null;
        if (admin == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        // set att 
        List<Notification> notis = new NotificationServiceImpl().getUnreadByUserId(admin.getUserId());
        req.setAttribute("notifications", notis);

        // Lấy tham số page
        String page = req.getParameter("page");

        if (page != null) {
            switch (page) {
          
                case "users":
                    resp.sendRedirect(req.getContextPath() + "/admin/users");
                    return;
                case "products":
                    resp.sendRedirect(req.getContextPath() + "/admin/products");
                    return;
                case "shops":
                    resp.sendRedirect(req.getContextPath() + "/admin/shops");
                    return;
                case "deliveries":
                    resp.sendRedirect(req.getContextPath() + "/admin/deliveries");
                    return;
                case "complaints":
                    resp.sendRedirect(req.getContextPath() + "/admin/complaints");
                    return;
                case "revenue":
                    resp.sendRedirect(req.getContextPath() + "/admin/revenue");
                    return;
                case "stats":
                    resp.sendRedirect(req.getContextPath() + "/admin/stats");
                    return;
                case "settings":
                    resp.sendRedirect(req.getContextPath() + "/admin/settings");
                    return;
                default:
                    resp.sendRedirect(req.getContextPath() + "/admin/home");
                    return;
            }
        }

        // Nếu không có page → mặc định Dashboard
        resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        return;
    }
}
