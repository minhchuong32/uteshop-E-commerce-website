package ute.shop.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import ute.shop.config.JPAConfig;
import ute.shop.dao.IRevenueDao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RevenueDaoImpl implements IRevenueDao {

	@Override
	public BigDecimal getTotalRevenue(BigDecimal platformFeeRate) {
		EntityManager em = JPAConfig.getEntityManager();
		BigDecimal total = BigDecimal.ZERO;

		try {
			String hql = "SELECT SUM(o.totalAmount) FROM Order o JOIN o.deliveries d WHERE d.status = :status";
			Query query = em.createQuery(hql);
			query.setParameter("status", "Đã giao");
			Object result = query.getSingleResult();
			if (result != null)
				total = (BigDecimal) result;
			return total.subtract(total.multiply(platformFeeRate));
		} finally {
			em.close();
		}
	}

	@Override
	public BigDecimal getTotalPlatformFee(BigDecimal platformFeeRate) {
		EntityManager em = JPAConfig.getEntityManager();
		BigDecimal total = BigDecimal.ZERO;

		try {
			String hql = "SELECT SUM(o.totalAmount) FROM Order o JOIN o.deliveries d WHERE d.status = :status";
			Query query = em.createQuery(hql);
			query.setParameter("status", "Đã giao");
			Object result = query.getSingleResult();
			if (result != null)
				total = (BigDecimal) result;
			return total.multiply(platformFeeRate);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Object[]> getRevenueByMonth() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String hql = """
					    SELECT MONTH(o.createdAt), SUM(o.totalAmount)
					    FROM Order o
					    WHERE o.status = :status
					      AND YEAR(o.createdAt) = YEAR(CURRENT_TIMESTAMP)
					    GROUP BY MONTH(o.createdAt)
					    ORDER BY MONTH(o.createdAt)
					""";

<<<<<<< HEAD
			Query query = em.createQuery(hql);
			query.setParameter("status", "Đã giao");
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Object[]> getRevenueByFilter(Date startDate, Date endDate, Integer shopId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			//  Xây dựng câu HQL động
			StringBuilder hql = new StringBuilder("""
					    SELECT MONTH(o.createdAt), SUM(o.totalAmount)
					    FROM Order o
					    WHERE o.status = :status
					      AND YEAR(o.createdAt) = YEAR(CURRENT_TIMESTAMP)
					""");

			// Thêm điều kiện lọc động
			if (startDate != null) {
				hql.append(" AND o.createdAt >= :startDate");
			}
			if (endDate != null) {
				hql.append(" AND o.createdAt <= :endDate");
			}
			if (shopId != null) {
				hql.append(" AND o.shop.shopId = :shopId");
			}

			hql.append(" GROUP BY MONTH(o.createdAt) ORDER BY MONTH(o.createdAt)");

			//  Tạo truy vấn
			Query query = em.createQuery(hql.toString());
			query.setParameter("status", "Đã giao");

			if (startDate != null) {
				query.setParameter("startDate", startDate);
			}
			if (endDate != null) {
				query.setParameter("endDate", endDate);
			}
			if (shopId != null) {
				query.setParameter("shopId", shopId);
			}

			//  Kết quả: List<Object[]> { [tháng, tổng doanh thu] }
			return query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			throw e; // hoặc log nếu muốn
		} finally {
			em.close();
		}
	}
=======
            Query query = em.createQuery(hql);
            query.setParameter("status", "Đã giao");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    //vendor dashboard
    @Override
    public BigDecimal getTotalRevenueByShop(int shopId) {
    	EntityManager em = JPAConfig.getEntityManager();
        try {
        	String hql = """
                    SELECT COALESCE(SUM(od.price * od.quantity), 0)
                    FROM OrderDetail od
                    JOIN od.order o
                    WHERE od.productVariant.product.shop.shopId = :sid
                      AND o.status = :status
                """;
                Query query = em.createQuery(hql);
                query.setParameter("sid", shopId);
                query.setParameter("status", "Đã giao"); // kiểm tra DB

                BigDecimal result = (BigDecimal) query.getSingleResult();

                return result;
        } finally {
            em.close();
        }
    }
    @Override
    public List<Object[]> getRevenueByMonthByShop(int shopId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String hql = """
                SELECT EXTRACT(MONTH FROM o.createdAt), SUM(od.price * od.quantity)
                FROM OrderDetail od
                JOIN od.order o
                WHERE od.productVariant.product.shop.shopId = :sid
                  AND o.status = :status
                GROUP BY EXTRACT(MONTH FROM o.createdAt)
                ORDER BY EXTRACT(MONTH FROM o.createdAt)
            """;
            Query query = em.createQuery(hql);
            query.setParameter("sid", shopId);
            query.setParameter("status", "Đã giao");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
>>>>>>> 8ea3bf959a76adc8b733df8eb3621fbf2ec65abe

}
