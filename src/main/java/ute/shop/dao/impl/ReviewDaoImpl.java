package ute.shop.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ute.shop.config.JPAConfig;
import ute.shop.dao.ReviewDao;
import ute.shop.entity.Review;

import java.util.List;

public class ReviewDaoImpl implements ReviewDao {

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
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.persist(review);
			trans.commit();
		} catch (Exception e) {
			if (trans.isActive())
				trans.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
}
