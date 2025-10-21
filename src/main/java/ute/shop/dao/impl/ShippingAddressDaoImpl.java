package ute.shop.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ute.shop.config.JPAConfig;
import ute.shop.dao.IShippingAddressDao;
import ute.shop.entity.ShippingAddress;

public class ShippingAddressDaoImpl implements IShippingAddressDao {

	@Override
	public List<ShippingAddress> findByUserId(int userId) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<ShippingAddress> query = em.createQuery(
                "SELECT s FROM ShippingAddress s WHERE s.user.userId = :uid ORDER BY s.createdAt DESC",
                ShippingAddress.class
            );
            query.setParameter("uid", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
	}

	@Override
	public ShippingAddress findById(int id) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(ShippingAddress.class, id);
        } finally {
            em.close();
        }
	}

	@Override
	public void insert(ShippingAddress address) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(address);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
	}

	@Override
	public void update(ShippingAddress address) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(address);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
	}

	@Override
	public void delete(int id) {
		EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            ShippingAddress addr = em.find(ShippingAddress.class, id);
            if (addr != null) em.remove(addr);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
	}

	@Override
	public void unsetOtherDefaults(int userId) {
		EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery(
                "UPDATE ShippingAddress s SET s.isDefault = false WHERE s.user.userId = :uid"
            ).setParameter("uid", userId)
             .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
	}

	@Override
	public void unsetOtherDefaults(int userId, int excludeId) {
		EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery(
                "UPDATE ShippingAddress s SET s.isDefault = false WHERE s.user.userId = :uid AND s.addressId <> :aid"
            )
            .setParameter("uid", userId)
            .setParameter("aid", excludeId)
            .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
	}
	

}
