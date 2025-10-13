package ute.shop.dao;

import ute.shop.entity.Category;
import java.util.List;

public interface ICategoryDao {
    List<Category> findAll();
    Category findById(Integer id);
	void save(Category category);
	void update(Category category);
	void delete(int id);
}
