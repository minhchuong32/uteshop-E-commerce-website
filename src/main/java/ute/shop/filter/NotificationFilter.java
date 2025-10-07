package ute.shop.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.impl.NotificationServiceImpl;
import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = {"/user/*", "/admin/*"})
public class NotificationFilter implements Filter {
    private final NotificationServiceImpl notiService = new NotificationServiceImpl();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        
        if (session != null) {
            User user = (User) session.getAttribute("account");
            if (user != null) {
                // Load notifications and set in request scope
                List<Notification> notifications = notiService.getUnreadByUserId(user.getUserId());
                request.setAttribute("notifications", notifications);
            }
        }
        
        chain.doFilter(request, response);
    }
}