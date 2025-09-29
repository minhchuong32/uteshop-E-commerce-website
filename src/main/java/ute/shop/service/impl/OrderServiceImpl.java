package ute.shop.service.impl;

import ute.shop.dao.IOrderDao;
import ute.shop.dao.impl.OrderDaoImpl;
import ute.shop.entity.Order;
import ute.shop.service.IOrderService;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements IOrderService {

    private final IOrderDao orderDao = new OrderDaoImpl();

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Optional<Order> getById(int id) {
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
