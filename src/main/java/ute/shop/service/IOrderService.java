package ute.shop.service;

import ute.shop.entity.Order;
import ute.shop.entity.User;

import java.util.List;

public interface IOrderService {
    List<Order> getAll();
    Order getById(int id);
    boolean insert(Order order);
    void update(Order order);
    void delete(int id);
    List<Order> findByUser(User user);
    boolean hasPurchased(int userId, int productId);
    
    //Vendor DashBoard
    long getTotalOrders(int shopId);
    long getTotalCustomers(int shopId);
	List<Object[]> getOrderStatusCountByShop(int shopId);
	List<Object[]> getOrderTrendByShop(int shopId);
}
