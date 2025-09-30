package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.dao.IProductDao;
import ute.shop.entity.Product;

import java.util.List;

public class ProductDaoImpl implements IProductDao {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UteShop");

    @Override
    public List<Product> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Product findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> findTopProducts(int limit) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Product p ORDER BY p.productId DESC", Product.class)
                     .setMaxResults(limit)
                     .getResultList();
        } finally {
            em.close();
        }
    }
    
    //  Đếm tổng số sản phẩm
    @Override
    public long countAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(p) FROM Product p", Long.class)
                     .getSingleResult();
        } finally {
            em.close();
        }
    }

    //  Lấy sản phẩm phân trang
    @Override
    public List<Product> findByPage(int page, int pageSize) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Product p ORDER BY p.productId DESC", Product.class)
                     .setFirstResult((page - 1) * pageSize)
                     .setMaxResults(pageSize)
                     .getResultList();
        } finally {
            em.close();
        }
    }
}
