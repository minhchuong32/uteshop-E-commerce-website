package ute.shop.state;

/**
 * Trạng thái "Mới" — chỉ cho phép confirm hoặc cancel
 */
public class NewOrderState implements IOrderState {

    @Override
    public void confirm(OrderContext context) {
        // Cho phép: Mới → Đã xác nhận
        context.setState(new ConfirmedOrderState());
    }

    @Override
    public void ship(OrderContext context) {
        throw new IllegalStateException(
            "Không thể giao hàng khi đơn chưa được xác nhận!");
    }

    @Override
    public void deliver(OrderContext context) {
        throw new IllegalStateException(
            "Không thể hoàn thành khi đơn chưa được giao!");
    }

    @Override
    public void cancel(OrderContext context) {
        // Cho phép: Mới → Đã hủy
        context.setState(new CancelledOrderState());
    }

    @Override
    public String getStatusName() { return "Mới"; }
}