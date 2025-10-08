package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.dao.IStoreSettingsDao;
import ute.shop.entity.StoreSettings;


public class StoreSettingsDAOImpl implements IStoreSettingsDao {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UteShop");

    @Override
    public StoreSettings getSettings() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<StoreSettings> query = em.createQuery(
                    "SELECT s FROM StoreSettings s ORDER BY s.id ASC",
                    StoreSettings.class
            );
            query.setMaxResults(1);
            return query.getResultStream().findFirst().orElse(null); //  nếu không có thì null
        } finally {
            em.close();
        }
    }


    @Override
    public boolean updateSettings(StoreSettings settings) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            settings.setUpdatedAt(new java.util.Date()); // tương đương GETDATE()
            em.merge(settings);
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
