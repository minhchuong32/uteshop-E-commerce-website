package ute.shop.test;

import ute.shop.service.impl.RevenueServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class TestRevenue {
    public static void main(String[] args) {
        try {
            System.out.println("=== KIỂM TRA CHỨC NĂNG QUẢN LÝ DOANH THU ===");

            // Tạo service
            RevenueServiceImpl revenueService = new RevenueServiceImpl();

            // Tính tổng doanh thu (sau chiết khấu) và phí sàn
            BigDecimal totalRevenue = revenueService.getTotalRevenueAfterFee();
            BigDecimal platformFee = revenueService.getTotalPlatformFee();

            // In tổng doanh thu
            System.out.println("Tổng phí sàn thương mại (10%): " + formatVND(platformFee));
            System.out.println("Tổng doanh thu sau chiết khấu: " + formatVND(totalRevenue));
            System.out.println("--------------------------------------------");

            // Kiểm tra chi tiết doanh thu theo tháng
            List<Object[]> monthlyRevenues = revenueService.getRevenueByMonth();
            if (monthlyRevenues.isEmpty()) {
                System.out.println("❌ Không có dữ liệu doanh thu theo tháng.");
            } else {
                System.out.println("📊 Doanh thu theo từng tháng trong năm:");
                for (Object[] row : monthlyRevenues) {
                    int month = ((Number) row[0]).intValue();
                    BigDecimal amount = (BigDecimal) row[1];
                    System.out.printf("  • Tháng %02d: %s%n", month, formatVND(amount));
                }
            }

            System.out.println("============================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm format tiền Việt Nam Đồng
    private static String formatVND(BigDecimal amount) {
        if (amount == null) return "0 ₫";
        return String.format("%,.0f ₫", amount);
    }
}
