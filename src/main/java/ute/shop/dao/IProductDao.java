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

	List<Product> findByCategoryId(Integer categoryId);

	List<Product> findTopByCategoryId(Integer categoryId, int limit);

	List<Product> searchByKeyword(String keyword, int page, int size);

	long countByKeyword(String keyword);

	long countFilterProducts(Integer categoryId, Double minPrice, Double maxPrice);

	List<Product> filterProducts(Integer categoryId, Double minPrice, Double maxPrice, String sortBy, int page,
			int size);

	Product findByIdWithVariants(int productId);
	

	long countByShopId(int shopId);
	List<Object[]> getTopSellingProducts(int shopId, int limit);
	List<Object[]> getProductCountByCategory(int shopId);
}
