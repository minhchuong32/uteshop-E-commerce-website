package ute.shop.test;

import ute.shop.config.JPAConfig;

public class TestSingleton {

    public static void main(String[] args) {

        JPAConfig config1 = JPAConfig.getInstance();
        JPAConfig config2 = JPAConfig.getInstance();

        System.out.println("Instance 1: " + config1);
        System.out.println("Instance 2: " + config2);

        System.out.println("HashCode 1: " + config1.hashCode());
        System.out.println("HashCode 2: " + config2.hashCode());

        if (config1 == config2) {
            System.out.println("✅ Singleton hoạt động: chỉ có 1 instance");
        } else {
            System.out.println("❌ Có nhiều instance");
        }
    }
}