package ute.shop.service.impl;

import java.util.List;
import ute.shop.dao.IOrderDao;
import ute.shop.dao.impl.OrderDaoImpl;
import ute.shop.models.Order;
import ute.shop.service.IOrderService;

public class OrderServiceImpl implements IOrderService {

    private IOrderDao orderDao = new OrderDaoImpl();

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order getById(int id) {
        return orderDao.getById(id);
    }

    @Override
    public void insert(Order order) {
        orderDao.insert(order);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order);
    }

    @Override
    public void delete(int id) {
        orderDao.delete(id);
    }
}
