package ute.shop.service;

import ute.shop.entity.Product;
import ute.shop.entity.ProductVariant;

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
    

	List<Object[]> findBestSellingProducts(int limit);
	Product findById_fix(Integer id);
	
	void deleteExtraImage(Long imageId);
	ProductVariant findVariantById(Long variantId);
	
	List<Product> searchByKeywordAndShop(String keyword, int shopId, int page, int size);
	long countByKeywordAndShop(String keyword, int shopId);

	List<Product> filterProductsByShop(int shopId, Integer categoryId, Double minPrice, Double maxPrice, String sortBy, int page, int size);
	long countFilterProductsByShop(int shopId, Integer categoryId, Double minPrice, Double maxPrice);


}
