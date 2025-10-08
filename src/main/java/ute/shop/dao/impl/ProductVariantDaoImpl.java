package ute.shop.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ute.shop.dao.IProductVariantDao;
import ute.shop.entity.ProductVariant;

public class ProductVariantDaoImpl implements IProductVariantDao{
	private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UteShop");
	@Override
	public ProductVariant findById(Integer variantId) {
		EntityManager em = emf.createEntityManager();
        try {
            return em.find(ProductVariant.class, variantId);
        } finally {
            em.close();
        }
	}

}
