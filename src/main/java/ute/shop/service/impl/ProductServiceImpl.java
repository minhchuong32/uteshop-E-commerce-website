package ute.shop.service.impl;

import ute.shop.dao.IProductDao;
import ute.shop.dao.impl.ProductDaoImpl;
import ute.shop.entity.Product;
import ute.shop.service.IProductService;

public class ProductServiceImpl implements IProductService {

    private final IProductDao productDao = new ProductDaoImpl();

    @Override
    public Product findById(int id) {
        return productDao.findById(id);
    }
}
