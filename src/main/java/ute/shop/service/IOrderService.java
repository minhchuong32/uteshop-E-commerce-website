package ute.shop.service;

import ute.shop.entity.Order;
import ute.shop.entity.User;

import java.util.List;
import java.util.Map;

public interface IOrderService {
	List<Order> getAll();
	Order save(Order order);
	Order getById(int id);

	void update(Order order);

	void delete(int id);

	List<Order> findByUser(User user);

	boolean hasPurchased(int userId, int productId);

	Order getById(Integer id);

	List<Order> getOrdersByUser(Integer userId);

	List<Order> getOrdersByUserAndStatus(int userId, String status);
	boolean updateStatus(int orderId, String status);

	// Vendor DashBoard
	long getTotalOrders(int shopId);

	long getTotalCustomers(int shopId);

	List<Object[]> getOrderStatusCountByShop(int shopId);

	List<Object[]> getOrderTrendByShop(int shopId);

	List<Order> getOrdersByShopAndStatuses(int shopId, List<String> statuses);

	User getOrderShipper(Order order);

	long countAllOrders();

	List<Order> findRecentOrders(int limit);
	
	List<Order> findAllForAdmin();
	
	//Vendor thong ke bo sung
		List<Object[]> getPaymentMethodStatsByShop(int shopId);
		List<Map<String, Object>> getReturnCancelRateByMonth(int shopId, int month, int year);
}
