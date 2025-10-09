package ute.shop.test;

import ute.shop.service.impl.*;
import ute.shop.entity.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

public class TestAdminDashboard {

    public static void main(String[] args) {
        System.out.println("=== 🧭 KIỂM TRA DỮ LIỆU DASHBOARD ADMIN ===\n");

        try {
            // Tạo service
            UserServiceImpl userService = new UserServiceImpl();
            ProductServiceImpl productService = new ProductServiceImpl();
            OrderServiceImpl orderService = new OrderServiceImpl();
            RevenueServiceImpl revenueService = new RevenueServiceImpl();

            // ==== 1️⃣ Thống kê tổng quan ====
            long totalUsers = userService.countAllUsers();
            long totalProducts = productService.countAll();
            long totalOrders = orderService.countAllOrders();
            BigDecimal totalRevenue = revenueService.getTotalRevenueAfterFee(BigDecimal.valueOf(0.10));

            System.out.println("📊 Tổng quan hệ thống:");
            System.out.println("  👤 Tổng người dùng:     " + totalUsers);
            System.out.println("  📦 Tổng sản phẩm:       " + totalProducts);
            System.out.println("  🛒 Tổng đơn hàng:       " + totalOrders);
            System.out.println("  💰 Tổng doanh thu (sau 10% phí): " + formatVND(totalRevenue));
            System.out.println("-----------------------------------------------------\n");

            // ==== 2️⃣ Đơn hàng gần đây ====
            System.out.println("🕒 Đơn hàng gần đây:");
            List<Order> recentOrders = orderService.findRecentOrders(5);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            if (recentOrders.isEmpty()) {
                System.out.println("  ⚠️ Không có đơn hàng nào gần đây.\n");
            } else {
                for (Order o : recentOrders) {
                    System.out.printf("  • #%d | %s | %s | %s | %s%n",
                            o.getOrderId(),
                            sdf.format(o.getCreatedAt()),
                            o.getStatus(),
                            o.getPaymentMethod(),
                            formatVND(o.getTotalAmount()));
                }
                System.out.println();
            }

         // ==== 3️⃣ Sản phẩm bán chạy ====
            System.out.println("🔥 Top 5 sản phẩm bán chạy:");
            List<Object[]> bestSellers = productService.findBestSellingProducts(5);
            if (bestSellers.isEmpty()) {
                System.out.println("  ⚠️ Chưa có sản phẩm nào được bán.\n");
            } else {
                int rank = 1;
                for (Object[] row : bestSellers) {
                    // Đọc đúng thứ tự cột trong JPQL
                    Integer productId   = (Integer) row[0];
                    String productName  = (String)  row[1];
                    Long totalSold      = ((Number) row[2]).longValue();
                    Integer shopId      = (Integer) row[3];
                    String shopName     = (String)  row[4];
                    BigDecimal minPrice = (BigDecimal) row[5];
                    BigDecimal maxPrice = (BigDecimal) row[6];

                    System.out.printf(
                        "  #%d️⃣ %s | %d lượt bán | Shop: %s | Giá: %,.0f₫ → %,.0f₫%n",
                        rank++, productName, totalSold, shopName, minPrice, maxPrice
                    );
                }
                System.out.println();
            }


        } catch (Exception e) {
            System.err.println("❌ Lỗi khi kiểm tra dữ liệu dashboard:");
            e.printStackTrace();
        }
    }

    // Hàm format tiền tệ
    private static String formatVND(BigDecimal amount) {
        if (amount == null) return "0 ₫";
        return String.format("%,.0f ₫", amount);
    }
}
