package ute.shop.service;

import java.util.List;

import ute.shop.entity.Promotion;

public interface IPromotionService {
	Promotion findById(int id);
	
	List<Promotion> findAll();

	List<Promotion> getValidPromotionsByShop(int shopId);

	void insert(Promotion p);

	void update(Promotion p);

	void delete(int id);
	
	long countAll();
}
