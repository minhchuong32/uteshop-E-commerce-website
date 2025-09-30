package ute.shop.service.impl;

import ute.shop.dao.IDeliveryDao;
import ute.shop.dao.impl.DeliveryDaoImpl;
import ute.shop.entity.Delivery;
import ute.shop.service.IDeliveryService;

import java.util.List;

public class DeliveryServiceImpl implements IDeliveryService {

    private final IDeliveryDao deliveryDao = new DeliveryDaoImpl();

    @Override
    public List<Delivery> getByShipper(Integer shipperId) {
        return deliveryDao.findByShipper(shipperId);
    }

    @Override
    public Delivery getById(Integer id) {
        return deliveryDao.findById(id);
    }

    @Override
    public Delivery save(Delivery delivery) {
        return deliveryDao.save(delivery);
    }

    @Override
    public void updateStatus(Integer deliveryId, String status) {
        deliveryDao.updateStatus(deliveryId, status);
    }

    @Override
    public void delete(Integer id) {
        deliveryDao.delete(id);
    }
}
