package ute.shop.service.impl;

import java.util.List;
import ute.shop.dao.IShopDao;
import ute.shop.dao.impl.ShopDaoImpl;
import ute.shop.models.Shop;
import ute.shop.service.IShopService;

public class ShopServiceImpl implements IShopService {
    private IShopDao shopDAO = new ShopDaoImpl();

	@Override
	public List<Shop> getAll() {
		// TODO Auto-generated method stub
		return shopDAO.getAll();
	}

	@Override
	public Shop getById(int id) {
		// TODO Auto-generated method stub
		
		return shopDAO.getById(id);
	}

	@Override
	public void insert(Shop shop) {
		// TODO Auto-generated method stub
		shopDAO.insert(shop);
		
	}

	@Override
	public void update(Shop shop) {
		// TODO Auto-generated method stub
		shopDAO.update(shop);
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		shopDAO.delete(id);
		
	}

 
}
