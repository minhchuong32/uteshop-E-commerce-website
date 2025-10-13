package ute.shop.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ute.shop.config.JPAConfig;
import ute.shop.dao.IComplaintDao;
import ute.shop.entity.Complaint;
import java.util.List;

public class ComplaintDaoImpl implements IComplaintDao {

	@Override
	public List<Complaint> findAll() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			TypedQuery<Complaint> query = em.createQuery("SELECT c FROM Complaint c " + "LEFT JOIN FETCH c.user "
					+ "LEFT JOIN FETCH c.order " + "ORDER BY c.createdAt DESC", Complaint.class);
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public Complaint findById(int id) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        String jpql = "SELECT c FROM Complaint c " +
	                      "LEFT JOIN FETCH c.user " +
	                      "LEFT JOIN FETCH c.order " +
	                      "WHERE c.complaintId = :id";
	        return em.createQuery(jpql, Complaint.class)
	                 .setParameter("id", id)
	                 .getSingleResult();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        em.close();
	    }
	}


	@Override
	public void insert(Complaint c) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(c);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void update(Complaint c) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(c);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void delete(int id) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Complaint c = em.find(Complaint.class, id);
			if (c != null)
				em.remove(c);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
	
	public List<Complaint> findByUserId(int userId) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        String jpql = "SELECT c FROM Complaint c WHERE c.user.userId = :uid ORDER BY c.createdAt DESC";
	        return em.createQuery(jpql, Complaint.class)
	                 .setParameter("uid", userId)
	                 .getResultList();
	    } finally {
	        em.close();
	    }
	}
	
	@Override
	public long countAll() {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        String jpql = "SELECT COUNT(c) FROM Complaint c";
	        return em.createQuery(jpql, Long.class).getSingleResult();
	    } finally {
	        em.close();
	    }
	}


}
