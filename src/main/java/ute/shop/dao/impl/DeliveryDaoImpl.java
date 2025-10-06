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

	@Override
	public void updateStatus(Integer deliveryId, String status) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Delivery d = em.find(Delivery.class, deliveryId);
			if (d != null) {
				d.setStatus(status);
				em.merge(d);
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

}
