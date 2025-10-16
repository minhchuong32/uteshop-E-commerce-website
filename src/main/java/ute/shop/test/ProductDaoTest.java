package ute.shop.test;

import ute.shop.dao.impl.ProductDaoImpl;
import ute.shop.entity.Product;
import java.util.List;

public class ProductDaoTest {
    public static void main(String[] args) {
        ProductDaoImpl dao = new ProductDaoImpl();

        // Gọi thử hàm filterProductsByShop()
        int shopId = 1;
        Integer categoryId = null;
        Double minPrice = null;
        Double maxPrice = null;
        String sortBy = "priceAsc"; // có thể đổi thành "priceDesc" hoặc null
        int page = 1;
        int size = 10;

        try {
            List<Product> products = dao.filterProductsByShop(
                    shopId, categoryId, minPrice, maxPrice, sortBy, page, size
            );

            System.out.println("===== Kết quả lọc sản phẩm =====");
            for (Product p : products) {
                System.out.println("ID: " + p.getProductId() + " | Tên: " + p.getName());
            }

            if (products.isEmpty()) {
                System.out.println("Không có sản phẩm nào phù hợp.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
