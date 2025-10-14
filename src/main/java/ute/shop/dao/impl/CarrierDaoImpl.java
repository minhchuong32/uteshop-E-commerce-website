package ute.shop.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ute.shop.config.JPAConfig;
import ute.shop.dao.ICarrierDao;
import ute.shop.entity.Carrier;

public class CarrierDaoImpl implements ICarrierDao {

    @Override
    public List<Carrier> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        TypedQuery<Carrier> query = em.createQuery("SELECT c FROM Carrier c", Carrier.class);
        return query.getResultList();
    }

    @Override
    public Carrier findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        return em.find(Carrier.class, id);
    }

    @Override
    public void save(Carrier carrier) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(carrier);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Carrier carrier) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(carrier);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            Carrier c = em.find(Carrier.class, id);
            if (c != null) em.remove(c);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
