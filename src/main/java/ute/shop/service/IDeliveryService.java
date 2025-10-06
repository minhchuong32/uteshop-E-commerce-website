package ute.shop.service;

import ute.shop.entity.Delivery;
import java.util.List;

public interface IDeliveryService {
    List<Delivery> getByShipper(Integer shipperId);
    Delivery getById(Integer id);
    Delivery save(Delivery delivery);
    void updateStatus(Integer deliveryId, String status);
    void delete(Integer id);
    List<Delivery> findAll();
    // thống kê hiệu suất (tỷ lệ % delivered)
    List<Object[]> getPerformanceStats();
}
