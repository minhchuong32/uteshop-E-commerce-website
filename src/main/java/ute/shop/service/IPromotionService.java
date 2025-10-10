package ute.shop.service;

import java.util.List;

import ute.shop.entity.Promotion;

public interface IPromotionService {
	Promotion findById(int id);
	List<Promotion> getValidPromotionsByShop(int shopId);
}
