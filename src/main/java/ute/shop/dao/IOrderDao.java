package ute.shop.dao;

import ute.shop.entity.Order;
import java.util.List;
import java.util.Optional;

public interface IOrderDao {
    List<Order> getAll();
    Optional<Order> getById(int id);
    void insert(Order order);
    void update(Order order);
    void delete(int id);
}
