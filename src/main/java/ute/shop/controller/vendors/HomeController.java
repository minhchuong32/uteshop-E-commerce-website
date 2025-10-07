package ute.shop.controller.vendors;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ute.shop.entity.User;

@WebServlet(urlPatterns = {"/vendor/home"})
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
                case "products":
                    resp.sendRedirect(req.getContextPath() + "/vendor/products");
                    return;
                case "orders":
                    resp.sendRedirect(req.getContextPath() + "/vendor/orders");
                    return;
                case "stats":
                    resp.sendRedirect(req.getContextPath() + "/vendor/stats");
                    return;
                case "settings":
                    resp.sendRedirect(req.getContextPath() + "/vendor/settings");
                    return;
                default:
                    resp.sendRedirect(req.getContextPath() + "/vendor/home");
                    return;
            }
        }

        // Nếu không có page → mặc định Dashboard
        req.setAttribute("page", "dashboard");
        req.setAttribute("view", "/views/vendor/dashboard.jsp");
        req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
    }
}
