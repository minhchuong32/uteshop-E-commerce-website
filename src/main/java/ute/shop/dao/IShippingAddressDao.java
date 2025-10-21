package ute.shop.dao;

import java.util.List;

import ute.shop.entity.ShippingAddress;

public interface IShippingAddressDao {
	List<ShippingAddress> findByUserId(int userId);
	ShippingAddress findById(int id);
	// Bỏ mặc định các địa chỉ khác
    void unsetOtherDefaults(int userId);
    // Bỏ mặc định trừ chính địa chỉ đang chỉnh sửa
    void unsetOtherDefaults(int userId, int excludeId);
	void insert(ShippingAddress address);
	void update(ShippingAddress address);
	void delete(int id);
	
}
