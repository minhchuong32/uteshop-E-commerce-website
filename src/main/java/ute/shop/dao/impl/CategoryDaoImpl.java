package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.dao.ICategoryDao;
import ute.shop.entity.Category;

import java.util.List;

public class CategoryDaoImpl implements ICategoryDao {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UteShop");

    @Override
    public List<Category> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c", Category.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Category findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }
}
