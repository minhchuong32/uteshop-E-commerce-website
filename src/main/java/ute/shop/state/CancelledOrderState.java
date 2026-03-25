package ute.shop.state;

/**
 * Trạng thái "Đã hủy" — trạng thái cuối, không được phép làm gì
 */
public class CancelledOrderState implements IOrderState {

    @Override
    public void confirm(OrderContext context) {
        throw new IllegalStateException("Đơn đã hủy, không thể xác nhận!");
    }

    @Override
    public void ship(OrderContext context) {
        throw new IllegalStateException("Đơn đã hủy, không thể giao!");
    }

    @Override
    public void deliver(OrderContext context) {
        throw new IllegalStateException("Đơn đã hủy, không thể hoàn thành!");
    }

    @Override
    public void cancel(OrderContext context) {
        throw new IllegalStateException("Đơn đã bị hủy rồi!");
    }

    @Override
    public String getStatusName() { return "Đã hủy"; }
}