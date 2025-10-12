package ute.shop.service.impl;

import ute.shop.dao.IShopDao;
import ute.shop.dao.impl.ShopDaoImpl;
import ute.shop.entity.Shop;
import ute.shop.service.IShopService;

import java.util.List;

public class ShopServiceImpl implements IShopService {
    private final IShopDao shopDAO = new ShopDaoImpl();

    @Override
    public List<Shop> getAll() {
        return shopDAO.getAll();
    }

    @Override
    public Shop getById(int id) {
        return shopDAO.getById(id);
    }

    @Override
    public void insert(Shop shop) {
        shopDAO.insert(shop);
    }

    @Override
    public void update(Shop shop) {
        shopDAO.update(shop);
    }

    @Override
    public void delete(int id) {
        shopDAO.delete(id);
    }
    
    @Override
    public Shop findByUserId(int id) {
        return shopDAO.findByUserId(id);
    }

	@Override
	public Shop getReferenceById(int id) {
		return shopDAO.getReferenceById(id);
	}

}
