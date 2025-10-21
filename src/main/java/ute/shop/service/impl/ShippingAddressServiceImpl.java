package ute.shop.service.impl;

import java.util.List;

import ute.shop.dao.IShippingAddressDao;
import ute.shop.dao.impl.ShippingAddressDaoImpl;
import ute.shop.entity.ShippingAddress;
import ute.shop.service.IShippingAddressService;

public class ShippingAddressServiceImpl implements IShippingAddressService {
	private final IShippingAddressDao dao = new ShippingAddressDaoImpl();
	@Override
	public List<ShippingAddress> getAddressesByUser(int userId) {
		return dao.findByUserId(userId);
	}

	@Override
	public ShippingAddress getById(int id) {
		return dao.findById(id);
	}

	@Override
	public void addAddress(ShippingAddress address) {
		dao.insert(address);
	}

	@Override
	public void updateAddress(ShippingAddress address) {
		dao.update(address);
	}

	@Override
	public void deleteAddress(int id) {
		dao.delete(id);
	}

}
