package ute.shop.report;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.servlet.http.HttpServletRequest;
import ute.shop.service.IRevenueService;
import ute.shop.service.impl.RevenueServiceImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Lớp con — chỉ viết phần nội dung doanh thu
 */
public class RevenueReportGenerator extends AbstractPdfReportGenerator {

    private final IRevenueService revenueService = new RevenueServiceImpl();

    @Override
    protected String getFileName() { return "BaoCaoDoanhThu.pdf"; }

    @Override
    protected String getReportTitle() { return "BÁO CÁO TỔNG QUAN DOANH THU"; }

    @Override
    protected PageSize getPageSize() { return PageSize.A4; } // A4 đứng

    @Override
    protected String getErrorRedirectUrl() { return "/admin/revenue?error=exportFailed"; }

    @Override
    protected void writeContent(HttpServletRequest req,
                                Document document,
                                PdfFont font) throws Exception {

        String feeParam = req.getParameter("fee");
        BigDecimal feeRate = new BigDecimal(feeParam != null ? feeParam : "0.10");

        BigDecimal totalRevenue = revenueService.getTotalRevenueAfterFee(feeRate);
        BigDecimal platformFee  = revenueService.getTotalPlatformFee(feeRate);
        List<Object[]> data     = revenueService.getRevenueByMonth();
        String analysis         = revenueService.analyzeGrowthAndForecast(data);

        // --- Bảng tổng quan ---
        Table overview = new Table(UnitValue.createPercentArray(new float[]{3,2}))
                .useAllAvailableWidth();
        overview.addCell(createHeaderCell("Hạng mục", font));
        overview.addCell(createHeaderCell("Giá trị", font));
        overview.addCell(createDataCell("Doanh thu sau phí của Shop", font));
        overview.addCell(createDataCell(String.format("%,.0f ₫", totalRevenue), font));
        overview.addCell(createDataCell("Phí sàn UTESHOP thu được", font));
        overview.addCell(createDataCell(String.format("%,.0f ₫", platformFee), font));
        document.add(overview.setMarginBottom(20));

        // --- Phân tích ---
        document.add(new Paragraph("Phân tích & Dự báo")
                .setFont(font).setFontSize(14).setBold());
        document.add(new Paragraph(analysis)
                .setFont(font).setFontSize(10).setItalic().setMarginBottom(20));

        // --- Bảng theo tháng ---
        document.add(new Paragraph("Chi tiết doanh thu theo tháng")
                .setFont(font).setFontSize(14).setBold());
        Table monthly = new Table(UnitValue.createPercentArray(new float[]{1,2}))
                .useAllAvailableWidth();
        monthly.addHeaderCell(createHeaderCell("Tháng", font));
        monthly.addHeaderCell(createHeaderCell("Doanh thu (trước phí)", font));
        for (Object[] row : data) {
            monthly.addCell(createDataCell("Tháng " + ((Number) row[0]).intValue(), font));
            monthly.addCell(createDataCell(
                    String.format("%,.0f ₫", (BigDecimal) row[1]), font));
        }
        document.add(monthly);
    }
}