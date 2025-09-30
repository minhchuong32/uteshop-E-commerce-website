package ute.shop.controller.shipper;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ute.shop.entity.User;

@WebServlet(urlPatterns = {"/shipper/home"})
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
                case "orders":
                    resp.sendRedirect(req.getContextPath() + "/shipper/orders");
                    return;
                case "settings":
                    resp.sendRedirect(req.getContextPath() + "/shipper/settings");
                    return;
                default:
                    resp.sendRedirect(req.getContextPath() + "/shipper/home");
                    return;
            }
        }

        // Nếu không có page → mặc định Dashboard
        req.setAttribute("page", "dashboard");
        req.setAttribute("view", "/views/shipper/dashboard.jsp");
        req.getRequestDispatcher("/WEB-INF/decorators/shipper.jsp").forward(req, resp);
    }
}
