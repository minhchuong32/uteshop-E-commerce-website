package ute.shop.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ute.shop.dao.IProductDao;
import ute.shop.entity.Product;

public class ProductDaoImpl implements IProductDao {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UteShop");

    @Override
    public Product findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }
}
