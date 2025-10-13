package ute.shop.dao;

import java.util.List;

import ute.shop.entity.Promotion;

public interface IPromotionDao {
	Promotion findById(int id);

	List<Promotion> findAll();

	List<Promotion> findValidByShop(int shopId);

	void insert(Promotion p);

	void update(Promotion p);

	void delete(int id);
}
