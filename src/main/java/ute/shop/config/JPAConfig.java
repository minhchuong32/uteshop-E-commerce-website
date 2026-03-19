
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//public class JPAConfig {
//
//    public EntityManager getEntityManager() {
//
//        EntityManagerFactory emf =
//                Persistence.createEntityManagerFactory("UteShop");
//
//        return emf.createEntityManager();
//    }
//}


package ute.shop.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig {

    private static JPAConfig instance;
    private EntityManagerFactory emf;

    // constructor private
    private JPAConfig() {
        emf = Persistence.createEntityManagerFactory("UteShop");
    }

    // Singleton instance
    public static synchronized JPAConfig getInstance() {
        if (instance == null) {
            instance = new JPAConfig();
        }
        return instance;
    }

    // create entity manager
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // close when server shutdown
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}