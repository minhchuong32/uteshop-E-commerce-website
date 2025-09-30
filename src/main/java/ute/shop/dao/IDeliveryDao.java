package ute.shop.dao;

import ute.shop.entity.Delivery;
import java.util.List;

public interface IDeliveryDao {
    Delivery findById(Integer id);
    List<Delivery> findByShipper(Integer shipperId);
    List<Delivery> findAll();
    Delivery save(Delivery delivery);
    void updateStatus(Integer deliveryId, String status);
    void delete(Integer id);
}
