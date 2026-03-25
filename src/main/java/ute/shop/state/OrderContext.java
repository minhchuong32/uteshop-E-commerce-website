package ute.shop.state;

import ute.shop.entity.Order;
import ute.shop.service.IOrderService;

/**
 * Context — giữ trạng thái hiện tại và ủy quyền các hành động cho State
 */
public class OrderContext {

    private IOrderState currentState;
    private final Order order;
    private final IOrderService orderService;

    public OrderContext(Order order, IOrderService orderService) {
        this.order       = order;
        this.orderService = orderService;
        // Khởi tạo state từ trạng thái hiện tại trong DB
        this.currentState = OrderStateFactory.fromStatus(order.getStatus());
    }

    // Ủy quyền hành động cho State hiện tại
    public void confirm() { currentState.confirm(this); }
    public void ship()    { currentState.ship(this); }
    public void deliver() { currentState.deliver(this); }
    public void cancel()  { currentState.cancel(this); }

    // State tự gọi để thay đổi trạng thái
    public void setState(IOrderState newState) {
        this.currentState = newState;
        // Cập nhật DB ngay khi đổi trạng thái
        order.setStatus(newState.getStatusName());
        orderService.update(order);
        System.out.println("[STATE] Đơn #" + order.getOrderId()
                + " chuyển sang: " + newState.getStatusName());
    }

    public Order getOrder()             { return order; }
    public IOrderState getCurrentState(){ return currentState; }
}