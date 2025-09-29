package ute.shop.dao;

import ute.shop.entity.Product;

public interface IProductDao {
    Product findById(int id);
}
