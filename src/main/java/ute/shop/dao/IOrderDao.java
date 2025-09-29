package ute.shop.dao;

import ute.shop.entity.Order;
import ute.shop.entity.User;

import java.util.List;
import java.util.Optional;

public interface IOrderDao {
    List<Order> getAll();
    Optional<Order> getById(int id);
    boolean insert(Order order);
    void update(Order order);
    void delete(int id);
	List<Order> findByUser(User user);
}
