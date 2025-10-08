package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.config.JPAConfig;
import ute.shop.dao.IOrderDao;
import ute.shop.entity.Order;
import ute.shop.entity.User;

import java.sql.Timestamp;
import java.util.List;

public class OrderDaoImpl implements IOrderDao {

    @Override
    public List<Order> getAll() {
    	 EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery("SELECT o FROM Order o ORDER BY o.createdAt DESC", Order.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Order getById(int id) {
    	 EntityManager em = JPAConfig.getEntityManager();
    	    try {
    	        return em.find(Order.class, id);
    	    } finally {
    	        em.close();
    	    }
    }

    @Override
    public boolean insert(Order order) {
    	 EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            order.setCreatedAt(new Timestamp(System.currentTimeMillis())); // tương đương GETDATE()
            em.persist(order);
            tx.commit();
            return true; // thành công
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false; // thất bại
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Order order) {
    	 EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(order);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
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
            Order o = em.find(Order.class, id);
            if (o != null) em.remove(o);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

	@Override
	public List<Order> findByUser(User user) {
		 EntityManager em = JPAConfig.getEntityManager();
	    try {
	        return em.createQuery("SELECT o FROM Order o WHERE o.user = :user ORDER BY o.createdAt DESC", Order.class)
	                 .setParameter("user", user)
	                 .getResultList();
	    } finally {
	        em.close();
	    }
	}

	@Override
	public boolean hasPurchased(int userId, int productId) {
		EntityManager em = JPAConfig.getEntityManager();
	    try {
	        Long count = em.createQuery(
	            "SELECT COUNT(od) FROM OrderDetail od " +
	            "JOIN od.order o " +
	            "WHERE o.user.userId = :uid " +
	            "AND od.productVariant.product.productId = :pid " +
	            "AND o.status = :status", Long.class)
	            .setParameter("uid", userId)
	            .setParameter("pid", productId)
	            .setParameter("status", "Hoàn tất") // hoặc OrderStatus.COMPLETED
	            .getSingleResult();

	        return count != null && count > 0;
	    } finally {
	        em.close();
	    }
	}

}
