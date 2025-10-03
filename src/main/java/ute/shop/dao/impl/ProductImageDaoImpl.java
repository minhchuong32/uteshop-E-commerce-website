package ute.shop.dao.impl;


import ute.shop.dao.ProductImageDao;
import ute.shop.entity.ProductImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ute.shop.config.JPAConfig;

import java.util.List;

public class ProductImageDaoImpl implements ProductImageDao {

    @Override
    public List<ProductImage> findByProductId(Long productId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery("SELECT pi FROM ProductImage pi WHERE pi.product.id = :pid", ProductImage.class)
                     .setParameter("pid", productId)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public ProductImage findById(Long id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(ProductImage.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public ProductImage save(ProductImage image) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (image.getId() == null) {
                em.persist(image); // insert
            } else {
                image = em.merge(image); // update
            }
            tx.commit();
            return image;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ProductImage img = em.find(ProductImage.class, id);
            if (img != null) {
                em.remove(img);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
