package ute.shop.controller.shipper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.impl.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/shipper/notifications")
public class ShipperNotificationController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final NotificationServiceImpl notiService = new NotificationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

      
        User shipper = (User) req.getAttribute("account");

        List<Notification> notifications = notiService.getAllByUserId(shipper.getUserId());
        req.setAttribute("notifications", notifications);
        
        req.setAttribute("page", "notifications");
        req.setAttribute("view", "/views/shipper/notifications.jsp");

        req.getRequestDispatcher("/WEB-INF/decorators/shipper.jsp").forward(req, resp);
    }
}