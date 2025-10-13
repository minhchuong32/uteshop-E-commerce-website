package ute.shop.controller.vendors;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import ute.shop.entity.User;
import ute.shop.service.impl.NotificationServiceImpl;

import java.io.IOException;

@WebFilter("/vendor/*")  // hoặc "/user/*", "/shipper/*" 
public class NotificationFilter implements Filter {
    private final NotificationServiceImpl notificationService = new NotificationServiceImpl();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        if (session != null) {
            User account = (User) session.getAttribute("account");
            if (account != null) {
                int userId = account.getUserId();
                var notifications = notificationService.getAllByUserId(userId);
                long unreadCount = notifications.stream().filter(n -> !n.isRead()).count();

                req.setAttribute("notifications", notifications);
                req.setAttribute("unreadCount", unreadCount);
            }
        }

        chain.doFilter(request, response);
    }
}
