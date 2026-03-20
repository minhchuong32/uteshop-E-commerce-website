package ute.shop.observer;

import ute.shop.entity.Notification;
import ute.shop.entity.Order;
import ute.shop.service.INotificationService;
import ute.shop.service.impl.NotificationServiceImpl;

public class NotificationObserver implements OrderEventObserver {

    private final INotificationService notiService = new NotificationServiceImpl();

    @Override
    public void onOrderStatusChanged(Order order, String newStatus, String actorName) {

        String title = "Cập nhật đơn hàng #" + order.getOrderId();
        String msgForUser = "Đơn hàng của bạn hiện đang ở trạng thái: " + newStatus;
        String msgForVendor = "Đơn hàng #" + order.getOrderId() + " đổi sang: " + newStatus
                              + " (bởi " + actorName + ")";

        // Gửi cho khách hàng
        if (order.getUser() != null) {
            notiService.insert(Notification.builder()
                .user(order.getUser())
                .title(title)
                .message(msgForUser)
                .build());
        }

        // Gửi cho chủ shop
        if (order.getShop() != null && order.getShop().getUser() != null) {
            notiService.insert(Notification.builder()
                .user(order.getShop().getUser())
                .title(title)
                .message(msgForVendor)
                .build());
        }
    }
}