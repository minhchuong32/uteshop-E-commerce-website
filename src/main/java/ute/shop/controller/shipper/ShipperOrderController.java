package ute.shop.controller.shipper;

import ute.shop.entity.Delivery;
import ute.shop.entity.Notification;
import ute.shop.entity.User;
import ute.shop.service.IDeliveryService;
import ute.shop.service.impl.DeliveryServiceImpl;
import ute.shop.service.impl.NotificationServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {
        "/shipper/orders",
        "/shipper/orders/delete",
        "/shipper/assign"
})
public class ShipperOrderController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IDeliveryService deliveryService = new DeliveryServiceImpl();
    private final NotificationServiceImpl notificationService = new NotificationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        //  Trường hợp: Xóa đơn hàng
        if (uri.endsWith("/delete")) {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                Integer id = Integer.parseInt(idStr);
                deliveryService.delete(id);
            }
            resp.sendRedirect(req.getContextPath() + "/shipper/orders");
            return;
        }

        //  Trường hợp: Hiển thị danh sách đơn hàng
        HttpSession session = req.getSession(false);
        User shipperLogin = (session != null) ? (User) session.getAttribute("account") : null;

        if (shipperLogin == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Integer shipperId = shipperLogin.getUserId();

        //  Lấy các đơn đang tìm shipper hoặc đã gán nhưng chưa giao
        List<Delivery> unassigned = deliveryService.findUnassignedDeliveries();

        // Các đơn thuộc shipper hiện tại
        List<Delivery> shipperDeliveries = deliveryService.getByShipper(shipperId);

        //  Phân loại danh sách
        List<Delivery> delivering = shipperDeliveries.stream()
                .filter(d -> "Đang giao".equalsIgnoreCase(d.getStatus()) ||
                             "Đã gán".equalsIgnoreCase(d.getStatus()))
                .collect(Collectors.toList());

        List<Delivery> finished = shipperDeliveries.stream()
                .filter(d -> "Đã giao".equalsIgnoreCase(d.getStatus()) ||
                             "Hủy".equalsIgnoreCase(d.getStatus()))
                .collect(Collectors.toList());

        req.setAttribute("unassigned", unassigned);
        req.setAttribute("delivering", delivering);
        req.setAttribute("finished", finished);
        req.setAttribute("shipperId", shipperId);

        req.setAttribute("page", "orders");
        req.setAttribute("view", "/views/shipper/orders.jsp");
        req.getRequestDispatcher("/WEB-INF/decorators/shipper.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        //  Nhận đơn (assign)
        if (uri.endsWith("/assign")) {
            HttpSession session = req.getSession(false);
            User shipperLogin = (session != null) ? (User) session.getAttribute("account") : null;

            if (shipperLogin == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            String deliveryIdStr = req.getParameter("deliveryId");
            if (deliveryIdStr != null) {
                Integer deliveryId = Integer.parseInt(deliveryIdStr);
                Integer shipperId = shipperLogin.getUserId();

                deliveryService.assignToShipper(deliveryId, shipperId);
            }
            
            Delivery d = deliveryService.getById(Integer.parseInt(deliveryIdStr));
            if (d != null && d.getOrder() != null) {
                var order = d.getOrder();
                var shop = order.getShop();

                // Gửi cho User
                Notification notiUser = Notification.builder()
                        .user(order.getUser())
                        .title("Đơn hàng #" + order.getOrderId() + " đã được nhận bởi shipper")
                        .message("Shipper " + shipperLogin.getUsername() + " đã nhận đơn và sẽ sớm giao hàng cho bạn.")
                        .build();
                notificationService.insert(notiUser);

                // Gửi cho Vendor (chủ shop)
                if (shop != null && shop.getUser() != null) {
                    Notification notiVendor = Notification.builder()
                            .user(shop.getUser())
                            .title("Shipper đã nhận đơn hàng #" + order.getOrderId())
                            .message("Shipper " + shipperLogin.getUsername() + " đã nhận đơn hàng của bạn để giao.")
                            .build();
                    notificationService.insert(notiVendor);
                }
            }

            resp.sendRedirect(req.getContextPath() + "/shipper/orders");
            return;
        }

        //  Cập nhật trạng thái đơn hàng
        if (uri.endsWith("/orders")) {
            String deliveryIdStr = req.getParameter("deliveryId");
            String status = req.getParameter("status");

            if (deliveryIdStr != null && status != null) {
                Integer deliveryId = Integer.parseInt(deliveryIdStr);
                deliveryService.updateStatus(deliveryId, status);
                
                Delivery d = deliveryService.getById(deliveryId);
                if (d != null && d.getOrder() != null) {
                    var order = d.getOrder();
                    var shop = order.getShop();

                    String messageForUser = "Đơn hàng #" + order.getOrderId() + " hiện đang ở trạng thái: " + status;
                    String messageForVendor = "Shipper đã cập nhật trạng thái đơn hàng #" + order.getOrderId() + " thành: " + status;

                    // Gửi cho User
                    Notification notiUser = Notification.builder()
                            .user(order.getUser())
                            .title("Cập nhật đơn hàng #" + order.getOrderId())
                            .message(messageForUser)
                            .build();
                    notificationService.insert(notiUser);

                    // Gửi cho Vendor
                    if (shop != null && shop.getUser() != null) {
                        Notification notiVendor = Notification.builder()
                                .user(shop.getUser())
                                .title("Trạng thái đơn hàng #" + order.getOrderId() + " đã thay đổi")
                                .message(messageForVendor)
                                .build();
                        notificationService.insert(notiVendor);
                    }
                }
            }

            resp.sendRedirect(req.getContextPath() + "/shipper/orders");
        }
    }
}
