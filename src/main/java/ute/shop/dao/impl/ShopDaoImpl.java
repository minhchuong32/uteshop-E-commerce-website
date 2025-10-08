package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.dao.IShopDao;
import ute.shop.entity.Shop;

import java.sql.Timestamp;
import java.util.List;

public class ShopDaoImpl implements IShopDao {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UteShop");

    @Override
    public List<Shop> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Shop s ORDER BY s.shopId", Shop.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Shop getById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Shop.class, id); // nếu không có thì trả null
        } finally {
            em.close();
        }
    }


    @Override
    public void insert(Shop shop) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            shop.setCreatedAt(new Timestamp(System.currentTimeMillis())); // set created_at
            em.persist(shop);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Shop shop) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(shop);  // merge sẽ update entity
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
            Shop s = em.find(Shop.class, id);
            if (s != null) em.remove(s);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    @Override
    public Shop findByUserId(int userId) {
    	EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            return em.createQuery("SELECT s FROM Shop s WHERE s.user.userId = :uid", Shop.class)
                     .setParameter("uid", userId)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
