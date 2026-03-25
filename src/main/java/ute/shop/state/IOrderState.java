package ute.shop.state;

/**
 * STATE PATTERN — Interface chung cho tất cả trạng thái đơn hàng
 * Mỗi trạng thái tự biết mình được phép làm gì
 */
public interface IOrderState {

    /** Xác nhận đơn — chỉ hợp lệ ở trạng thái "Mới" */
    void confirm(OrderContext context);

    /** Giao cho shipper — chỉ hợp lệ ở trạng thái "Đã xác nhận" */
    void ship(OrderContext context);

    /** Hoàn thành giao hàng — chỉ hợp lệ ở trạng thái "Đang giao" */
    void deliver(OrderContext context);

    /** Hủy đơn — chỉ hợp lệ ở một số trạng thái nhất định */
    void cancel(OrderContext context);

    /** Tên trạng thái để lưu DB */
    String getStatusName();
}