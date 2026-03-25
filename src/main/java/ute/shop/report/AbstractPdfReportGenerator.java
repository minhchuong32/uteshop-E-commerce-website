package ute.shop.report;

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
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TEMPLATE METHOD PATTERN:
 * - Định nghĩa "khung" (skeleton) tạo PDF cố định
 * - Các lớp con chỉ cần override phần nội dung riêng
 */
public abstract class AbstractPdfReportGenerator {

    // ===== TEMPLATE METHOD — không cho override =====
    // Đây là "công thức" cố định, các bước luôn theo thứ tự này
    public final void generate(HttpServletRequest req,
                               HttpServletResponse resp) throws IOException {

        // Bước 1: Gắn cookie báo hiệu download (giống hệt code cũ)
        Cookie downloadCookie = new Cookie("download_token", "completed");
        downloadCookie.setPath("/");
        resp.addCookie(downloadCookie);

        // Bước 2: Set response header — lấy tên file từ lớp con
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition",
                "attachment; filename=\"" + getFileName() + "\"");

        // Bước 3: Tạo document PDF
        try (PdfWriter writer = new PdfWriter(resp.getOutputStream());
             PdfDocument pdf   = new PdfDocument(writer);
             Document document = new Document(pdf, getPageSize())) {

            document.setMargins(20, 20, 20, 20);

            // Bước 4: Load font tiếng Việt (dùng chung, không lặp)
            PdfFont font = loadVietnameseFont(req);

            // Bước 5: Viết tiêu đề (dùng chung)
            document.add(new Paragraph(getReportTitle())
                    .setFont(font).setFontSize(18).setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph(
                    "Ngày xuất: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()))
                    .setFont(font).setFontSize(9)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(15));

            // Bước 6: Viết NỘI DUNG — lớp con tự quyết định
            writeContent(req, document, font);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + getErrorRedirectUrl());
        }
    }

    // ===== ABSTRACT METHODS — bắt buộc lớp con implement =====

    /** Tên file PDF tải về, ví dụ: "bao-cao-don-hang.pdf" */
    protected abstract String getFileName();

    /** Tiêu đề in trên PDF, ví dụ: "BÁO CÁO ĐƠN HÀNG" */
    protected abstract String getReportTitle();

    /** Viết toàn bộ bảng/biểu đồ đặc thù của từng loại báo cáo */
    protected abstract void writeContent(HttpServletRequest req,
                                         Document document,
                                         PdfFont font) throws Exception;

    // ===== HOOK METHODS — lớp con có thể override nếu muốn =====

    /** Mặc định A4 ngang, lớp con override nếu cần A4 đứng */
    protected PageSize getPageSize() {
        return PageSize.A4.rotate();
    }

    protected String getErrorRedirectUrl() {
        return "/admin/orders?error=errorExportPdf";
    }

    // ===== PHƯƠNG THỨC DÙNG CHUNG — không lặp nữa =====

    private PdfFont loadVietnameseFont(HttpServletRequest req) throws Exception {
        InputStream fontStream = req.getServletContext()
                .getResourceAsStream("/fonts/Roboto-Regular.ttf");
        if (fontStream == null)
            throw new IOException("Không tìm thấy font /fonts/Roboto-Regular.ttf");
        byte[] fontBytes = fontStream.readAllBytes();
        return PdfFontFactory.createFont(fontBytes, "Identity-H",
                EmbeddingStrategy.PREFER_EMBEDDED, true);
    }

    /** Tạo cell tiêu đề bảng — dùng chung cho tất cả báo cáo */
    protected Cell createHeaderCell(String content, PdfFont font) {
        return new Cell()
                .add(new Paragraph(content).setFont(font).setFontSize(9))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBold()
                .setBackgroundColor(ColorConstants.LIGHT_GRAY);
    }

    /** Tạo cell dữ liệu bảng — dùng chung */
    protected Cell createDataCell(String content, PdfFont font) {
        return new Cell()
                .add(new Paragraph(content).setFont(font).setFontSize(9))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
    }
}