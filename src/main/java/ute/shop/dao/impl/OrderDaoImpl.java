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
            em.persist(order); // lưu order + orderDetails (cascade ALL)
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
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
	
	//Vendor dashboard
	@Override
	public long countOrdersByShop(int shopId) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        String jpql = "SELECT COUNT(DISTINCT o) " +
	                      "FROM Order o JOIN o.orderDetails od " +
	                      "WHERE od.productVariant.product.shop.shopId = :sid";
	        return em.createQuery(jpql, Long.class)
	                 .setParameter("sid", shopId)
	                 .getSingleResult();
	    } finally {
	        em.close();
	    }
	}
	
	@Override
	public long countDistinctCustomersByShop(int shopId) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        String jpql = "SELECT COUNT(DISTINCT o.user.userId) " +
	                      "FROM Order o JOIN o.orderDetails od " +
	                      "WHERE od.productVariant.product.shop.shopId = :sid";
	        return em.createQuery(jpql, Long.class)
	                 .setParameter("sid", shopId)
	                 .getSingleResult();
	    } finally {
	        em.close();
	    }
	}
	
	@Override
	public List<Object[]> getOrderTrendByShop(int shopId) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        String sql = """
	            SELECT CONVERT(date, o.created_at) AS orderDate,
	                   COUNT(o.order_id) AS total
	            FROM orders o
	            JOIN order_details od ON o.order_id = od.order_id
	            JOIN product_variants pv ON od.product_variant_id = pv.id
	            JOIN products p ON pv.product_id = p.product_id
	            WHERE p.shop_id = :sid AND o.status = :status
	            GROUP BY CONVERT(date, o.created_at)
	            ORDER BY CONVERT(date, o.created_at)
	        """;
	        return em.createNativeQuery(sql)
	                 .setParameter("sid", shopId)
	                 .setParameter("status", "Đã giao")
	                 .getResultList();
	    } finally {
	        em.close();
	    }
	}

	@Override
	public List<Object[]> getOrderStatusCountByShop(int shopId) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        String jpql = """
	            SELECT o.status, COUNT(o)
	            FROM Order o JOIN o.orderDetails od
	            WHERE od.productVariant.product.shop.shopId = :sid
	            GROUP BY o.status
	        """;
	        return em.createQuery(jpql, Object[].class)
	                 .setParameter("sid", shopId)
	                 .getResultList();
	    } finally {
	        em.close();
	    }
	}
	
	@Override
	public List<Order> getOrdersByShopAndStatuses(int shopId, List<String> statuses) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        return em.createQuery(
	            "SELECT DISTINCT o FROM Order o JOIN o.orderDetails od " +
	            "WHERE od.productVariant.product.shop.shopId = :sid AND o.status IN :statuses " +
	            "ORDER BY o.createdAt DESC", Order.class)
	            .setParameter("sid", shopId)
	            .setParameter("statuses", statuses)
	            .getResultList();
	    } finally {
	        em.close();
	    }
	}
	@Override
	public User getOrderShipper(Order order) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        List<User> list = em.createQuery(
	            "SELECT d.shipper FROM Delivery d WHERE d.order = :order ORDER BY d.createdAt ASC", User.class)
	            .setParameter("order", order)
	            .setMaxResults(1)
	            .getResultList();
	        return list.isEmpty() ? null : list.get(0);
	    } finally {
	        em.close();
	    }
	}
	
	@Override
	public long countAllOrders() {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        return em.createQuery("SELECT COUNT(o) FROM Order o", Long.class).getSingleResult();
	    } finally {
	        em.close();
	    }
	}
	
	@Override
	public List<Order> findRecentOrders(int limit) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        String jpql = """
	            SELECT DISTINCT o FROM Order o
	            LEFT JOIN FETCH o.orderDetails od
	            LEFT JOIN FETCH od.productVariant pv
	            LEFT JOIN FETCH pv.product p
	            ORDER BY o.createdAt DESC
	        """;
	        return em.createQuery(jpql, Order.class)
	                 .setMaxResults(limit)
	                 .getResultList();
	    } finally {
	        em.close();
	    }
	}

	@Override
	public Order findById(Integer id) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(Order.class, id);
        } finally {
            em.close();
        }
	}

	@Override
	public List<Order> findByUserId(Integer userId) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery("SELECT o FROM Order o WHERE o.user.userId = :uid ORDER BY o.createdAt DESC", Order.class)
                    .setParameter("uid", userId)
                    .getResultList();
        } finally {
            em.close();
        }
	}


	

}
