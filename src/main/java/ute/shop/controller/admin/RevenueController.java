package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.service.impl.*;
import ute.shop.service.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(urlPatterns = {"/admin/revenue"})
public class RevenueController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IRevenueService revenueService = new RevenueServiceImpl();
    private final IShopService shopService = new ShopServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
        	// phân tích tăng trưởng 
        	List<Object[]> r_data = revenueService.getRevenueByMonth();
        	String advancedAnalysis = revenueService.analyzeGrowthAndForecast(r_data);
        	req.setAttribute("advancedAnalysis", advancedAnalysis);
        	
            // ===== Lấy tham số lọc và chiết khấu =====
            String startDateParam = req.getParameter("startDate");
            String endDateParam = req.getParameter("endDate");
            String shopIdParam = req.getParameter("shopId");
            String feeParam = req.getParameter("fee");

            Date startDate = null;
            Date endDate = null;
            Integer shopId = null;
            BigDecimal platformFeeRate = new BigDecimal(feeParam != null ? feeParam : "0.10");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (startDateParam != null && !startDateParam.isEmpty()) startDate = sdf.parse(startDateParam);
            if (endDateParam != null && !endDateParam.isEmpty()) endDate = sdf.parse(endDateParam);
            if (shopIdParam != null && !shopIdParam.isEmpty() && !shopIdParam.equals("all"))
                shopId = Integer.parseInt(shopIdParam);

            // ===== Lấy dữ liệu doanh thu (lọc động) =====
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

            // =====  Tính tổng doanh thu & phí sàn =====
            BigDecimal totalRevenue = revenueService.getTotalRevenueAfterFee(platformFeeRate);
            BigDecimal platformFee = revenueService.getTotalPlatformFee(platformFeeRate);

            // ===== Phân tích doanh thu động =====
            String analysis = "Với mức chiết khấu hiện tại là " + (platformFeeRate.multiply(BigDecimal.valueOf(100))) + "%, "
                    + "doanh thu sau phí là " + formatCurrency(totalRevenue)
                    + ", còn phí sàn là " + formatCurrency(platformFee)
                    + ". Mức chiết khấu cao có thể làm giảm lợi nhuận ròng, "
                    + "nhưng tăng thu nhập cho sàn thương mại.";

            // =====  Truyền dữ liệu ra JSP =====
            req.setAttribute("months", months.toString());
            req.setAttribute("revenues", revenues.toString());
            req.setAttribute("totalRevenue", totalRevenue);
            req.setAttribute("platformFee", platformFee);
            req.setAttribute("analysis", analysis);
            req.setAttribute("feeRate", platformFeeRate.multiply(BigDecimal.valueOf(100)).intValue());

            // Dữ liệu cho bộ lọc
            req.setAttribute("shops", shopService.getAll());
            req.setAttribute("selectedShopId", shopId);
            req.setAttribute("startDate", startDateParam);
            req.setAttribute("endDate", endDateParam);

            // =====  Forward sang JSP =====
            req.setAttribute("page", "revenue");
            req.setAttribute("view", "/views/admin/revenue.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải thống kê doanh thu");
        }
    }

    private String formatCurrency(BigDecimal amount) {
        return String.format("%,.0f ₫", amount);
    }
}
