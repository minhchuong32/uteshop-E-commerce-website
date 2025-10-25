package ute.shop.controller.admin;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.service.impl.*;
import ute.shop.service.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(urlPatterns = { "/admin/revenue", "/admin/revenue/report" })
public class RevenueController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final IRevenueService revenueService = new RevenueServiceImpl();
	private final IShopService shopService = new ShopServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();

		// === Nếu truy cập /admin/revenue/report thì sinh file PDF ===
		if (uri.endsWith("/report")) {
			generateRevenueReportPdf(req, resp);
			return;
		}
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
			if (startDateParam != null && !startDateParam.isEmpty())
				startDate = sdf.parse(startDateParam);
			if (endDateParam != null && !endDateParam.isEmpty())
				endDate = sdf.parse(endDateParam);
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

			// ===== Tính tổng doanh thu & phí sàn =====
			BigDecimal totalRevenue = revenueService.getTotalRevenueAfterFee(platformFeeRate);
			BigDecimal platformFee = revenueService.getTotalPlatformFee(platformFeeRate);

			// ===== Phân tích doanh thu động =====
			String analysis = "Với mức chiết khấu hiện tại là " + (platformFeeRate.multiply(BigDecimal.valueOf(100)))
					+ "%, " + "doanh thu sau phí là " + formatCurrency(totalRevenue) + ", còn phí sàn là "
					+ formatCurrency(platformFee) + ". Mức chiết khấu cao có thể làm giảm lợi nhuận ròng, "
					+ "nhưng tăng thu nhập cho sàn thương mại.";

			// ===== Truyền dữ liệu ra JSP =====
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

			// ===== Forward sang JSP =====
			req.setAttribute("page", "revenue");
			req.setAttribute("view", "/views/admin/revenue.jsp");
			req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải thống kê doanh thu");
		}
	}
	private void generateRevenueReportPdf(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Cookie downloadCookie = new Cookie("download_token", "completed");
	    downloadCookie.setPath("/"); 
	    resp.addCookie(downloadCookie);
	    
		resp.setContentType("application/pdf");
		resp.setHeader("Content-Disposition", "attachment; filename=\"BaoCaoDoanhThu.pdf\"");

		try (PdfWriter writer = new PdfWriter(resp.getOutputStream());
			 PdfDocument pdf = new PdfDocument(writer);
			 Document document = new Document(pdf, PageSize.A4)) { // Giấy A4 đứng

			document.setMargins(30, 30, 30, 30);

			// Lấy font tiếng Việt (giống hệt OrderController)
			InputStream fontStream = getServletContext().getResourceAsStream("/fonts/Roboto-Regular.ttf");
			if (fontStream == null) {
				throw new IOException("Không tìm thấy file font tại /fonts/Roboto-Regular.ttf");
			}
			byte[] fontBytes = fontStream.readAllBytes();
			PdfFont vietnameseFont = PdfFontFactory.createFont(fontBytes, "Identity-H", EmbeddingStrategy.PREFER_EMBEDDED, true);

			// Tiêu đề
			document.add(new Paragraph("BÁO CÁO TỔNG QUAN DOANH THU")
					.setFont(vietnameseFont).setFontSize(18).setBold()
					.setTextAlignment(TextAlignment.CENTER));
			document.add(new Paragraph("Ngày xuất: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()))
					.setFont(vietnameseFont).setFontSize(9)
					.setTextAlignment(TextAlignment.CENTER).setMarginBottom(20));
			
			// --- Lấy dữ liệu từ Service (giống hệt logic trong showRevenuePage) ---
			String feeParam = req.getParameter("fee");
			BigDecimal platformFeeRate = new BigDecimal(feeParam != null ? feeParam : "0.10");
			BigDecimal totalRevenue = revenueService.getTotalRevenueAfterFee(platformFeeRate);
			BigDecimal platformFee = revenueService.getTotalPlatformFee(platformFeeRate);
			List<Object[]> revenueData = revenueService.getRevenueByMonth();
			String advancedAnalysis = revenueService.analyzeGrowthAndForecast(revenueData);

			// Phần thống kê tổng quan
			document.add(new Paragraph("Thống kê tổng quan").setFont(vietnameseFont).setFontSize(14).setBold());
			Table overviewTable = new Table(UnitValue.createPercentArray(new float[]{3, 2})).useAllAvailableWidth();
			overviewTable.addCell(createHeaderCell("Hạng mục", vietnameseFont));
			overviewTable.addCell(createHeaderCell("Giá trị", vietnameseFont));
			overviewTable.addCell(createCell("Doanh thu sau phí của Shop", vietnameseFont, false));
			overviewTable.addCell(createCell(formatCurrency(totalRevenue), vietnameseFont, false));
			overviewTable.addCell(createCell("Phí sàn UTESHOP thu được", vietnameseFont, false));
			overviewTable.addCell(createCell(formatCurrency(platformFee), vietnameseFont, false));
			document.add(overviewTable.setMarginBottom(20));

			// Phần phân tích & dự báo
			document.add(new Paragraph("Phân tích & Dự báo").setFont(vietnameseFont).setFontSize(14).setBold());
			document.add(new Paragraph(advancedAnalysis).setFont(vietnameseFont).setFontSize(10).setItalic().setMarginBottom(20));
			
			// Bảng chi tiết doanh thu theo tháng
			document.add(new Paragraph("Chi tiết doanh thu theo tháng (Toàn hệ thống)").setFont(vietnameseFont).setFontSize(14).setBold());
			Table monthlyTable = new Table(UnitValue.createPercentArray(new float[]{1, 2})).useAllAvailableWidth();
			monthlyTable.addHeaderCell(createHeaderCell("Tháng", vietnameseFont));
			monthlyTable.addHeaderCell(createHeaderCell("Doanh thu (trước phí)", vietnameseFont));

			for (Object[] row : revenueData) {
				int month = ((Number) row[0]).intValue();
				BigDecimal amount = (BigDecimal) row[1];
				monthlyTable.addCell(createCell("Tháng " + month, vietnameseFont, false));
				monthlyTable.addCell(createCell(formatCurrency(amount), vietnameseFont, false));
			}
			document.add(monthlyTable);
			
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// --- CÁC PHƯƠNG THỨC TIỆN ÍCH (COPY TỪ ORDERCONTROLLER) ---
	private String formatCurrency(BigDecimal amount) {
		return String.format("%,.0f ₫", amount);
	}

	private Cell createCell(String content, PdfFont font, boolean isHeader) {
		Paragraph p = new Paragraph(content).setFont(font).setFontSize(9);
		Cell cell = new Cell().add(p).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
		if (isHeader) {
			cell.setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY);
		}
		return cell;
	}

	private Cell createHeaderCell(String content, PdfFont font) {
		return createCell(content, font, true);
	}
}
