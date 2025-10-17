package ute.shop.service.impl;

import ute.shop.dao.IDeliveryDao;
import ute.shop.dao.IUserDao;
import ute.shop.dao.impl.DeliveryDaoImpl;
import ute.shop.dao.impl.UserDaoImpl;
import ute.shop.entity.Delivery;
import ute.shop.entity.User;
import ute.shop.service.IDeliveryService;

import java.util.ArrayList;
import java.util.List;

public class DeliveryServiceImpl implements IDeliveryService {

    private final IDeliveryDao deliveryDao = new DeliveryDaoImpl();
    private final IUserDao userDao = new UserDaoImpl();

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

    /**
     * Thống kê hiệu suất giao hàng của từng shipper
     * Trả về: [Tên Shipper, Tổng số đơn, Tỷ lệ thành công %]
     */
    @Override
    public List<Object[]> getPerformanceStats() {
        List<User> shippers = userDao.getUsersByRole("Shipper");
        List<Object[]> stats = new ArrayList<>();

        for (User shipper : shippers) {
            List<Delivery> deliveries = getByShipper(shipper.getUserId());

            long total = deliveries.size();
            long delivered = deliveries.stream()
                    .filter(d -> d.getStatus() != null &&
                            d.getStatus().trim().equalsIgnoreCase("Đã giao"))
                    .count();

            double successRate = (total == 0) ? 0.0 : ((double) delivered / total * 100.0);

            // Thêm dữ liệu vào danh sách
            stats.add(new Object[]{
                    shipper.getName(),   // 0: tên shipper
                    total,               // 1: tổng số đơn
                    Math.round(successRate * 100.0) / 100.0  // 2: làm tròn 2 chữ số
            });
        }

        return stats;
    }

	@Override
	public List<Delivery> findAll() {
		return deliveryDao.findAll();
	}
	
	//shipper dashboard
	@Override
    public long countByStatus(int shipperId, String status) {
        return deliveryDao.countByStatus(shipperId, status);
    }

    @Override
    public List<Object[]> getSuccessRateByMonth(int shipperId) {
        return deliveryDao.getSuccessRateByMonth(shipperId);
    }

    @Override
    public List<Object[]> getRecentDeliveries(int shipperId, int limit) {
        return deliveryDao.getRecentDeliveries(shipperId, limit);
    }

	@Override
	public List<Delivery> findUnassignedDeliveries() {
		// TODO Auto-generated method stub
		return deliveryDao.findUnassignedDeliveries();
	}

	@Override
	public void assignToShipper(Integer deliveryId, Integer shipperId) {
		deliveryDao.assignToShipper(deliveryId, shipperId);
	}

	@Override
	public long countAll() {
		return deliveryDao.countAll();
	}

	@Override
	public boolean insert(Delivery delivery) {
		return deliveryDao.insert(delivery);
	}

}
