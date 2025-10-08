package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.service.impl.RevenueServiceImpl;
import ute.shop.utils.PDFGenerator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/revenue/export"})
public class RevenueReportController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final RevenueServiceImpl revenueService = new RevenueServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ✅ Lấy tỷ lệ chiết khấu hiện tại (hoặc mặc định 10%)
        String feeParam = req.getParameter("fee");
        BigDecimal platformFeeRate = new BigDecimal(feeParam != null ? feeParam : "0.10");

        BigDecimal totalRevenue = revenueService.getTotalRevenueAfterFee(platformFeeRate);
        BigDecimal platformFee = revenueService.getTotalPlatformFee(platformFeeRate);
        List<Object[]> revenueByMonth = revenueService.getRevenueByMonth();

        String analysis = "Báo cáo được tạo tự động. "
                + "Tỷ lệ chiết khấu hiện tại là " + platformFeeRate.multiply(BigDecimal.valueOf(100)) + "%, "
                + "phí sàn là " + formatCurrency(platformFee) + ", "
                + "doanh thu sau phí là " + formatCurrency(totalRevenue) + ".";

        // --- Xuất file PDF ---
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=DoanhThu_UteShop.pdf");

        try {
            PDFGenerator.generateRevenueReport(
                    resp.getOutputStream(),
                    totalRevenue,
                    platformFee,
                    platformFeeRate.multiply(BigDecimal.valueOf(100)),
                    revenueByMonth,
                    analysis
            );
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Lỗi khi tạo file PDF: " + e.getMessage());
        }
    }

    private String formatCurrency(BigDecimal amount) {
        return String.format("%,.0f ₫", amount);
    }
}
