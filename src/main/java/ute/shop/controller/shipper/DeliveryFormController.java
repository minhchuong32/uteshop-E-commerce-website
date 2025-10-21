package ute.shop.controller.shipper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;

import ute.shop.entity.Delivery;
import ute.shop.service.IDeliveryService;
import ute.shop.service.impl.DeliveryServiceImpl;

import java.io.*;
import java.time.LocalDate;


@WebServlet("/shipper/delivery/form")
public class DeliveryFormController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IDeliveryService deliveryService = new DeliveryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendRedirect(req.getContextPath() + "/shipper/orders");
            return;
        }

        int deliveryId = Integer.parseInt(idStr);
        Delivery d = deliveryService.getById(deliveryId);

        if (d == null) {
            resp.sendRedirect(req.getContextPath() + "/shipper/orders");
            return;
        }

        // Hiển thị form JSP cho shipper nhập thông tin phiếu
        req.setAttribute("delivery", d);
        req.setAttribute("view", "/views/shipper/delivery_form.jsp");
        
        req.getRequestDispatcher("/WEB-INF/decorators/shipper.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int deliveryId = Integer.parseInt(req.getParameter("deliveryId"));
        String receiverName = req.getParameter("receiverName");
        String phone = req.getParameter("phone");
        String note = req.getParameter("note");

        Delivery d = deliveryService.getById(deliveryId);
        if (d == null) {
            resp.sendRedirect(req.getContextPath() + "/shipper/orders");
            return;
        }

        // === Xuất PDF ===
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition",
                "attachment; filename=phieu_giao_hang_" + deliveryId + ".pdf");

        PdfWriter writer = new PdfWriter(resp.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(40, 40, 40, 40);
        
        String fontPath = "C:/Windows/Fonts/arial.ttf";
        PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, pdf);
        document.setFont(font);

        // === Tiêu đề ===
        Paragraph title = new Paragraph("PHIẾU GIAO HÀNG")
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        document.add(new Paragraph("Ngày lập: " + LocalDate.now())
                .setTextAlignment(TextAlignment.RIGHT));

        document.add(new LineSeparator(new SolidLine()));
        document.add(new Paragraph("\n"));

        // === Bảng thông tin ===
        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{30, 70}))
                .useAllAvailableWidth();

        infoTable.addCell(new Cell().add(new Paragraph("📦 Mã đơn:")));
        infoTable.addCell(new Cell().add(new Paragraph(String.valueOf(d.getDeliveryId()))));

        infoTable.addCell(new Cell().add(new Paragraph("👤 Khách hàng:")));
        infoTable.addCell(new Cell().add(new Paragraph(d.getOrder().getUser().getUsername())));

        infoTable.addCell(new Cell().add(new Paragraph("🏠 Địa chỉ:")));
        infoTable.addCell(new Cell().add(new Paragraph(d.getOrder().getAddress())));

        infoTable.addCell(new Cell().add(new Paragraph("💰 Tổng tiền:")));
        infoTable.addCell(new Cell().add(new Paragraph(String.valueOf(d.getOrder().getTotalAmount()))));

        infoTable.addCell(new Cell().add(new Paragraph("📱 Người nhận:")));
        infoTable.addCell(new Cell().add(new Paragraph(receiverName + " (" + phone + ")")));

        infoTable.addCell(new Cell().add(new Paragraph("📝 Ghi chú:")));
        infoTable.addCell(new Cell().add(new Paragraph(note != null && !note.isEmpty() ? note : "Không có")));

        document.add(infoTable);

        // === Ký tên ===
        document.add(new Paragraph("\n\nChữ ký người giao hàng: __________________________"));
        document.add(new Paragraph("Chữ ký người nhận: ______________________________"));

        document.close();
    }
}
