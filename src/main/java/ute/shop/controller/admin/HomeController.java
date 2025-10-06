package ute.shop.controller.admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ute.shop.entity.User;

@WebServlet(urlPatterns = {"/admin/home"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Kiểm tra đăng nhập
        HttpSession session = req.getSession(false);
        User userLogin = (session != null) ? (User) session.getAttribute("account") : null;
        if (userLogin == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Lấy tham số page
        String page = req.getParameter("page");

        if (page != null) {
            switch (page) {
                case "users":
                    resp.sendRedirect(req.getContextPath() + "/admin/users");
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
        req.setAttribute("page", "dashboard");
        req.setAttribute("view", "/views/admin/dashboard.jsp");
        req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
    }
}
