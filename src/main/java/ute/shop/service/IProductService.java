package ute.shop.service;

import ute.shop.entity.Product;
import java.util.List;

public interface IProductService {
    List<Product> findAll();
    Product findById(Integer id);
    Product findByIdWithVariants(Integer id);
    List<Product> findTopProducts(int limit);
    long countAll();
    List<Product> findByPage(int page, int pageSize);
    Product findById(int productId);
    List<Product> findByShopId(int shopId);
    void save(Product product);
    void update(Product product);
    void delete(int productId);
    List<Product> getProductsByCategory(Integer categoryId);
    List<Product> findTopByCategoryId(Integer categoryId, int limit);
    List<Product> searchByKeyword(String keyword, int page, int size);
    long countByKeyword(String keyword);
	long countFilterProducts(Integer categoryId, Double minPrice, Double maxPrice);
	List<Product> filterProducts(Integer categoryId, Double minPrice, Double maxPrice, String sortBy, int page,
			int size);

	
	List<Product> findByCategoryAndShop(int categoryId, int shopId);

	Product findByIdWithVariants(int productId);
	
	//Vendor dashboard
	long getTotalProducts(int shopId);
	List<Object[]> getTopSellingProducts(int shopId, int limit);
    List<Object[]> getProductCountByCategory(int shopId);

}
