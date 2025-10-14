package ute.shop.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ute.shop.config.JPAConfig;
import ute.shop.dao.IReviewDao;
import ute.shop.entity.Product;
import ute.shop.entity.Review;
import ute.shop.entity.User;

import java.util.List;

public class ReviewDaoImpl implements IReviewDao {

	@Override
	public List<Review> findByProductId(Integer productId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.createQuery(
				    "SELECT r FROM Review r JOIN FETCH r.user WHERE r.product.productId = :pid", Review.class)
				    .setParameter("pid", productId)
				    .getResultList();

		} finally {
			em.close();
		}
	}

	@Override
	public void insert(Review review) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(review);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
	}

	@Override
	public boolean hasReviewed(User user, Product product) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT COUNT(r) FROM Review r WHERE r.user = :user AND r.product = :product";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("user", user)
                    .setParameter("product", product)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
	}

	@Override
	public void update(Review review) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(review);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
	}

	@Override
	public void delete(Integer id) {
		EntityManager em = JPAConfig.getEntityManager();
	    try {
	        em.getTransaction().begin();
	        Review r = em.find(Review.class, id);
	        if (r != null) {
	            Product p = r.getProduct();
	            // Gỡ review khỏi danh sách trong product
	            p.getReviews().remove(r);
	            // Sau khi orphanRemoval = true, JPA tự xóa review
	            em.merge(p);
	        }
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	}

	@Override
	public Review findById(Integer id) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(Review.class, id);
        } finally {
            em.close();
        }
	}

	@Override
	public Review findByUserAndProduct(User user, Product product) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Review> q = em.createQuery(
                "SELECT r FROM Review r WHERE r.user = :user AND r.product = :product", Review.class);
            q.setParameter("user", user);
            q.setParameter("product", product);
            List<Review> list = q.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
	}

	@Override
	public List<Review> findByProduct(Product product) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery(
                "SELECT r FROM Review r WHERE r.product = :product", Review.class)
                .setParameter("product", product)
                .getResultList();
        } finally {
            em.close();
        }
	}
}
