package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.dao.IOrderDao;
import ute.shop.entity.Order;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements IOrderDao {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UteShop");

    @Override
    public List<Order> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT o FROM Order o ORDER BY o.createdAt DESC", Order.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Order> getById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Order.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Order order) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            order.setCreatedAt(new Timestamp(System.currentTimeMillis())); // tương đương GETDATE()
            em.persist(order);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Order order) {
        EntityManager em = emf.createEntityManager();
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
        EntityManager em = emf.createEntityManager();
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
}
