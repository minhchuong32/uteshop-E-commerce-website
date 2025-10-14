package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.config.JPAConfig;
import ute.shop.dao.IComplaintMessageDao;
import ute.shop.entity.ComplaintMessage;

import java.util.List;

public class ComplaintMessageDaoImpl implements IComplaintMessageDao {
	


    public List<ComplaintMessage> findByComplaintId(int complaintId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT m FROM ComplaintMessage m "
                        + "LEFT JOIN FETCH m.sender "
                        + "WHERE m.complaint.complaintId = :id "
                        + "ORDER BY m.createdAt ASC";
            return em.createQuery(jpql, ComplaintMessage.class)
                     .setParameter("id", complaintId)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public void insert(ComplaintMessage message) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(message);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
