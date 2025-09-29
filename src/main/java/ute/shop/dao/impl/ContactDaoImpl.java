package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.dao.IContactDao;
import ute.shop.entity.Contact;

import java.util.Date;

public class ContactDaoImpl implements IContactDao {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UteShop");

    @Override
    public boolean insert(Contact c) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            c.setCreatedAt(new Date()); // tự động set created_at = now()
            em.persist(c);
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
}
