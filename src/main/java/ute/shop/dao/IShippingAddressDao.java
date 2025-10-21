package ute.shop.dao;

import java.util.List;

import ute.shop.entity.ShippingAddress;

public interface IShippingAddressDao {
	List<ShippingAddress> findByUserId(int userId);
	ShippingAddress findById(int id);
	void insert(ShippingAddress address);
	void update(ShippingAddress address);
	void delete(int id);
}
