package ute.shop.test;

import jakarta.persistence.EntityManager;

import ute.shop.config.JPAConfig;

public class TestJPA {

    public static void main(String[] args) {

        EntityManager em = null;

        try {
            // Lấy EntityManager từ Singleton
            em = JPAConfig.getInstance().getEntityManager();

            // Test kết nối
            if (em != null) {
                System.out.println("✅ Kết nối JPA thành công!");
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi kết nối JPA");
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}