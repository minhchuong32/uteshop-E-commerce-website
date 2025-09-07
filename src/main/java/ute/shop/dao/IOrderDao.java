package ute.shop.dao;

import java.util.List;
import ute.shop.models.Order;

public interface IOrderDao {
    List<Order> getAll();
    Order getById(int id);
    void insert(Order order);
    void update(Order order);
    void delete(int id);
}
