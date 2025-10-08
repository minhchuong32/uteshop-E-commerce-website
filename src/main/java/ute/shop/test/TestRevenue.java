package ute.shop.test;


import ute.shop.service.impl.RevenueServiceImpl;

import java.math.BigDecimal;

public class TestRevenue {
    public static void main(String[] args) {
        try {
            System.out.println("=== KIỂM TRA CHỨC NĂNG QUẢN LÝ DOANH THU ===");

            // Gọi service
            RevenueServiceImpl revenueService = new RevenueServiceImpl();
            BigDecimal totalRevenue = revenueService.getTotalRevenueAfterFee();
            BigDecimal platformFee = revenueService.getTotalPlatformFee();

            System.out.println("Tổng phí sàn thương mại (10%): " + formatVND(platformFee));
            System.out.println("Tổng doanh thu sau chiết khấu: " + formatVND(totalRevenue));
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
