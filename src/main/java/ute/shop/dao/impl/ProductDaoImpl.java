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
				em.remove(p);
			}
			tx.commit();
		} catch (Exception e) {
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
	public List<Product> filterProducts(Integer categoryId, Double minPrice, Double maxPrice, String sortBy, int page, int size) {
		EntityManager em = JPAConfig.getEntityManager();
	    try {
	        StringBuilder jpql = new StringBuilder(
	            "SELECT DISTINCT p FROM Product p JOIN p.variants v WHERE 1=1"
	        );

	        if (categoryId != null) {
	            jpql.append(" AND p.category.categoryId = :cid");
	        }
	        if (minPrice != null) {
	            jpql.append(" AND v.price >= :minPrice");
	        }
	        if (maxPrice != null) {
	            jpql.append(" AND v.price <= :maxPrice");
	        }

	        // Sắp xếp theo min price trong variants
	        if ("priceAsc".equals(sortBy)) {
	            jpql.append(" ORDER BY v.price ASC");
	        } else if ("priceDesc".equals(sortBy)) {
	            jpql.append(" ORDER BY v.price DESC");
	        } else {
	            jpql.append(" ORDER BY p.productId DESC");
	        }

	        TypedQuery<Product> query = em.createQuery(jpql.toString(), Product.class);

	        if (categoryId != null) query.setParameter("cid", categoryId);
	        if (minPrice != null) query.setParameter("minPrice", minPrice);
	        if (maxPrice != null) query.setParameter("maxPrice", maxPrice);

	        return query.setFirstResult((page - 1) * size)
	                    .setMaxResults(size)
	                    .getResultList();
	    } finally {
	        em.close();
	    }
	}

	@Override
	public long countFilterProducts(Integer categoryId, Double minPrice, Double maxPrice) {
		EntityManager em = JPAConfig.getEntityManager();
	    try {
	        StringBuilder jpql = new StringBuilder(
	            "SELECT COUNT(DISTINCT p) FROM Product p JOIN p.variants v WHERE 1=1"
	        );

	        if (categoryId != null) jpql.append(" AND p.category.categoryId = :cid");
	        if (minPrice != null) jpql.append(" AND v.price >= :minPrice");
	        if (maxPrice != null) jpql.append(" AND v.price <= :maxPrice");

	        TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);

	        if (categoryId != null) query.setParameter("cid", categoryId);
	        if (minPrice != null) query.setParameter("minPrice", minPrice);
	        if (maxPrice != null) query.setParameter("maxPrice", maxPrice);

	        return query.getSingleResult();
	    } finally {
	        em.close();
	    }
	}


}
