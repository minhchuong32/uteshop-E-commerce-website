package ute.shop.observer;

import ute.shop.entity.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderEventPublisher {

    private static final OrderEventPublisher INSTANCE = new OrderEventPublisher();
    private final List<OrderEventObserver> observers = new ArrayList<>();

    private OrderEventPublisher() {}

    public static OrderEventPublisher getInstance() {
        return INSTANCE;
    }

    public void subscribe(OrderEventObserver observer) {
        observers.add(observer);
    }

    // Gọi hàm này khi trạng thái đơn hàng thay đổi
    public void publish(Order order, String newStatus, String actorName) {
        for (OrderEventObserver observer : observers) {
            observer.onOrderStatusChanged(order, newStatus, actorName);
        }
    }
}