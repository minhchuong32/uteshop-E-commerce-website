package ute.shop.dao.impl;

import java.util.Map;
import java.util.Map.Entry;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import ute.shop.config.JPAConfig;
import ute.shop.dao.IProductVariantDao;
import ute.shop.entity.ProductVariant;

public class ProductVariantDaoImpl implements IProductVariantDao{

	@Override
	public ProductVariant findById(Integer variantId) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(ProductVariant.class, variantId);
        } finally {
            em.close();
        }
	}
	@Override
	public ProductVariant findByOptions(Integer productId, Map<String, Object> selectedOptions) {
		EntityManager em = JPAConfig.getEntityManager();
	    try {
	        StringBuilder jpql = new StringBuilder("SELECT v FROM ProductVariant v WHERE v.product.productId = :pid");
	        for (String key : selectedOptions.keySet()) {
	            jpql.append(" AND v.optionName = :optName AND v.optionValue = :optValue");
	        }

	        TypedQuery<ProductVariant> query = em.createQuery(jpql.toString(), ProductVariant.class);
	        query.setParameter("pid", productId);
	        for (Entry<String, Object> e : selectedOptions.entrySet()) {
	            query.setParameter("optName", e.getKey());
	            query.setParameter("optValue", e.getValue());
	        }

	        return query.getResultStream().findFirst().orElse(null);
	    } finally {
	        em.close();
	    }
	}

}
