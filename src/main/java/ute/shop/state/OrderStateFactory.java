package ute.shop.state;

/**
 * Factory để tạo State object từ chuỗi trạng thái trong DB
 */
public class OrderStateFactory {

    public static IOrderState fromStatus(String status) {
        if (status == null) return new NewOrderState();
        return switch (status) {
            case "Mới",
                 "Chờ thanh toán VNPAY",
                 "Chờ thanh toán MOMO" -> new NewOrderState();
            case "Đã xác nhận"         -> new ConfirmedOrderState();
            case "Đang giao"            -> new ShippingOrderState();
            case "Đã giao"              -> new DeliveredOrderState();
            case "Đã hủy"              -> new CancelledOrderState();
            default                     -> new NewOrderState();
        };
    }
}