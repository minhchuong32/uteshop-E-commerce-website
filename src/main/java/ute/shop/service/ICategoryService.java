package ute.shop.service;

import ute.shop.entity.Category;
import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
    Category findById(Integer id);
}
