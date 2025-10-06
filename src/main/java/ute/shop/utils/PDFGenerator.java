package ute.shop.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import ute.shop.entity.Delivery;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class PDFGenerator {

	public static String generateDeliveryNote(Delivery d, String uploadDir) throws Exception {
		// Validate input
		if (d == null) {
			throw new IllegalArgumentException("Delivery không được null");
		}
		if (d.getShipper() == null) {
			throw new IllegalArgumentException("Thông tin shipper bị thiếu");
		}
		if (d.getOrder() == null) {
			throw new IllegalArgumentException("Thông tin đơn hàng bị thiếu");
		}

		// Đảm bảo thư mục tồn tại
		File folder = new File(uploadDir);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		String fileName = "delivery_note_" + d.getDeliveryId() + "_" + System.currentTimeMillis() + ".pdf";
		String filePath = uploadDir + File.separator + fileName;

		Document document = new Document(PageSize.A4, 50, 50, 60, 60);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
		document.open();

		// ================== MÀU CHỦ ĐẠO & FONT ==================
		BaseColor primary = new BaseColor(0, 85, 141); // #00558D
		Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, primary);
		Font sectionTitle = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, primary);
		Font textFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
		Font grayFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);

		// ================== HEADER (LOGO + THÔNG TIN) ==================
		PdfPTable headerTable = new PdfPTable(2);
		headerTable.setWidthPercentage(100);
		headerTable.setWidths(new float[] { 70, 30 });

		PdfPCell left = new PdfPCell();
		left.setBorder(Rectangle.NO_BORDER);
		left.addElement(new Paragraph("UTESHOP.VN", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, primary)));
		left.addElement(new Paragraph("Phiếu giao hàng chính thức", textFont));
		left.addElement(new Paragraph("Ngày tạo: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(d.getCreatedAt()),
				grayFont));

		PdfPCell right = new PdfPCell();
		right.setBorder(Rectangle.NO_BORDER);
		right.setHorizontalAlignment(Element.ALIGN_RIGHT);

		// Xử lý logo an toàn hơn
		try {
			// Lấy đường dẫn gốc của project
			String projectRoot = new File(uploadDir).getParentFile().getParentFile().getAbsolutePath();
			String logoPath = projectRoot + File.separator + "assets" + File.separator + "images" + File.separator + "logo.png";
			
			File logoFile = new File(logoPath);
			if (logoFile.exists()) {
				Image logo = Image.getInstance(logoPath);
				logo.scaleAbsolute(70, 70);
				right.addElement(logo);
			} else {
				// Nếu không tìm thấy logo, hiển thị text
				Paragraph logoText = new Paragraph("UTESHOP", 
					new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, primary));
				logoText.setAlignment(Element.ALIGN_RIGHT);
				right.addElement(logoText);
			}
		} catch (Exception e) {
			System.err.println("⚠️  Không thể load logo: " + e.getMessage());
			Paragraph logoText = new Paragraph("UTESHOP", 
				new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, primary));
			logoText.setAlignment(Element.ALIGN_RIGHT);
			right.addElement(logoText);
		}

		headerTable.addCell(left);
		headerTable.addCell(right);
		document.add(headerTable);

		document.add(new Paragraph("\n"));

		// ================== TIÊU ĐỀ ==================
		Paragraph title = new Paragraph("PHIẾU GIAO HÀNG", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingAfter(10);
		document.add(title);

		// ================== THÔNG TIN CƠ BẢN ==================
		PdfPTable infoTable = new PdfPTable(2);
		infoTable.setWidthPercentage(100);
		infoTable.setSpacingBefore(10);
		infoTable.setSpacingAfter(15);
		infoTable.setWidths(new float[] { 50, 50 });

		infoTable.addCell(createInfoCell("Mã phiếu giao hàng:", textFont));
		infoTable.addCell(createValueCell("#" + d.getDeliveryId(), textFont));

		infoTable.addCell(createInfoCell("Trạng thái giao hàng:", textFont));
		infoTable.addCell(createValueCell(d.getStatus() != null ? d.getStatus() : "N/A", textFont));

		infoTable.addCell(createInfoCell("Ngày lập phiếu:", textFont));
		infoTable.addCell(createValueCell(
			d.getCreatedAt() != null 
				? new SimpleDateFormat("dd/MM/yyyy HH:mm").format(d.getCreatedAt()) 
				: "N/A", 
			textFont));

		document.add(infoTable);

		// ================== THÔNG TIN SHIPPER ==================
		document.add(new Paragraph("THÔNG TIN NGƯỜI GIAO HÀNG", sectionTitle));
		document.add(new Paragraph("Tên: " + safeString(d.getShipper().getName()), textFont));
		document.add(new Paragraph("SĐT: " + safeString(d.getShipper().getPhone()), textFont));
		document.add(new Paragraph("Email: " + safeString(d.getShipper().getEmail()), textFont));
		document.add(new Paragraph("\n"));

		// ================== THÔNG TIN ĐƠN HÀNG ==================
		document.add(new Paragraph("THÔNG TIN ĐƠN HÀNG", sectionTitle));
		document.add(new Paragraph("Mã đơn hàng: #" + d.getOrder().getOrderId(), textFont));
		document.add(new Paragraph("Trạng thái: " + safeString(d.getOrder().getStatus()), textFont));
		document.add(new Paragraph("Phương thức thanh toán: " + safeString(d.getOrder().getPaymentMethod()), textFont));
		document.add(new Paragraph("Địa chỉ giao hàng: " + safeString(d.getOrder().getAddress()), textFont));
		
		BigDecimal amount = d.getOrder().getTotalAmount();
		String amountStr = amount != null ? String.format("%,.0f ₫", amount.doubleValue()) : "0 ₫";
		document.add(new Paragraph("Tổng tiền: " + amountStr, textFont));
		document.add(new Paragraph("\n"));

		// ================== GHI CHÚ ==================
		if (d.getNoteText() != null && !d.getNoteText().trim().isEmpty()) {
			Paragraph noteTitle = new Paragraph("GHI CHÚ GIAO HÀNG", sectionTitle);
			noteTitle.setSpacingBefore(5);
			noteTitle.setSpacingAfter(5);
			document.add(noteTitle);

			PdfPTable noteTable = new PdfPTable(1);
			noteTable.setWidthPercentage(100);
			PdfPCell noteCell = new PdfPCell(new Paragraph(d.getNoteText(), textFont));
			noteCell.setPadding(8);
			noteCell.setBorderColor(primary);
			noteTable.addCell(noteCell);
			document.add(noteTable);
		}

		document.add(new Paragraph("\n\n"));

		// ================== MÃ QR TRACKING ==================
		String qrPath = null;
		try {
			String trackingUrl = "https://uteshop.vn/track/delivery?id=" + d.getDeliveryId();
			qrPath = uploadDir + File.separator + "qr_temp_" + d.getDeliveryId() + "_" + System.currentTimeMillis() + ".png";

			// Tạo QR code bằng ZXing
			com.google.zxing.qrcode.QRCodeWriter qrCodeWriter = new com.google.zxing.qrcode.QRCodeWriter();
			com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(trackingUrl,
					com.google.zxing.BarcodeFormat.QR_CODE, 200, 200);

			java.awt.image.BufferedImage qrImage = new java.awt.image.BufferedImage(200, 200,
					java.awt.image.BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < 200; x++) {
				for (int y = 0; y < 200; y++) {
					qrImage.setRGB(x, y,
							bitMatrix.get(x, y) ? java.awt.Color.BLACK.getRGB() : java.awt.Color.WHITE.getRGB());
				}
			}
			javax.imageio.ImageIO.write(qrImage, "png", new File(qrPath));

			// Chèn QR vào PDF
			Image qrPdfImage = Image.getInstance(qrPath);
			qrPdfImage.scaleAbsolute(90, 90);
			qrPdfImage.setAlignment(Element.ALIGN_RIGHT);
			document.add(qrPdfImage);
		} catch (Exception e) {
			System.err.println("⚠️  Không thể tạo QR code: " + e.getMessage());
			document.add(new Paragraph("QR Code: https://uteshop.vn/track/delivery?id=" + d.getDeliveryId(), grayFont));
		}

		// ================== Ô KÝ NHẬN ==================
		document.add(new Paragraph("\n"));
		PdfPTable signTable = new PdfPTable(2);
		signTable.setWidthPercentage(100);
		signTable.setWidths(new float[] { 50, 50 });

		PdfPCell shipperSign = new PdfPCell();
		shipperSign.setFixedHeight(100);
		shipperSign.setVerticalAlignment(Element.ALIGN_BOTTOM);
		shipperSign.setHorizontalAlignment(Element.ALIGN_CENTER);
		shipperSign.addElement(new Paragraph("Người giao hàng", textFont));
		shipperSign.setBorderColor(primary);

		PdfPCell receiverSign = new PdfPCell();
		receiverSign.setFixedHeight(100);
		receiverSign.setVerticalAlignment(Element.ALIGN_BOTTOM);
		receiverSign.setHorizontalAlignment(Element.ALIGN_CENTER);
		receiverSign.addElement(new Paragraph("Người nhận hàng", textFont));
		receiverSign.setBorderColor(primary);

		signTable.addCell(shipperSign);
		signTable.addCell(receiverSign);
		document.add(signTable);

		// ================== FOOTER ==================
		document.add(new Paragraph("\n"));
		Paragraph footer = new Paragraph("Cảm ơn quý khách đã tin tưởng và sử dụng UteShop.vn!", grayFont);
		footer.setAlignment(Element.ALIGN_CENTER);
		document.add(footer);

		document.close();
		writer.close();

		// ================== XÓA FILE QR TẠM ==================
		if (qrPath != null) {
			try {
				File qrFile = new File(qrPath);
				if (qrFile.exists()) {
					qrFile.delete();
					System.out.println("🗑️  Đã xóa QR tạm: " + qrPath);
				}
			} catch (Exception e) {
				System.err.println("⚠️  Không thể xóa QR tạm: " + e.getMessage());
			}
		}

		System.out.println("✅ PDF đã tạo thành công: " + filePath);
		return filePath;
	}

	// ====== HÀM PHỤ DÙNG CHUNG ======
	private static PdfPCell createInfoCell(String text, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPaddingBottom(8);
		return cell;
	}

	private static PdfPCell createValueCell(String text, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(text, new Font(font.getFamily(), font.getSize(), Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPaddingBottom(8);
		return cell;
	}

	private static String safeString(String str) {
		return str != null && !str.trim().isEmpty() ? str : "N/A";
	}
}