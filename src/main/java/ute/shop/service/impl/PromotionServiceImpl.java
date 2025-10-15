package ute.shop.service.impl;

import java.util.List;

import ute.shop.dao.IPromotionDao;
import ute.shop.dao.impl.PromotionDaoImpl;
import ute.shop.entity.Promotion;
import ute.shop.service.IPromotionService;

public class PromotionServiceImpl implements IPromotionService {
	private final IPromotionDao promoDao = new PromotionDaoImpl();

	@Override
	public Promotion findById(int id) {
		return promoDao.findById(id);
	}

	@Override
	public List<Promotion> getValidPromotionsByShop(int shopId) {
		return promoDao.findValidByShop(shopId);
	}

	@Override
	public void insert(Promotion p) {
		promoDao.insert(p);
	}

	@Override
	public void update(Promotion p) {
		promoDao.update(p);
	}

	@Override
	public void delete(int id) {
		promoDao.delete(id);
	}

	@Override
	public List<Promotion> findAll() {
		return promoDao.findAll();
	}

	@Override
	public long countAll() {
		return promoDao.countAll();
	}

	@Override
	public List<Promotion> getValidPromotionsByProduct(int productId) {
		return promoDao.findValidByProduct(productId);
	}

}
