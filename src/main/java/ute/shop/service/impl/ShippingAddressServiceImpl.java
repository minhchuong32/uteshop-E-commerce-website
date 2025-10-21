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
		List<ShippingAddress> list = dao.findByUserId(address.getUser().getUserId());
		// Nếu chưa có địa chỉ nào, mặc định luôn
        if (list.isEmpty()) {
        	address.setIsDefault(true);
        }

        // Nếu được chọn làm mặc định -> bỏ mặc định các địa chỉ khác
        if (address.getIsDefault()) {
            dao.unsetOtherDefaults(address.getUser().getUserId());
        }

		dao.insert(address);
	}

	@Override
	public void updateAddress(ShippingAddress address) {
		// 🟡 Nếu tick làm mặc định -> bỏ tick các địa chỉ khác
        if (address.getIsDefault()) {
            dao.unsetOtherDefaults(address.getUser().getUserId(), address.getAddressId());
        }
		dao.update(address);
	}

	@Override
	public void deleteAddress(int id) {
		ShippingAddress deleted = dao.findById(id);
	    if (deleted == null) return;

	    int userId = deleted.getUser().getUserId();
	    boolean wasDefault = deleted.getIsDefault();

	    // Xóa địa chỉ
	    dao.delete(id);

	    // Nếu xóa địa chỉ mặc định → đặt địa chỉ mới nhất còn lại làm mặc định
	    if (wasDefault) {
	        List<ShippingAddress> remaining = dao.findByUserId(userId);
	        if (!remaining.isEmpty()) {
	            ShippingAddress latest = remaining.get(0); // vì findByUserId ORDER BY createdAt DESC
	            latest.setIsDefault(true);
	            dao.update(latest);
	        }
	    }
	}

}
