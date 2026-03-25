package ute.shop.state;

/**
 * Trạng thái "Đang giao" — chỉ cho phép deliver
 */
public class ShippingOrderState implements IOrderState {

    @Override
    public void confirm(OrderContext context) {
        throw new IllegalStateException("Đơn đang được giao, không cần xác nhận!");
    }

    @Override
    public void ship(OrderContext context) {
        throw new IllegalStateException("Đơn đang được giao rồi!");
    }

    @Override
    public void deliver(OrderContext context) {
        // Cho phép: Đang giao → Đã giao
        context.setState(new DeliveredOrderState());
    }

    @Override
    public void cancel(OrderContext context) {
        throw new IllegalStateException(
            "Không thể hủy đơn khi đang trong quá trình giao hàng!");
    }

    @Override
    public String getStatusName() { return "Đang giao"; }
}