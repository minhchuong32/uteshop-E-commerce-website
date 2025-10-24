package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.config.JPAConfig;
import ute.shop.dao.IContactDao;
import ute.shop.entity.Contact;

import java.util.Date;
import java.util.List;

public class ContactDaoImpl implements IContactDao {

    @Override
    public boolean insert(Contact c) {
    	 EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            c.setCreatedAt(new Date());
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

    @Override
    public List<Contact> findAll() {
         EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT c FROM Contact c ORDER BY c.createdAt DESC";
            TypedQuery<Contact> query = em.createQuery(jpql, Contact.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Contact findById(int contactId) {
         EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(Contact.class, contactId);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(int contactId) {
         EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Contact contact = em.find(Contact.class, contactId);
            if (contact != null) {
                em.remove(contact);
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
}