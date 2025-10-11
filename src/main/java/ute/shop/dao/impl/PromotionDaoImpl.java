package ute.shop.dao.impl;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ute.shop.config.JPAConfig;
import ute.shop.dao.IPromotionDao;
import ute.shop.entity.Promotion;

public class PromotionDaoImpl implements IPromotionDao {

	@Override
	public Promotion findById(int id) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(Promotion.class, id);
        } finally {
            em.close();
        }
	}

	@Override
	public List<Promotion> findAll() {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Promotion> query = em.createQuery("SELECT p FROM Promotion p", Promotion.class);
            return query.getResultList();
        } finally {
            em.close();
        }
	}

	@Override
	public List<Promotion> findValidByShop(int shopId) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
        	String jpql = """
        		    SELECT p FROM Promotion p
        		    WHERE p.shop.shopId = :shopId
        		      AND :today BETWEEN p.startDate AND p.endDate
        		""";
            TypedQuery<Promotion> query = em.createQuery(jpql, Promotion.class);
            query.setParameter("shopId", shopId);
            query.setParameter("today", LocalDate.now());
            return query.getResultList();
        } finally {
            em.close();
        }
	}

}
