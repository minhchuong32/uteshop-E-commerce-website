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

	List<Object[]> getSuccessRateByMonth(int shipperId);

	List<Object[]> getRecentDeliveries(int shipperId, int limit);

	long countByStatus(int shipperId, String status);

	List<Delivery> findUnassignedDeliveries();

	void assignToShipper(Integer deliveryId, Integer shipperId);

	long countAll();
}
