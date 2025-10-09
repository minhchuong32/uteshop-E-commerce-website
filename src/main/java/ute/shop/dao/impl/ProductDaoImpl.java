package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.dao.IProductDao;
import ute.shop.entity.Product;
import ute.shop.config.JPAConfig;
import java.util.List;

public class ProductDaoImpl implements IProductDao {

	@Override
	public List<Product> findAll() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public Product findById(Integer id) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.find(Product.class, id);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Product> findTopProducts(int limit) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.createQuery("SELECT p FROM Product p ORDER BY p.productId DESC", Product.class)
					.setMaxResults(limit).getResultList();
		} finally {
			em.close();
		}
	}

	// Đếm tổng số sản phẩm
	@Override
	public long countAll() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.createQuery("SELECT COUNT(p) FROM Product p", Long.class).getSingleResult();
		} finally {
			em.close();
		}
	}

	// Lấy sản phẩm phân trang
	@Override
	public List<Product> findByPage(int page, int pageSize) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.createQuery("SELECT p FROM Product p ORDER BY p.productId DESC", Product.class)
					.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Product> findByShopId(int shopId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.createQuery("SELECT p FROM Product p WHERE p.shop.shopId = :sid", Product.class)
					.setParameter("sid", shopId).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public Product findById(int productId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.find(Product.class, productId);
		} finally {
			em.close();
		}
	}

	@Override
	public void save(Product product) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(product);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void update(Product product) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(product);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void delete(int productId) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			Product p = em.find(Product.class, productId);
			if (p != null) {
				if (!em.contains(p)) {
					p = em.merge(p);
				}
				em.remove(p);
			}
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
	public List<Product> findByCategoryId(Integer categoryId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT p FROM Product p WHERE p.category.categoryId = :categoryId";
			return em.createQuery(jpql, Product.class).setParameter("categoryId", categoryId).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Product> findTopByCategoryId(Integer categoryId, int limit) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT p FROM Product p WHERE p.category.categoryId = :cid ORDER BY p.productId DESC";
			return em.createQuery(jpql, Product.class).setParameter("cid", categoryId).setMaxResults(limit)
					.getResultList();
		} finally {
			em.close();
		}

	}

	@Override
	public List<Product> searchByKeyword(String keyword, int page, int size) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT p FROM Product p " + "WHERE LOWER(p.name) LIKE :kw OR p.description LIKE :kw";
			return em.createQuery(jpql, Product.class).setParameter("kw", "%" + keyword.toLowerCase() + "%")
					.setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public long countByKeyword(String keyword) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT COUNT(p) FROM Product p WHERE LOWER(p.name) LIKE :kw";
			return em.createQuery(jpql, Long.class).setParameter("kw", "%" + keyword.toLowerCase() + "%")
					.getSingleResult();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Product> filterProducts(Integer categoryId, Double minPrice, Double maxPrice, String sortBy, int page,
			int size) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			StringBuilder jpql = new StringBuilder("SELECT p FROM ProductVariant v JOIN v.product p WHERE 1=1");

			if (categoryId != null)
				jpql.append(" AND p.category.categoryId = :cid");
			if (minPrice != null)
				jpql.append(" AND v.price >= :minPrice");
			if (maxPrice != null)
				jpql.append(" AND v.price <= :maxPrice");

			// Sắp xếp theo giá min của product
			jpql.append(" GROUP BY p.productId, p.name, p.description, p.imageUrl, p.category, p.shop");

			if ("priceAsc".equals(sortBy)) {
				jpql.append(" ORDER BY MIN(v.price) ASC");
			} else if ("priceDesc".equals(sortBy)) {
				jpql.append(" ORDER BY MIN(v.price) DESC");
			} else {
				jpql.append(" ORDER BY p.productId DESC");
			}

			TypedQuery<Product> query = em.createQuery(jpql.toString(), Product.class);

			if (categoryId != null)
				query.setParameter("cid", categoryId);
			if (minPrice != null)
				query.setParameter("minPrice", minPrice);
			if (maxPrice != null)
				query.setParameter("maxPrice", maxPrice);

			return query.setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public long countFilterProducts(Integer categoryId, Double minPrice, Double maxPrice) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			StringBuilder jpql = new StringBuilder(
					"SELECT COUNT(DISTINCT p) FROM Product p JOIN p.variants v WHERE 1=1");

			if (categoryId != null)
				jpql.append(" AND p.category.categoryId = :cid");
			if (minPrice != null)
				jpql.append(" AND v.price >= :minPrice");
			if (maxPrice != null)
				jpql.append(" AND v.price <= :maxPrice");

			TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);

			if (categoryId != null)
				query.setParameter("cid", categoryId);
			if (minPrice != null)
				query.setParameter("minPrice", minPrice);
			if (maxPrice != null)
				query.setParameter("maxPrice", maxPrice);

			return query.getSingleResult();
		} finally {
			em.close();
		}
	}

	@Override
	public Product findByIdWithVariants(int productId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT p FROM Product p LEFT JOIN FETCH p.variants WHERE p.id = :pid";
			return em.createQuery(jpql, Product.class).setParameter("pid", productId).getSingleResult();
		} finally {
			em.close();
		}
	}

	// vendor dashboard
	@Override
	public long countByShopId(int shopId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT COUNT(p) FROM Product p WHERE p.shop.shopId = :sid";
			return em.createQuery(jpql, Long.class).setParameter("sid", shopId).getSingleResult();
		} finally {
			em.close();
		}
	}

	@Override
	// Top sản phẩm bán chạy theo shop
	public List<Object[]> getTopSellingProducts(int shopId, int limit) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String hql = """
					    SELECT od.productVariant.product, SUM(od.quantity), SUM(od.price * od.quantity)
					    FROM OrderDetail od
					    JOIN od.order o
					    WHERE od.productVariant.product.shop.shopId = :sid
					      AND o.status = :status
					    GROUP BY od.productVariant.product
					    ORDER BY SUM(od.quantity) DESC
					""";
			return em.createQuery(hql).setParameter("sid", shopId).setParameter("status", "Đã giao")
					.setMaxResults(limit).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Object[]> getProductCountByCategory(int shopId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = """
					    SELECT p.category.name, COUNT(p)
					    FROM Product p
					    WHERE p.shop.shopId = :sid
					    GROUP BY p.category.name
					""";
			return em.createQuery(jpql, Object[].class).setParameter("sid", shopId).getResultList();
		} finally {
			em.close();
		}
	}

	// Lấy top sản phẩm bán chạy (theo tổng số lượng trong OrderDetail)
	@Override
	public List<Object[]> findBestSellingProducts(int limit) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        String jpql = """
	            SELECT 
	                p.productId,
	                p.name,
	                SUM(od.quantity) AS totalSold,
	                s.shopId,
	                s.name,
	                MIN(pv.price),
	                MAX(pv.price)
	            FROM OrderDetail od
	            JOIN od.productVariant pv
	            JOIN pv.product p
	            JOIN p.shop s
	            JOIN od.order o
	            WHERE o.status = :status
	            GROUP BY p.productId, p.name, s.shopId, s.name
	            ORDER BY SUM(od.quantity) DESC
	        """;

	        return em.createQuery(jpql, Object[].class)
	                 .setParameter("status", "Đã giao")  // ✅ Unicode-safe
	                 .setMaxResults(limit)
	                 .getResultList();

	    } catch (Exception e) {
	        e.printStackTrace();
	        return List.of();
	    } finally {
	        em.close();
	    }
	}



}
