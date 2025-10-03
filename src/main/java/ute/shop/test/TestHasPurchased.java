package ute.shop.test;

import ute.shop.dao.impl.OrderDaoImpl;

public class TestHasPurchased {
    public static void main(String[] args) {
    	OrderDaoImpl dao = new OrderDaoImpl();

        int userId = 1;       // ID user trong DB
        int productId = 5;    // ID product trong DB

        boolean result = dao.hasPurchased(userId, productId);

        if (result) {
            System.out.println("✅ User " + userId + " đã mua product " + productId);
        } else {
            System.out.println("❌ User " + userId + " chưa mua product " + productId);
        }
    }
}
