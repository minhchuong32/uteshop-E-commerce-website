package ute.shop.controller.vendors;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.dao.IProductDao;
import ute.shop.dao.IRevenueDao;
import ute.shop.dao.impl.ProductDaoImpl;
import ute.shop.dao.impl.RevenueDaoImpl;
import ute.shop.entity.Shop;

@WebServlet(urlPatterns = { "/vendor/stats" })
public class StatController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IRevenueDao revenueDao = new RevenueDaoImpl();
    private final IProductDao productDao = new ProductDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Shop shop = (Shop) session.getAttribute("currentShop");
        if (shop == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int shopId = shop.getShopId();

        // --- Doanh thu tháng ---
        Map<String, Object> revenueData = calculateRevenueChange(shopId);

        // --- Top 3 danh mục sản phẩm ---
        List<Map<String, Object>> topCategories = calculateTopCategories(shopId);

        // --- Gửi sang JSP ---
        req.setAttribute("currentMonthRevenue", revenueData.get("current"));
        req.setAttribute("previousMonthRevenue", revenueData.get("previous"));
        req.setAttribute("changePercent", revenueData.get("changePercent"));
        req.setAttribute("topCategories", topCategories);

        req.setAttribute("page", "stats");
        req.setAttribute("view", "/views/vendor/stats.jsp");
        req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
    }

    /**
     * 🔹 Tính doanh thu tháng hiện tại, tháng trước và phần trăm thay đổi
     */
    private Map<String, Object> calculateRevenueChange(int shopId) {
        List<Object[]> revenueByMonth = revenueDao.getRevenueByMonthByShop(shopId);

        BigDecimal currentMonthRevenue = BigDecimal.ZERO;
        BigDecimal previousMonthRevenue = BigDecimal.ZERO;

        java.time.Month currentMonth = java.time.LocalDate.now().getMonth();
        int currentMonthValue = currentMonth.getValue();

        for (Object[] row : revenueByMonth) {
            int month = ((Number) row[0]).intValue();
            BigDecimal total = (BigDecimal) row[1];
            if (month == currentMonthValue) {
                currentMonthRevenue = total;
            } else if (month == currentMonthValue - 1) {
                previousMonthRevenue = total;
            }
        }

        BigDecimal changePercent = BigDecimal.ZERO;
        if (previousMonthRevenue.compareTo(BigDecimal.ZERO) > 0) {
            changePercent = currentMonthRevenue.subtract(previousMonthRevenue)
                    .divide(previousMonthRevenue, 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("current", currentMonthRevenue);
        result.put("previous", previousMonthRevenue);
        result.put("changePercent", changePercent);
        return result;
    }

    /**
     * 🔹 Lấy danh sách 3 danh mục có tỉ lệ sản phẩm cao nhất
     */
    private List<Map<String, Object>> calculateTopCategories(int shopId) {
        List<Object[]> rawCategories = productDao.getProductCountByCategory(shopId);

        long total = rawCategories.stream()
                .mapToLong(r -> ((Number) r[1]).longValue())
                .sum();

        List<Map<String, Object>> topCategories = new ArrayList<>();
        for (Object[] row : rawCategories) {
            String name = (String) row[0];
            long count = ((Number) row[1]).longValue();
            BigDecimal percent = BigDecimal.ZERO;

            if (total > 0) {
                percent = BigDecimal.valueOf(count * 100.0 / total)
                        .setScale(1, RoundingMode.HALF_UP);
            }

            Map<String, Object> cat = new HashMap<>();
            cat.put("name", name);
            cat.put("count", count);
            cat.put("percent", percent);
            topCategories.add(cat);
        }

        topCategories.sort((a, b) -> ((BigDecimal) b.get("percent"))
                .compareTo((BigDecimal) a.get("percent")));

        return topCategories.size() > 3 ? topCategories.subList(0, 3) : topCategories;
    }
}
