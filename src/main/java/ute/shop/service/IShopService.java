package ute.shop.service;

import java.util.List;

import ute.shop.models.Shop;

public interface IShopService {
	List<Shop> getAll();

	Shop getById(int id);

	void insert(Shop shop);

	void update(Shop shop);

	void delete(int id);
}
