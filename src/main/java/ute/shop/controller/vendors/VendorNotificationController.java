package ute.shop.controller.vendors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.impl.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/vendor/notifications")
public class VendorNotificationController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final NotificationServiceImpl notiService = new NotificationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User vendor = (User) session.getAttribute("account");

        if (vendor == null || !"VENDOR".equalsIgnoreCase(vendor.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<Notification> notifications = notiService.getAllByUserId(vendor.getUserId());
        req.setAttribute("notifications", notifications);
        
        req.setAttribute("page", "notifications");
        req.setAttribute("view", "/views/vendor/notifications.jsp");

        req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
    }
}