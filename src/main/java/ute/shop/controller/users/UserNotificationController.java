package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.impl.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/user/notifications", "/user/notifications/view" })
public class UserNotificationController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final NotificationServiceImpl notiService = new NotificationServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        
        User user = (User) req.getAttribute("account");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String uri = req.getRequestURI();

        // ======================== 1Click "Xem tất cả" ========================
        if (uri.endsWith("/notifications")) {
            List<Notification> notifications = notiService.getAllByUserId(user.getUserId());
            req.setAttribute("notifications", notifications);
            req.getRequestDispatcher("/views/user/notifications.jsp").forward(req, resp);
            return;
        }

        // ======================== Click vào 1 thông báo cụ thể ========================
        if (uri.endsWith("/notifications/view")) {
            String notiIdParam = req.getParameter("id");
            if (notiIdParam != null) {
                try {
                    int notiId = Integer.parseInt(notiIdParam);
                    Notification noti = notiService.findById(notiId);

                    // Nếu có complaint liên kết
                    if (noti != null && noti.getRelatedComplaint() != null) {
                        // đánh dấu là đã đọc
                        noti.setRead(true);
                        notiService.update(noti);

                        // chuyển hướng đến trang chat khiếu nại tương ứng
                        resp.sendRedirect(req.getContextPath()
                                + "/user/chat?complaintId=" + noti.getRelatedComplaint().getComplaintId());
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi ID thông báo không hợp lệ: " + notiIdParam);
                }
            }

            // fallback: nếu không có complaintId hợp lệ
            resp.sendRedirect(req.getContextPath() + "/user/notifications");
        }
    }
}
