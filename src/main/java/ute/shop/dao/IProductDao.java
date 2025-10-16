package ute.shop.dao;

import java.util.List;

import ute.shop.entity.ProductVariant;
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

	List<Product> findByCategory(Integer categoryId);

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

	List<Object[]> findBestSellingProducts(int limit);

	Product findById_fix(Integer id);

	void deleteExtraImage(Long imageId);

	ProductVariant findVariantById(Long variantId);

	List<Product> searchByKeywordAndShop(String keyword, int shopId, int page, int size);
	long countByKeywordAndShop(String keyword, int shopId);

	List<Product> filterProductsByShop(int shopId, Integer categoryId, Double minPrice, Double maxPrice, String sortBy, int page, int size);
	long countFilterProductsByShop(int shopId, Integer categoryId, Double minPrice, Double maxPrice);

}
