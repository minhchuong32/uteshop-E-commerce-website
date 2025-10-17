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
			return em.createQuery("SELECT o FROM Order o ORDER BY o.createdAt DESC", Order.class).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Order> findAllForAdmin() {
		EntityManager em = JPAConfig.getEntityManager();
		List<Order> list = em.createQuery(
				"SELECT DISTINCT o FROM Order o " + "LEFT JOIN FETCH o.deliveries d " + "LEFT JOIN FETCH d.shipper s "
						+ "LEFT JOIN FETCH d.carrier c " + "LEFT JOIN FETCH o.user u " + "LEFT JOIN FETCH o.shop sh",
				Order.class).getResultList();
		em.close();
		return list;
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
	public Order insert(Order order) {
		EntityManager em = JPAConfig.getEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        em.persist(order); // chỉ cần persist Order
	        tx.commit();
	        return order; // đã có ID và tất cả các con được cascade lưu tự động
	    } catch (Exception e) {
	        tx.rollback();
	        e.printStackTrace();
	        throw e;
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
			if (tx.isActive())
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
			Order o = em.find(Order.class, id);
			if (o != null)
				em.remove(o);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Order> findByUser(User user) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT o FROM Order o " + "LEFT JOIN FETCH o.orderDetails d "
					+ "LEFT JOIN FETCH d.productVariant pv " + "LEFT JOIN FETCH pv.product p " + "WHERE o.user = :user "
					+ "ORDER BY o.createdAt DESC";
			TypedQuery<Order> query = em.createQuery(jpql, Order.class);
			query.setParameter("user", user);
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public boolean hasPurchased(int userId, int productId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			Long count = em
					.createQuery(
							"SELECT COUNT(od) FROM OrderDetail od " + "JOIN od.order o " + "WHERE o.user.userId = :uid "
									+ "AND od.productVariant.product.productId = :pid " + "AND o.status = :status",
							Long.class)
					.setParameter("uid", userId).setParameter("pid", productId).setParameter("status", "Đã giao")
					.getSingleResult();

			return count != null && count > 0;
		} finally {
			em.close();
		}
	}

	// Vendor dashboard
	@Override
	public long countOrdersByShop(int shopId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT COUNT(DISTINCT o) " + "FROM Order o JOIN o.orderDetails od "
					+ "WHERE od.productVariant.product.shop.shopId = :sid";
			return em.createQuery(jpql, Long.class).setParameter("sid", shopId).getSingleResult();
		} finally {
			em.close();
		}
	}

	@Override
	public long countDistinctCustomersByShop(int shopId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT COUNT(DISTINCT o.user.userId) " + "FROM Order o JOIN o.orderDetails od "
					+ "WHERE od.productVariant.product.shop.shopId = :sid";
			return em.createQuery(jpql, Long.class).setParameter("sid", shopId).getSingleResult();
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
			return em.createNativeQuery(sql).setParameter("sid", shopId).setParameter("status", "Đã giao")
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
			return em.createQuery(jpql, Object[].class).setParameter("sid", shopId).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Order> getOrdersByShopAndStatuses(int shopId, List<String> statuses) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em
					.createQuery("SELECT DISTINCT o FROM Order o JOIN o.orderDetails od "
							+ "WHERE od.productVariant.product.shop.shopId = :sid AND o.status IN :statuses "
							+ "ORDER BY o.createdAt DESC", Order.class)
					.setParameter("sid", shopId).setParameter("statuses", statuses).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public User getOrderShipper(Order order) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			List<User> list = em
					.createQuery("SELECT d.shipper FROM Delivery d WHERE d.order = :order ORDER BY d.createdAt ASC",
							User.class)
					.setParameter("order", order).setMaxResults(1).getResultList();
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
			return em.createQuery(jpql, Order.class).setMaxResults(limit).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Order> findByUserId(Integer userId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.createQuery("SELECT o FROM Order o WHERE o.user.userId = :uid ORDER BY o.createdAt DESC",
					Order.class).setParameter("uid", userId).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public Order getById(Integer id) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT o FROM Order o " + "LEFT JOIN FETCH o.orderDetails d "
					+ "LEFT JOIN FETCH d.productVariant pv " + "LEFT JOIN FETCH pv.product p "
					+ "WHERE o.orderId = :id";
			TypedQuery<Order> query = em.createQuery(jpql, Order.class);
			query.setParameter("id", id);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}

	@Override
	public List<Order> getOrdersByUserAndStatus(int userId, String status) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			// Truy vấn đơn hàng + orderDetails
			List<Order> orders = em.createQuery(
					"SELECT DISTINCT o FROM Order o " + "LEFT JOIN FETCH o.orderDetails d "
							+ "WHERE o.user.userId = :userId AND o.status = :status " + "ORDER BY o.createdAt DESC",
					Order.class).setParameter("userId", userId).setParameter("status", status).getResultList();

			// Sau đó fetch deliveries riêng (Hibernate sẽ tự cache)
			for (Order o : orders) {
				o.getDeliveries().size(); // Lazy load
			}

			return orders;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean updateStatus(int orderId, String status) {
		EntityManager em = JPAConfig.getEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        Order order = em.find(Order.class, orderId);
	        if (order != null) {
	            order.setStatus(status);
	            em.merge(order);
	            tx.commit();
	            return true;
	        }
	        return false;
	    } catch (Exception e) {
	        if (tx.isActive()) tx.rollback();
	        e.printStackTrace();
	        return false;
	    } finally {
	        em.close();
	    }
	}

	@Override
	public Order save(Order order) {
		EntityManager em = JPAConfig.getEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        Order managed = em.merge(order);
	        em.flush();
	        tx.commit();
	        return em.find(Order.class, managed.getOrderId());
	    } catch (Exception e) {
	        if (tx.isActive()) tx.rollback();
	        e.printStackTrace();
	        return null;
	    } finally {
	        em.close();
	    }
	}

}
