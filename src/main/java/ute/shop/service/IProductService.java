package ute.shop.service;

import ute.shop.entity.Product;
import java.util.List;

public interface IProductService {
    List<Product> findAll();
    Product findById(Integer id);
    List<Product> findTopProducts(int limit);
    long countAll();
    List<Product> findByPage(int page, int pageSize);
    Product findById(int productId);
    List<Product> findByShopId(int shopId);
    void save(Product product);
    void update(Product product);
    void delete(int productId);
    List<Product> getProductsByCategory(Integer categoryId);
}
