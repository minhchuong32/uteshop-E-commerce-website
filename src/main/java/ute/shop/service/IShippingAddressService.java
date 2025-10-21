package ute.shop.service;

import java.util.List;

import ute.shop.entity.ShippingAddress;

public interface IShippingAddressService {
	List<ShippingAddress> getAddressesByUser(int userId);
    ShippingAddress getById(int id);
    void addAddress(ShippingAddress address);
    void updateAddress(ShippingAddress address);
    void deleteAddress(int id);
}
