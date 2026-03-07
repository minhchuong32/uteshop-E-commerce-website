
//Mỗi lần gọi DAO:
//
//new DBConnection()
//      ↓
//createEntityManagerFactory()
//      ↓
//createEntityManager()
//
//Nếu website có 1000 request
//
//1000 EntityManagerFactory được tạo
//
//Trong khi EntityManagerFactory rất nặng.


//* Nâng cấp: Toàn bộ hệ thống chỉ có 1 EntityManagerFactory
//package ute.shop.config;
//

//. Luồng hoạt động sau khi nâng cấp
//UserDAO
//   ↓
//DBConnection.getInstance()
//   ↓
//1 EntityManagerFactory duy nhất
//   ↓
//createEntityManager()
//   ↓
//Database
//4. So sánh Code Cũ vs Code Singleton
//Tiêu chí				Code cũ				Singleton
//EntityManagerFactory	tạo mỗi request		chỉ tạo 1 lần
//Memory				tốn RAM				tối ưu
//Performance			chậm				nhanh
//Design Pattern		❌					✅ Singleton
//Khả năng mở rộng		thấp				tốt

//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//public class DBConnection {
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