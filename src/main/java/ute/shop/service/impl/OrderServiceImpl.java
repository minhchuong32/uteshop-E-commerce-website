package ute.shop.service.impl;

import ute.shop.dao.IOrderDao;
import ute.shop.dao.impl.OrderDaoImpl;
import ute.shop.entity.Order;
import ute.shop.entity.User;
import ute.shop.service.IOrderService;

import java.util.List;


public class OrderServiceImpl implements IOrderService {

    private final IOrderDao orderDao = new OrderDaoImpl();

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order getById(int id) {
        return orderDao.getById(id);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order);
    }

    @Override
    public void delete(int id) {
        orderDao.delete(id);
    }

	@Override
	public List<Order> findByUser(User user) {
		return orderDao.findByUser(user);
	}

	@Override
	public boolean hasPurchased(int userId, int productId) {
		return orderDao.hasPurchased(userId, productId);
	}
	
	//Vendor DashBoard
	@Override
    public long getTotalOrders(int shopId) {
        return orderDao.countOrdersByShop(shopId);
    }

    @Override
    public long getTotalCustomers(int shopId) {
        return orderDao.countDistinctCustomersByShop(shopId);
    }

	@Override
	public List<Object[]> getOrderStatusCountByShop(int shopId) {
		return orderDao.getOrderStatusCountByShop(shopId);
	}

	@Override
	public List<Object[]> getOrderTrendByShop(int shopId) {
		return orderDao.getOrderTrendByShop(shopId);
	}

	@Override
	public List<Order> getOrdersByShopAndStatuses(int shopId, List<String> statuses) {
		return orderDao.getOrdersByShopAndStatuses(shopId, statuses);
	}

	@Override
	public User getOrderShipper(Order order) {
		return orderDao.getOrderShipper(order);
	}

	@Override
	public long countAllOrders() {
		return orderDao.countAllOrders();
	}

	@Override
	public List<Order> findRecentOrders(int limit) {
		return orderDao.findRecentOrders(limit);
	}

	@Override
	public Order getById(Integer id) {
		return orderDao.getById(id);
	}

	@Override
	public List<Order> getOrdersByUser(Integer userId) {
		return orderDao.findByUserId(userId);
	}

	@Override
	public List<Order> getOrdersByUserAndStatus(int userId, String status) {
		return orderDao.getOrdersByUserAndStatus(userId, status);
	}

	@Override
	public List<Order> findAllForAdmin() {
		return orderDao.findAllForAdmin();
	}

	@Override
	public boolean updateStatus(int orderId, String status) {
		return orderDao.updateStatus(orderId, status);
	}

	@Override
	public Order save(Order order) {
		return orderDao.insert(order);
	}

	
}
