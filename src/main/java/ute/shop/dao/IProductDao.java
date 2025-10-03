package ute.shop.dao;

import java.util.List;

import ute.shop.entity.Product;

public interface IProductDao {
	List<Product> findAll();

	Product findById(Integer id);

	List<Product> findTopProducts(int limit);

	long countAll();

	List<Product> findByPage(int page, int pageSize);

	List<Product> findByShopId(int shopId);

	Product findById(int productId);

	void save(Product product);

	void update(Product product);

	void delete(int productId);

}
