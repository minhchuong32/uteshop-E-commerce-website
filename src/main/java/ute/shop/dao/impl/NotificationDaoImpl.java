package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.config.JPAConfig;
import ute.shop.dao.INotificationDao;
import ute.shop.entity.Notification;
import java.util.List;

public class NotificationDaoImpl implements INotificationDao {
	
	public List<Notification> getAllByUserId(int userId) {
		EntityManager em = JPAConfig.getEntityManager();
	    try {
	        String jpql = "SELECT DISTINCT n FROM Notification n "
	                     + "LEFT JOIN FETCH n.relatedComplaint c "
	                     + "LEFT JOIN FETCH c.messages m "
	                     + "LEFT JOIN FETCH m.sender "
	                     + "WHERE n.user.userId = :uid "
	                     + "ORDER BY n.createdAt DESC";

	        return em.createQuery(jpql, Notification.class)
	                 .setParameter("uid", userId)
	                 .getResultList();
	    } finally {
	        em.close();
	    }
	}


	public List<Notification> findByUserId(int userId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT n FROM Notification n " + "LEFT JOIN FETCH n.relatedComplaint "
					+ "WHERE n.user.userId = :uid " + "ORDER BY n.createdAt DESC";
			return em.createQuery(jpql, Notification.class).setParameter("uid", userId).getResultList();
		} finally {
			em.close();
		}
	}

	public Notification findById(int id) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.find(Notification.class, id);
		} finally {
			em.close();
		}
	}

	public void update(Notification noti) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(noti);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public List<Notification> findUnreadByUserId(int userId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT n FROM Notification n WHERE n.user.userId = :uid AND n.read = false ORDER BY n.createdAt DESC";
			return em.createQuery(jpql, Notification.class).setParameter("uid", userId).setMaxResults(10)
					.getResultList();
		} finally {
			em.close();
		}
	}

	public void insert(Notification n) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(n);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
	}
}
