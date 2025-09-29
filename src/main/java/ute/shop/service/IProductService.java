package ute.shop.service;

import ute.shop.entity.Product;
import java.util.List;

public interface IProductService {
    List<Product> findAll();
    Product findById(Integer id);
    List<Product> findTopProducts(int limit);
}
