package ute.shop.dao;

import ute.shop.entity.Order;
import ute.shop.entity.User;

import java.util.List;

public interface IOrderDao {
    List<Order> getAll();
    Order getById(int id);
    boolean insert(Order order);
    void update(Order order);
    void delete(int id);
	List<Order> findByUser(User user);
	boolean hasPurchased(int userId, int productId);
	
	//Vendor dashboard
	long countOrdersByShop(int shopId);
	long countDistinctCustomersByShop(int shopId);
	List<Object[]> getOrderStatusCountByShop(int shopId);
	List<Object[]> getOrderTrendByShop(int shopId);
	
	List<Order> getOrdersByShopAndStatuses(int shopId, List<String> statuses);
	User getOrderShipper(Order order);
	long countAllOrders();
	List<Order> findRecentOrders(int limit);
}
