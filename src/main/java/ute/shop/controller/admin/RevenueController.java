package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.service.impl.RevenueServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/admin/revenue"})
public class RevenueController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final RevenueServiceImpl revenueService = new RevenueServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //  Lấy phần trăm chiết khấu từ request (mặc định 10%)
        String feeParam = req.getParameter("fee");
        BigDecimal platformFeeRate = new BigDecimal(feeParam != null ? feeParam : "0.10");

        //  Gọi service
        BigDecimal totalRevenue = revenueService.getTotalRevenueAfterFee(platformFeeRate);
        BigDecimal platformFee = revenueService.getTotalPlatformFee(platformFeeRate);
        List<Object[]> revenueByMonth = revenueService.getRevenueByMonth();

        //  Chuẩn bị dữ liệu biểu đồ
        String months = revenueByMonth.stream()
                .map(r -> String.valueOf(r[0]))
                .collect(Collectors.joining(","));
        String revenues = revenueByMonth.stream()
                .map(r -> String.valueOf(r[1]))
                .collect(Collectors.joining(","));

        //  Phân tích doanh thu động
        String analysis = "Với mức chiết khấu hiện tại là " + (platformFeeRate.multiply(BigDecimal.valueOf(100))) + "%, "
                + "doanh thu sau phí là " + formatCurrency(totalRevenue)
                + ", còn phí sàn là " + formatCurrency(platformFee)
                + ". Mức chiết khấu cao có thể làm giảm lợi nhuận ròng, "
                + "nhưng tăng thu nhập cho sàn thương mại.";

        req.setAttribute("totalRevenue", totalRevenue);
        req.setAttribute("platformFee", platformFee);
        req.setAttribute("months", months);
        req.setAttribute("revenues", revenues);
        req.setAttribute("analysis", analysis);
        req.setAttribute("feeRate", platformFeeRate.multiply(BigDecimal.valueOf(100)).intValue()); // hiển thị %

    	req.setAttribute("page", "revenue"); // css
    	req.setAttribute("view", "/views/admin/revenue.jsp"); // render -> main
    	req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp); // sitemesh
    }

    private String formatCurrency(BigDecimal amount) {
        return String.format("%,.0f ₫", amount);
    }
}
