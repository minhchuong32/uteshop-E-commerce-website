package ute.shop.observer;

import ute.shop.entity.Order;

public interface OrderEventObserver {
    void onOrderStatusChanged(Order order, String newStatus, String actorName);
}