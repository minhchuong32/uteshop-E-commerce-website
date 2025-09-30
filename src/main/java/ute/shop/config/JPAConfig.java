package ute.shop.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig {

    private static final EntityManagerFactory emf;

    // Khởi tạo 1 lần duy nhất khi class load
    static {
        try {
            emf = Persistence.createEntityManagerFactory("UteShop");
        } catch (Throwable ex) {
            System.err.println("Initial EntityManagerFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Lấy EntityManager
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Đóng khi shutdown (có thể gọi trong ServletContextListener)
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
