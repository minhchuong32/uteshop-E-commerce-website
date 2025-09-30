package ute.shop.dao;

import java.util.List;

import ute.shop.entity.Product;

public interface IProductDao {
	List<Product> findAll();
    Product findById(Integer id);
    List<Product> findTopProducts(int limit);
    long countAll();
    List<Product> findByPage(int page, int pageSize);
}
