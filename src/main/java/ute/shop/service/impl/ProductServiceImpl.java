package ute.shop.service.impl;

import ute.shop.dao.IProductDao;
import ute.shop.dao.impl.ProductDaoImpl;
import ute.shop.entity.Product;
import ute.shop.service.IProductService;

import java.util.List;

public class ProductServiceImpl implements IProductService {

    private final IProductDao productDao = new ProductDaoImpl();

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return productDao.findById(id);
    }

    @Override
    public List<Product> findTopProducts(int limit) {
        return productDao.findTopProducts(limit);
    }
}
