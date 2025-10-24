package ute.shop.dao;

import ute.shop.entity.Order;
import ute.shop.entity.User;

import java.util.List;
import java.util.Map;

public interface IOrderDao {
	List<Order> getAll();

	Order getById(int id);
	Order insert(Order order);

	void update(Order order);

	void delete(int id);

	List<Order> findByUser(User user);

	boolean hasPurchased(int userId, int productId);

	Order getById(Integer id);

	List<Order> findByUserId(Integer userId);

	List<Order> getOrdersByUserAndStatus(int userId, String status);
	boolean updateStatus(int orderId, String status);
	boolean updateStatusForOrders(List<Integer> orderIds, String status);


	// Vendor dashboard
	long countOrdersByShop(int shopId);

	long countDistinctCustomersByShop(int shopId);

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
