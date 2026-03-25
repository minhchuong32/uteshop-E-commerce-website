package ute.shop.state;

/**
 * Trạng thái "Đã xác nhận" — chỉ cho phép ship hoặc cancel
 */
public class ConfirmedOrderState implements IOrderState {

    @Override
    public void confirm(OrderContext context) {
        throw new IllegalStateException("Đơn đã được xác nhận rồi!");
    }

    @Override
    public void ship(OrderContext context) {
        // Cho phép: Đã xác nhận → Đang giao
        context.setState(new ShippingOrderState());
    }

    @Override
    public void deliver(OrderContext context) {
        throw new IllegalStateException(
            "Không thể hoàn thành khi chưa bắt đầu giao!");
    }

    @Override
    public void cancel(OrderContext context) {
        // Vendor vẫn có thể hủy ở bước này
        context.setState(new CancelledOrderState());
    }

    @Override
    public String getStatusName() { return "Đã xác nhận"; }
}