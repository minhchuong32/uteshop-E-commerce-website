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
	
	 @Override
	    public void insert(Promotion p) {
	        EntityManager em = JPAConfig.getEntityManager();
	        try {
	            em.getTransaction().begin();
	            em.persist(p);
	            em.getTransaction().commit();
	        } catch (Exception e) {
	            if (em.getTransaction().isActive()) em.getTransaction().rollback();
	            e.printStackTrace();
	        } finally {
	            em.close();
	        }
	    }

	    @Override
	    public void update(Promotion p) {
	        EntityManager em = JPAConfig.getEntityManager();
	        try {
	            em.getTransaction().begin();
	            em.merge(p);
	            em.getTransaction().commit();
	        } catch (Exception e) {
	            if (em.getTransaction().isActive()) em.getTransaction().rollback();
	            e.printStackTrace();
	        } finally {
	            em.close();
	        }
	    }

	    @Override
	    public void delete(int id) {
	        EntityManager em = JPAConfig.getEntityManager();
	        try {
	            em.getTransaction().begin();
	            Promotion p = em.find(Promotion.class, id);
	            if (p != null) em.remove(p);
	            em.getTransaction().commit();
	        } catch (Exception e) {
	            if (em.getTransaction().isActive()) em.getTransaction().rollback();
	            e.printStackTrace();
	        } finally {
	            em.close();
	        }
	    }
	    
		@Override
		public long countAll() {
		    EntityManager em = JPAConfig.getEntityManager();
		    try {
		        String jpql = "SELECT COUNT(c) FROM Promotion c";
		        return em.createQuery(jpql, Long.class).getSingleResult();
		    } finally {
		        em.close();
		    }
		}

}
