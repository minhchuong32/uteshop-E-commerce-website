package ute.shop.dao.impl;


import ute.shop.dao.ProductImageDao;
import ute.shop.entity.ProductImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ute.shop.config.JPAConfig;

import java.util.List;

public class ProductImageDaoImpl implements ProductImageDao {
	
	@Override
	public ProductImage update(ProductImage image) {
	    EntityManager em = JPAConfig.getEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        ProductImage updated = em.merge(image);
	        tx.commit();
	        return updated;
	    } catch (Exception e) {
	        if (tx.isActive()) tx.rollback();
	        e.printStackTrace();
	        throw e;
	    } finally {
	        em.close();
	    }
	}

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
    public void delete(Long productId) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //  Xóa tất cả ảnh có product_id = :pid
            int deletedCount = em.createQuery(
                    "DELETE FROM ProductImage p WHERE p.product.id = :pid")
                    .setParameter("pid", productId)
                    .executeUpdate();

            tx.commit();
            System.out.println("Đã xóa " + deletedCount + " ảnh của productId = " + productId);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

}
