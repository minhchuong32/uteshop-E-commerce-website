package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.service.impl.RevenueServiceImpl;
import ute.shop.service.impl.ShopServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(urlPatterns = {"/admin/revenue/filter"})
public class RevenueFilterController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final RevenueServiceImpl revenueService = new RevenueServiceImpl();
    private final ShopServiceImpl shopService = new ShopServiceImpl(); // để load danh sách shop

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // ===== Lấy tham số lọc =====
            String startDateParam = req.getParameter("startDate");
            String endDateParam = req.getParameter("endDate");
            String shopIdParam = req.getParameter("shopId");

            Date startDate = null;
            Date endDate = null;
            Integer shopId = null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (startDateParam != null && !startDateParam.isEmpty()) {
                startDate = sdf.parse(startDateParam);
            }
            if (endDateParam != null && !endDateParam.isEmpty()) {
                endDate = sdf.parse(endDateParam);
            }
            if (shopIdParam != null && !shopIdParam.isEmpty() && !shopIdParam.equals("all")) {
                shopId = Integer.parseInt(shopIdParam);
            }

            // ===== Lấy dữ liệu doanh thu =====
            List<Object[]> revenueData = revenueService.getRevenueByFilter(startDate, endDate, shopId);

            // Tạo map doanh thu theo tháng
            Map<Integer, BigDecimal> monthMap = new HashMap<>();
            for (Object[] row : revenueData) {
                int month = ((Number) row[0]).intValue();
                BigDecimal amount = (BigDecimal) row[1];
                monthMap.put(month, amount);
            }

            // Sinh dữ liệu cho Chart.js (12 tháng)
            StringBuilder months = new StringBuilder();
            StringBuilder revenues = new StringBuilder();
            for (int i = 1; i <= 12; i++) {
                months.append("'Tháng ").append(i).append("'");
                BigDecimal revenue = monthMap.getOrDefault(i, BigDecimal.ZERO);
                revenues.append(revenue);
                if (i < 12) {
                    months.append(",");
                    revenues.append(",");
                }
            }

            // ===== Gọi service thống kê tổng =====
            BigDecimal total = revenueService.getTotalRevenueAfterFee(BigDecimal.valueOf(0.10));
            BigDecimal fee = revenueService.getTotalPlatformFee(BigDecimal.valueOf(0.10));

            // ===== Truyền dữ liệu ra JSP =====
            req.setAttribute("months", months.toString());
            req.setAttribute("revenues", revenues.toString());
            req.setAttribute("totalRevenue", total);
            req.setAttribute("platformFee", fee);
            req.setAttribute("shops", shopService.getAll());
            req.setAttribute("selectedShopId", shopId);
            req.setAttribute("startDate", startDateParam);
            req.setAttribute("endDate", endDateParam);

            req.setAttribute("view", "/views/admin/revenue_filter.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi lọc doanh thu");
        }
    }
}
