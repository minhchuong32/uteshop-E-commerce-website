package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.service.impl.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@WebServlet(urlPatterns = {"/admin/dashboard"})
public class AdminDashboardController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ProductServiceImpl productService = new ProductServiceImpl();
    private final OrderServiceImpl orderService = new OrderServiceImpl();
    private final UserServiceImpl userService = new UserServiceImpl();
    private final RevenueServiceImpl revenueService = new RevenueServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // ===== 1️⃣ Thống kê nhanh =====
            req.setAttribute("totalUsers", userService.countAllUsers());
            req.setAttribute("totalProducts", productService.countAll());
            req.setAttribute("totalOrders", orderService.countAllOrders());
            req.setAttribute("totalRevenue", revenueService.getTotalRevenueAfterFee(BigDecimal.valueOf(0.10)));

            // ===== 2️⃣ Đơn hàng gần đây =====
            req.setAttribute("recentOrders", orderService.findRecentOrders(5));

            // ===== 3️⃣ Top sản phẩm bán chạy =====
            var rawList = productService.findBestSellingProducts(5);
            List<Map<String, Object>> bestSellers = new ArrayList<>();

            for (Object[] row : rawList) {
                Map<String, Object> item = new HashMap<>();
                item.put("productId", row[0]);
                item.put("productName", row[1]);
                item.put("totalSold", ((Number) row[2]).longValue());
                item.put("shopId", row[3]);
                item.put("shopName", row[4]);
                item.put("minPrice", row[5]);
                item.put("maxPrice", row[6]);
                bestSellers.add(item);
            }

            req.setAttribute("bestSellers", bestSellers);

            // ===== 4️⃣ Điều hướng view =====
            req.setAttribute("page", "dashboard");
            req.setAttribute("view", "/views/admin/dashboard.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500, "Lỗi khi tải dashboard admin");
        }
    }
}
