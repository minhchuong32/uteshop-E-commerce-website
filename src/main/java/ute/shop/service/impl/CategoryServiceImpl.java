package ute.shop.service.impl;

import ute.shop.dao.ICategoryDao;
import ute.shop.dao.impl.CategoryDaoImpl;
import ute.shop.entity.Category;
import ute.shop.service.ICategoryService;

import java.util.List;

public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(Integer id) {
        return categoryDao.findById(id);
    }
}
