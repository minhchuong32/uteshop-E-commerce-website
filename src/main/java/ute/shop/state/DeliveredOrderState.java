package ute.shop.state;

/**
 * Trạng thái "Đã giao" — trạng thái cuối, không được phép làm gì thêm
 */
public class DeliveredOrderState implements IOrderState {

    @Override
    public void confirm(OrderContext context) {
        throw new IllegalStateException("Đơn đã giao, không thể thay đổi!");
    }

    @Override
    public void ship(OrderContext context) {
        throw new IllegalStateException("Đơn đã giao, không thể thay đổi!");
    }

    @Override
    public void deliver(OrderContext context) {
        throw new IllegalStateException("Đơn đã được giao thành công rồi!");
    }

    @Override
    public void cancel(OrderContext context) {
        throw new IllegalStateException(
            "Không thể hủy đơn đã giao thành công!");
    }

    @Override
    public String getStatusName() { return "Đã giao"; }
}