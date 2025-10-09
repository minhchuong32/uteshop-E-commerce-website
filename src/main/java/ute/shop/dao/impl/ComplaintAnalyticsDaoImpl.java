package ute.shop.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import ute.shop.config.JPAConfig;
import ute.shop.dao.IComplaintAnalyticsDao;

import java.util.List;

public class ComplaintAnalyticsDaoImpl implements IComplaintAnalyticsDao {

    @Override
    public long countAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            Query q = em.createQuery("SELECT COUNT(c.complaintId) FROM Complaint c");
            return (long) q.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> countByStatus() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String hql = "SELECT c.status, COUNT(c) FROM Complaint c GROUP BY c.status";
            return em.createQuery(hql).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> countByMonth() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String hql = """
                SELECT MONTH(c.createdAt), COUNT(c)
                FROM Complaint c
                GROUP BY MONTH(c.createdAt)
                ORDER BY MONTH(c.createdAt)
            """;
            return em.createQuery(hql).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> topUsers(int limit) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String hql = """
                SELECT c.user.name, COUNT(c)
                FROM Complaint c
                GROUP BY c.user.name
                ORDER BY COUNT(c) DESC
            """;
            Query q = em.createQuery(hql);
            q.setMaxResults(limit);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
