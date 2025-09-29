package ute.shop.service;

import ute.shop.entity.Order;
import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<Order> getAll();
    Optional<Order> getById(int id);
    void insert(Order order);
    void update(Order order);
    void delete(int id);
}
