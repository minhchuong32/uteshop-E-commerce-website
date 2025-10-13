package ute.shop.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ute.shop.config.JPAConfig;
import ute.shop.dao.IProductVariantDao;
import ute.shop.entity.ProductVariant;

public class ProductVariantDaoImpl implements IProductVariantDao {

    @Override
    public ProductVariant findById(Integer variantId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(ProductVariant.class, variantId);
        } finally {
            em.close();
        }
    }

    @Override
    public ProductVariant findByOptions(Integer productId, Map<String, Object> selectedOptions) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT v FROM ProductVariant v WHERE v.product.productId = :pid");

            // Xây dựng query động dựa trên số lượng options
            for (String key : selectedOptions.keySet()) {
                jpql.append(" AND v.optionName = :optName AND v.optionValue = :optValue");
            }

            TypedQuery<ProductVariant> query = em.createQuery(jpql.toString(), ProductVariant.class);
            query.setParameter("pid", productId);

            // Set parameters cho từng option
            for (Entry<String, Object> e : selectedOptions.entrySet()) {
                query.setParameter("optName", e.getKey());
                query.setParameter("optValue", e.getValue());
            }

            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    // Thêm mới variant
    @Override
    public void save(ProductVariant variant) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(variant);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Cập nhật variant
    @Override
    public void update(ProductVariant variant) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(variant);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Xóa variant theo id
    @Override
    public void delete(Integer variantId) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ProductVariant v = em.find(ProductVariant.class, variantId);
            if (v != null) em.remove(v);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Xóa toàn bộ variant của 1 sản phẩm
    @Override
    public void deleteByProductId(Integer productId) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM ProductVariant v WHERE v.product.productId = :pid")
              .setParameter("pid", productId)
              .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Lấy danh sách variant theo productId
    @Override
    public List<ProductVariant> findByProductId(Integer productId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<ProductVariant> query = em.createQuery(
                "SELECT v FROM ProductVariant v WHERE v.product.productId = :pid", ProductVariant.class);
            query.setParameter("pid", productId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}