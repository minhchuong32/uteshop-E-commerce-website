package ute.shop.service;

import java.util.List;
import ute.shop.models.Order;

public interface IOrderService {
    List<Order> getAll();
    Order getById(int id);
    void insert(Order order);
    void update(Order order);
    void delete(int id);
}
