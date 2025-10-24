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
import ute.shop.entity.*;
import ute.shop.service.*;
import ute.shop.service.impl.*;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = { "/admin/orders", "/admin/orders/form", "/admin/orders/delete", "/admin/orders/edit",
		"/admin/orders/report" })
public class OrderController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final IDeliveryService deliveryService = new DeliveryServiceImpl();
	private final IOrderService orderService = new OrderServiceImpl();
	private final ICarrierService carrierService = new CarrierServiceImpl();
	private final IUserService userService = new UserServiceImpl();
	// Thêm service cho ShippingAddress
	private final IShippingAddressService shippingAddressService = new ShippingAddressServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		// === XUẤT BÁO CÁO PDF ===
		if (uri.endsWith("/report")) {
			generateOrderReportPdf(req, resp);
			resp.sendRedirect(req.getContextPath() + "/admin/orders");
			return;
		}

		// Lấy dữ liệu chung
		List<User> shippers = userService.getUsersByRole("shipper");
		List<Carrier> carriers = carrierService.findAll();

		req.setAttribute("shippers", shippers);
		req.setAttribute("carriers", carriers);
		// === Trang danh sách đơn hàng ===
		if (uri.endsWith("/orders")) {
			List<Order> orders = orderService.findAllForAdmin();
			List<Object[]> stats = deliveryService.getPerformanceStats();
			req.setAttribute("orders", orders);
			req.setAttribute("stats", stats);
			req.setAttribute("page", "orders");
			req.setAttribute("view", "/views/admin/orders/list.jsp");
			req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
		}
		// === Trang form chỉnh sửa ===
		else if (uri.contains("/form")) {
			String orderIdParam = req.getParameter("orderId");
			String deliveryIdParam = req.getParameter("deliveryId");

			if (orderIdParam == null || deliveryIdParam == null) {
				resp.sendRedirect(req.getContextPath() + "/admin/orders");
				return;
			}

			try {
				// Lấy order
				int orderId = Integer.parseInt(orderIdParam);
				Order order = orderService.getById(orderId);

				// Lấy delivery
				int deliveryId = Integer.parseInt(deliveryIdParam);
				Delivery delivery = deliveryService.getById(deliveryId);

				// Lấy danh sách địa chỉ của khách hàng để cho admin chọn
				if (order != null && order.getUser() != null) {
					List<ShippingAddress> addresses = shippingAddressService
							.getAddressesByUser(order.getUser().getUserId());
					req.setAttribute("shippingAddresses", addresses);
				}

				req.setAttribute("order", order);
				req.setAttribute("delivery", delivery);
				req.setAttribute("page", "orders");
				req.setAttribute("view", "/views/admin/orders/form.jsp");
				req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
			} catch (NumberFormatException e) {
				resp.sendRedirect(req.getContextPath() + "/admin/orders");
			}
		}
		// === Xóa ===
		else if (uri.endsWith("/delete")) {
			try {
				int deliveryId = Integer.parseInt(req.getParameter("deliveryId"));
				int orderId = Integer.parseInt(req.getParameter("orderId"));
				deliveryService.delete(deliveryId);
				orderService.delete(orderId);
				req.getSession().setAttribute("message",
						"Đã xóa Delivery ID = " + deliveryId + " và Order ID = " + orderId);
			} catch (NumberFormatException e) {
				req.getSession().setAttribute("error", "ID không hợp lệ!");
			}
			resp.sendRedirect(req.getContextPath() + "/admin/orders");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String orderIdParam = req.getParameter("orderId");
		String deliveryIdParam = req.getParameter("deliveryId");

		try {
			// Lấy các parameter từ form
			String shipperIdParam = req.getParameter("shipperId");
			String carrierIdParam = req.getParameter("carrierId");
			String orderStatusParam = req.getParameter("status");
			String deliveryStatusParam = req.getParameter("deliveryStatus");
			String paymentMethodParam = req.getParameter("paymentMethod");
			String noteText = req.getParameter("noteText");
			String shippingAddressIdParam = req.getParameter("shippingAddressId");

			// Kiểm tra Order ID
			if (orderIdParam == null || orderIdParam.isEmpty()) {
				req.getSession().setAttribute("error", "Order ID không hợp lệ!");
				resp.sendRedirect(req.getContextPath() + "/admin/orders");
				return;
			}

			int orderId = Integer.parseInt(orderIdParam);
			Order order = orderService.getById(orderId);
			if (order == null) {
				req.getSession().setAttribute("error", "Đơn hàng không tồn tại!");
				resp.sendRedirect(req.getContextPath() + "/admin/orders");
				return;
			}

			// Cập nhật thông tin đơn hàng
			if (shippingAddressIdParam != null && !shippingAddressIdParam.isEmpty()) {
				int addressId = Integer.parseInt(shippingAddressIdParam);
				ShippingAddress shippingAddress = shippingAddressService.getById(addressId);
				order.setShippingAddress(shippingAddress);
			}

			order.setPaymentMethod(paymentMethodParam);
			if (orderStatusParam != null && !orderStatusParam.isEmpty()) {
				order.setStatus(orderStatusParam);
			}
			orderService.update(order);

			// Cập nhật Delivery nếu có
			if (deliveryIdParam != null && !deliveryIdParam.isEmpty()) {
				int deliveryId = Integer.parseInt(deliveryIdParam);
				Delivery delivery = deliveryService.getById(deliveryId);
				if (delivery != null) {
					// set shipper
					int shipperId = Integer.parseInt(shipperIdParam);
					User shipper = userService.getUserById(shipperId)
							.orElseThrow(() -> new RuntimeException("Shipper không tồn tại"));
					delivery.setShipper(shipper);

					// set carrier
					if (carrierIdParam != null && !carrierIdParam.isEmpty()) {
						int carrierId = Integer.parseInt(carrierIdParam);
						Carrier carrier = carrierService.findById(carrierId);
						delivery.setCarrier(carrier);
					} else {
						delivery.setCarrier(null);
					}

					delivery.setOrder(order);
					delivery.setStatus(deliveryStatusParam);
					delivery.setNoteText(noteText);
					deliveryService.save(delivery);
				}
			}

			req.getSession().setAttribute("message", "Cập nhật thành công!");
			// Sửa redirect để bao gồm cả deliveryId
			resp.sendRedirect(
					req.getContextPath() + "/admin/orders/form?orderId=" + orderId + "&deliveryId=" + deliveryIdParam);

		} catch (NumberFormatException e) {
			e.printStackTrace();
			req.getSession().setAttribute("error", "ID không hợp lệ!");
			resp.sendRedirect(req.getContextPath() + "/admin/orders");
		} catch (Exception e) {
			e.printStackTrace();
			req.getSession().setAttribute("error", "Lỗi khi cập nhật: " + e.getMessage());
			resp.sendRedirect(req.getContextPath() + "/admin/orders/form?orderId=" + orderIdParam + "&deliveryId="
					+ deliveryIdParam);
		}
	}

	private void generateOrderReportPdf(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Tạo một cookie để báo hiệu cho JavaScript biết quá trình tải đã bắt đầu
	    Cookie downloadCookie = new Cookie("download_token", "completed");
	    downloadCookie.setPath("/"); // Đảm bảo cookie có thể được đọc từ mọi nơi trên trang
	    resp.addCookie(downloadCookie);
		// Lấy dữ liệu cần thiết
		List<Order> orders = orderService.findAllForAdmin();
		List<Object[]> stats = deliveryService.getPerformanceStats();

		resp.setContentType("application/pdf");
		resp.setHeader("Content-Disposition", "attachment; filename=\"bao-cao-don-hang.pdf\"");

		try (PdfWriter writer = new PdfWriter(resp.getOutputStream());
				PdfDocument pdf = new PdfDocument(writer);
				Document document = new Document(pdf, PageSize.A4.rotate())) { // Xoay ngang giấy A4

			document.setMargins(20, 20, 20, 20);

			// Đường dẫn đúng tính từ gốc của thư mục resources
			InputStream fontStream = getServletContext().getResourceAsStream("/fonts/Roboto-Regular.ttf");

			// Sau đó kiểm tra để đảm bảo stream không bị null trước khi dùng
			if (fontStream == null) {
				throw new IOException("Không tìm thấy file font tại /fonts/Roboto-Regular.ttf");
			}
			byte[] fontBytes = fontStream.readAllBytes();
			PdfFont vietnameseFont = PdfFontFactory.createFont(fontBytes, "Identity-H",
					EmbeddingStrategy.PREFER_EMBEDDED, true);

			// Tiêu đề
			document.add(new Paragraph("BÁO CÁO TỔNG THỂ QUẢN LÝ ĐƠN HÀNG").setFont(vietnameseFont).setFontSize(18)
					.setBold().setTextAlignment(TextAlignment.CENTER));
			document.add(new Paragraph("Ngày xuất: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()))
					.setFont(vietnameseFont).setFontSize(9).setTextAlignment(TextAlignment.CENTER).setMarginBottom(15));

			// Thống kê tổng quan
			long totalOrders = orders.size();
			long successfulDeliveries = orders.stream().filter(o -> "Đã giao".equals(o.getStatus())).count();
			long canceledOrders = orders.stream().filter(o -> "Đã hủy".equals(o.getStatus())).count();
			double successRate = totalOrders > 0 ? (double) successfulDeliveries / totalOrders * 100 : 0;
			DecimalFormat df = new DecimalFormat("#.##");

			document.add(new Paragraph("Thống kê tổng quan").setFont(vietnameseFont).setFontSize(14).setBold());
			Table overviewTable = new Table(UnitValue.createPercentArray(new float[] { 1, 1, 1, 1 }))
					.useAllAvailableWidth();
			overviewTable.addCell(createCell("Tổng số đơn", vietnameseFont, true));
			overviewTable.addCell(createCell("Giao thành công", vietnameseFont, true));
			overviewTable.addCell(createCell("Đã hủy", vietnameseFont, true));
			overviewTable.addCell(createCell("Tỷ lệ thành công", vietnameseFont, true));
			overviewTable.addCell(createCell(String.valueOf(totalOrders), vietnameseFont, false));
			overviewTable.addCell(createCell(String.valueOf(successfulDeliveries), vietnameseFont, false));
			overviewTable.addCell(createCell(String.valueOf(canceledOrders), vietnameseFont, false));
			overviewTable.addCell(createCell(df.format(successRate) + "%", vietnameseFont, false));
			document.add(overviewTable.setMarginBottom(15));

			// Bảng hiệu suất Shipper
			document.add(
					new Paragraph("Hiệu suất giao hàng của Shipper").setFont(vietnameseFont).setFontSize(14).setBold());
			Table statsTable = new Table(UnitValue.createPercentArray(new float[] { 4, 3, 3 })).useAllAvailableWidth();
			statsTable.addHeaderCell(createHeaderCell("Tên Shipper", vietnameseFont));
			statsTable.addHeaderCell(createHeaderCell("Tổng số đơn giao", vietnameseFont));
			statsTable.addHeaderCell(createHeaderCell("Tỷ lệ thành công (%)", vietnameseFont));
			for (Object[] stat : stats) {
				statsTable.addCell(createCell(String.valueOf(stat[0]), vietnameseFont, false));
				statsTable.addCell(createCell(String.valueOf(stat[1]), vietnameseFont, false));
				statsTable.addCell(createCell(df.format(stat[2]), vietnameseFont, false));
			}
			document.add(statsTable.setMarginBottom(15));

			// Bảng chi tiết đơn hàng
			document.add(new Paragraph("Chi tiết các đơn hàng").setFont(vietnameseFont).setFontSize(14).setBold());

			// SỬA LỖI 1: Thêm một cột vào định nghĩa (từ 6 thành 7 cột)
			Table orderTable = new Table(UnitValue.createPercentArray(new float[] { 1, 2, 2, 2, 1.5f, 1.5f, 1.5f }))
					.useAllAvailableWidth();

			// SỬA LỖI 2: Thêm tiêu đề cho cột mới "Hãng vận chuyển"
			orderTable.addHeaderCell(createHeaderCell("ID", vietnameseFont));
			orderTable.addHeaderCell(createHeaderCell("Khách hàng", vietnameseFont));
			orderTable.addHeaderCell(createHeaderCell("Shipper", vietnameseFont));
			orderTable.addHeaderCell(createHeaderCell("Hãng vận chuyển", vietnameseFont)); // <-- TIÊU ĐỀ MỚI
			orderTable.addHeaderCell(createHeaderCell("Tổng tiền", vietnameseFont));
			orderTable.addHeaderCell(createHeaderCell("Trạng thái", vietnameseFont));
			orderTable.addHeaderCell(createHeaderCell("Ngày tạo", vietnameseFont));

			for (Order order : orders) {
				orderTable.addCell(createCell("#" + order.getOrderId(), vietnameseFont, false));
				orderTable.addCell(createCell(order.getUser().getName(), vietnameseFont, false));

				// Logic lấy shipper và carrier của bạn đã đúng
				String shipperName = "Chưa gán";
				String carrierName = "Chưa gán";

				if (!order.getDeliveries().isEmpty()) {
					Delivery firstDelivery = order.getDeliveries().get(0);
					if (firstDelivery != null) {
						if (firstDelivery.getShipper() != null) {
							shipperName = firstDelivery.getShipper().getName();
						}
						if (firstDelivery.getCarrier() != null) {
							carrierName = firstDelivery.getCarrier().getCarrierName();
						}
					}
				}

				// Thứ tự thêm dữ liệu bây giờ đã khớp với tiêu đề
				orderTable.addCell(createCell(shipperName, vietnameseFont, false));
				orderTable.addCell(createCell(carrierName, vietnameseFont, false)); // <-- Dữ liệu cho cột mới
				orderTable.addCell(createCell(new DecimalFormat("###,###₫").format(order.getTotalAmount()),
						vietnameseFont, false));
				orderTable.addCell(createCell(order.getStatus(), vietnameseFont, false));
				orderTable.addCell(createCell(new SimpleDateFormat("dd/MM/yyyy").format(order.getCreatedAt()),
						vietnameseFont, false));
			}

			document.add(orderTable);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Phương thức tiện ích để tạo Cell cho bảng
	private Cell createCell(String content, PdfFont font, boolean isHeader) {
		Paragraph p = new Paragraph(content).setFont(font).setFontSize(9);
		Cell cell = new Cell().add(p).setTextAlignment(TextAlignment.CENTER)
				.setVerticalAlignment(VerticalAlignment.MIDDLE);
		if (isHeader) {
			cell.setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY);
		}
		return cell;
	}

	// Phương thức tiện ích để tạo Header Cell
	private Cell createHeaderCell(String content, PdfFont font) {
		return createCell(content, font, true);
	}
}