package ute.shop.dao;

import java.util.List;

import ute.shop.models.Shop;

public interface IShopDao {
	List<Shop> getAll();

	Shop getById(int id);

	void insert(Shop shop);

	void update(Shop shop);

	void delete(int id);
}
