package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.impl.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/user/chat", "/user/chat/send" })
public class UserChatController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
    private final ComplaintMessageServiceImpl msgService = new ComplaintMessageServiceImpl();
    private final NotificationServiceImpl notiService = new NotificationServiceImpl();
    private final UserServiceImpl userService = new UserServiceImpl(); // để tìm Admin

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        
        User user = (User) req.getAttribute("account");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int complaintId = Integer.parseInt(req.getParameter("complaintId"));
        Complaint complaint = complaintService.findById(complaintId);
        List<ComplaintMessage> messages = msgService.findByComplaintId(complaintId);

        req.setAttribute("complaint", complaint);
        req.setAttribute("messages", messages);

        req.getRequestDispatcher("/views/user/complaints/chat.jsp").forward(req, resp);
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//
//    
//        User user = (User) req.getAttribute("account");
//        if (user == null) {
//            resp.sendRedirect(req.getContextPath() + "/login");
//            return;
//        }
//
//        int complaintId = Integer.parseInt(req.getParameter("complaintId"));
//        String content = req.getParameter("content");
//
//        // ====== 1. Lưu tin nhắn ======
//        Complaint complaint = complaintService.findById(complaintId);
//        ComplaintMessage msg = ComplaintMessage.builder()
//                .complaint(complaint)
//                .sender(user)
//                .content(content)
//                .build();
//        msgService.insert(msg);
//
//        // ====== 2. Gửi thông báo cho Admin ======
//        try {
//            // Giả sử bạn có nhiều Admin → lấy admin đầu tiên hoặc theo điều kiện role = 'ADMIN'
//            List<User> admins = userService.getUsersByRole("ADMIN");
//            for (User admin : admins) {
//                Notification noti = Notification.builder()
//                        .user(admin) // admin nhận thông báo
//                        .message("Người dùng " + user.getUsername() + " đã gửi tin nhắn trong khiếu nại #" + complaintId)
//                        .relatedComplaint(complaint)
//                        .build();
//                notiService.insert(noti);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // ====== 3. Quay lại trang chat ======
//        resp.sendRedirect(req.getContextPath() + "/user/chat?complaintId=" + complaintId);
//    }
}
