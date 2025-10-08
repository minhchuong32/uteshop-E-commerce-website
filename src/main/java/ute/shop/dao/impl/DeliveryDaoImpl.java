package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.dao.IDeliveryDao;
import ute.shop.entity.Delivery;

import java.util.List;

public class DeliveryDaoImpl implements IDeliveryDao {

	private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UteShop");

	@Override
	public Delivery findById(Integer id) {
		EntityManager em = emf.createEntityManager();
		try {
			return em.find(Delivery.class, id);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Delivery> findByShipper(Integer shipperId) {
		EntityManager em = emf.createEntityManager();
		try {
			return em.createQuery("SELECT d FROM Delivery d WHERE d.shipper.userId = :shipperId", Delivery.class)
					.setParameter("shipperId", shipperId).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Delivery> findAll() {
		EntityManager em = emf.createEntityManager();
		List<Delivery> list = em
				.createQuery("SELECT d FROM Delivery d " + "JOIN FETCH d.shipper " + "JOIN FETCH d.order",
						Delivery.class)
				.getResultList();
		em.close();
		return list;
	}

	@Override
	public Delivery save(Delivery delivery) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			if (delivery.getDeliveryId() == null) {
				em.persist(delivery);
			} else {
				delivery = em.merge(delivery);
			}
			tx.commit();
			return delivery;
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

//	@Override
//	public void updateStatus(Integer deliveryId, String status) {
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction tx = em.getTransaction();
//		try {
//			tx.begin();
//			Delivery d = em.find(Delivery.class, deliveryId);
//			if (d != null) {
//				d.setStatus(status);
//				em.merge(d);
//			}
//			tx.commit();
//		} catch (Exception e) {
//			if (tx.isActive())
//				tx.rollback();
//			throw e;
//		} finally {
//			em.close();
//		}
//	}
	@Override
	public void updateStatus(Integer deliveryId, String status) {
	    EntityManager em = emf.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        Delivery d = em.find(Delivery.class, deliveryId);
	        if (d != null) {
	            d.setStatus(status);

	            // ✅ Đồng bộ trạng thái với Order tương ứng
	            if (d.getOrder() != null) {
	                switch (status) {
	                    case "Đang giao":
	                        d.getOrder().setStatus("Đang giao");
	                        break;
	                    case "Đã giao":
	                        d.getOrder().setStatus("Đã giao");
	                        break;
	                    case "Trả lại":
	                        d.getOrder().setStatus("Trả lại");
	                        break;
	                    case "Hủy":
	                        d.getOrder().setStatus("Đã hủy");
	                        break;
	                    default:
	                        d.getOrder().setStatus(status);
	                }
	            }

	            em.merge(d);
	        }
	        tx.commit();
	    } catch (Exception e) {
	        if (tx.isActive()) tx.rollback();
	        throw e;
	    } finally {
	        em.close();
	    }
	}


	@Override
	public void delete(Integer id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Delivery d = em.find(Delivery.class, id);
			if (d != null) {
				em.remove(d);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			throw e;
		} finally {
			em.close();
		}
	}
	
	//Shipper dashboard
	@Override
	public long countByStatus(int shipperId, String status) {
	    EntityManager em = emf.createEntityManager();
	    try {
	        String jpql = "SELECT COUNT(d) FROM Delivery d WHERE d.shipper.userId = :sid";
	        if (status != null && !status.isEmpty()) {
	            jpql += " AND d.status = :status";
	        }
	        Query query = em.createQuery(jpql);
	        query.setParameter("sid", shipperId);
	        if (status != null && !status.isEmpty()) {
	            query.setParameter("status", status);
	        }
	        return (Long) query.getSingleResult();
	    } finally {
	        em.close();
	    }
	}

	@Override
	public List<Object[]> getSuccessRateByMonth(int shipperId) {
	    EntityManager em = emf.createEntityManager();
	    try {
	        String sql = """
	            SELECT MONTH(d.created_at) AS month,
	                   SUM(CASE WHEN d.status = N'Đã giao' THEN 1 ELSE 0 END) AS successCount,
	                   COUNT(*) AS totalCount
	            FROM deliveries d
	            WHERE d.shipper_id = :sid
	            GROUP BY MONTH(d.created_at)
	            ORDER BY month
	        """;
	        return em.createNativeQuery(sql)
	                 .setParameter("sid", shipperId)
	                 .getResultList();
	    } finally {
	        em.close();
	    }
	}

	@Override
	public List<Object[]> getRecentDeliveries(int shipperId, int limit) {
	    EntityManager em = emf.createEntityManager();
	    try {
	        String jpql = """
	            SELECT d FROM Delivery d 
	            JOIN FETCH d.order 
	            WHERE d.shipper.userId = :sid
	            ORDER BY d.createdAt DESC
	        """;
	        return em.createQuery(jpql)
	                 .setParameter("sid", shipperId)
	                 .setMaxResults(limit)
	                 .getResultList();
	    } finally {
	        em.close();
	    }
	}
	
	//Shipper QL đơn
	@Override
	public List<Delivery> findUnassignedDeliveries() {
	    EntityManager em = emf.createEntityManager();
	    try {
	        // Lấy các đơn chưa gán shipper hoặc đang trong trạng thái chờ nhận
	        return em.createQuery("""
	                SELECT d FROM Delivery d
	                JOIN FETCH d.order o
	                WHERE d.status IN ('Tìm shipper', 'Đã gán')
	                ORDER BY d.createdAt DESC
	                """, Delivery.class)
	                .getResultList();
	    } finally {
	        em.close();
	    }
	}
	
	@Override
	public void assignToShipper(Integer deliveryId, Integer shipperId) {
	    EntityManager em = emf.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        Delivery d = em.find(Delivery.class, deliveryId);
	        if (d != null) {
	            d.setStatus("Đang giao");
	            d.getOrder().setStatus("Đang giao");

	            d.setShipper(em.getReference(ute.shop.entity.User.class, shipperId));

	            em.merge(d);
	        }
	        tx.commit();
	    } catch (Exception e) {
	        if (tx.isActive()) tx.rollback();
	        throw e;
	    } finally {
	        em.close();
	    }
	}

}
