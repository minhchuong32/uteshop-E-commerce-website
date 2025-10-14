package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.Notification;
import ute.shop.entity.User;
import ute.shop.service.impl.NotificationServiceImpl;
import ute.shop.service.INotificationService;

import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/notifications", "/admin/notifications/view"})
public class AdminNotificationController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final INotificationService notiService = new NotificationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        //  Xem toàn bộ danh sách thông báo
        if (uri.endsWith("/notifications")) {
            HttpSession session = req.getSession();
            User admin = (User) session.getAttribute("account");
            if (admin == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            req.setAttribute("notifications", notiService.getAllByUserId(admin.getUserId()));
            
            req.setAttribute("page", "complaints");
            req.setAttribute("view", "/views/admin/notifications.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
        }

        //  Khi click vào 1 thông báo
        else if (uri.endsWith("/view")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Notification noti = notiService.findById(id);

            if (noti != null) {
                // Đánh dấu là đã đọc
                noti.setRead(true);
                notiService.update(noti);

                // Chuyển tới khung chat khiếu nại tương ứng
                resp.sendRedirect(req.getContextPath()
                        + "/admin/complaints/chat?id=" + noti.getRelatedComplaint().getComplaintId());
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin/notifications");
            }
        }
    }
}
