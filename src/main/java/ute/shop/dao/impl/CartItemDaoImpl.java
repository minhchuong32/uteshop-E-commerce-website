package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.dao.ICartItemDao;
import ute.shop.entity.CartItem;
import ute.shop.entity.User;
import ute.shop.entity.Product;

import java.util.List;

public class CartItemDaoImpl implements ICartItemDao {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UteShop");

    @Override
    public boolean insert(CartItem item) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(item);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean update(CartItem item) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(item);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(Integer cartItemId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CartItem item = em.find(CartItem.class, cartItemId);
            if (item != null) {
                em.remove(item);
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
    public CartItem findById(Integer cartItemId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(CartItem.class, cartItemId);
        } finally {
            em.close();
        }
    }

    @Override
    public List<CartItem> findByUser(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM CartItem c WHERE c.user = :user", CartItem.class)
                     .setParameter("user", user)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public CartItem findByUserAndProduct(User user, Product product) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM CartItem c WHERE c.user = :user AND c.product = :product", CartItem.class)
                     .setParameter("user", user)
                     .setParameter("product", product)
                     .getResultStream()
                     .findFirst()
                     .orElse(null);
        } finally {
            em.close();
        }
    }
}
