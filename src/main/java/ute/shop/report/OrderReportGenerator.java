package ute.shop.report;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.servlet.http.HttpServletRequest;
import ute.shop.entity.Delivery;
import ute.shop.entity.Order;
import ute.shop.service.IOrderService;
import ute.shop.service.IDeliveryService;
import ute.shop.service.impl.OrderServiceImpl;
import ute.shop.service.impl.DeliveryServiceImpl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Lớp con — chỉ cần viết phần nội dung đơn hàng
 * Không cần quan tâm đến font, header, cookie
 */
public class OrderReportGenerator extends AbstractPdfReportGenerator {

    private final IOrderService    orderService    = new OrderServiceImpl();
    private final IDeliveryService deliveryService = new DeliveryServiceImpl();

    @Override
    protected String getFileName() { return "bao-cao-don-hang.pdf"; }

    @Override
    protected String getReportTitle() { return "BÁO CÁO TỔNG THỂ QUẢN LÝ ĐƠN HÀNG"; }

    @Override
    protected PageSize getPageSize() { return PageSize.A4.rotate(); } // A4 ngang

    @Override
    protected String getErrorRedirectUrl() { return "/admin/orders?error=errorExportPdf"; }

    @Override
    protected void writeContent(HttpServletRequest req,
                                Document document,
                                PdfFont font) throws Exception {

        List<Order> orders = orderService.findAllForAdmin();
        DecimalFormat df   = new DecimalFormat("#.##");

        // --- Bảng tổng quan ---
        long total     = orders.size();
        long delivered = orders.stream().filter(o -> "Đã giao".equals(o.getStatus())).count();
        long canceled  = orders.stream().filter(o -> "Đã hủy".equals(o.getStatus())).count();
        double rate    = total > 0 ? (double) delivered / total * 100 : 0;

        Table overview = new Table(UnitValue.createPercentArray(new float[]{1,1,1,1}))
                .useAllAvailableWidth();
        overview.addCell(createHeaderCell("Tổng đơn", font));
        overview.addCell(createHeaderCell("Giao thành công", font));
        overview.addCell(createHeaderCell("Đã hủy", font));
        overview.addCell(createHeaderCell("Tỷ lệ thành công", font));
        overview.addCell(createDataCell(String.valueOf(total), font));
        overview.addCell(createDataCell(String.valueOf(delivered), font));
        overview.addCell(createDataCell(String.valueOf(canceled), font));
        overview.addCell(createDataCell(df.format(rate) + "%", font));
        document.add(overview);

        // --- Bảng chi tiết đơn hàng ---
        Table orderTable = new Table(
                UnitValue.createPercentArray(new float[]{1,2,2,2,1.5f,1.5f,1.5f}))
                .useAllAvailableWidth();

        for (String header : new String[]{"ID","Khách hàng","Shipper",
                                          "Hãng vận chuyển","Tổng tiền",
                                          "Trạng thái","Ngày tạo"}) {
            orderTable.addHeaderCell(createHeaderCell(header, font));
        }

        for (Order o : orders) {
            String shipperName = "Chưa gán", carrierName = "Chưa gán";
            if (!o.getDeliveries().isEmpty()) {
                Delivery d = o.getDeliveries().get(0);
                if (d.getShipper() != null) shipperName = d.getShipper().getName();
                if (d.getCarrier() != null) carrierName = d.getCarrier().getCarrierName();
            }
            orderTable.addCell(createDataCell("#" + o.getOrderId(), font));
            orderTable.addCell(createDataCell(o.getUser().getName(), font));
            orderTable.addCell(createDataCell(shipperName, font));
            orderTable.addCell(createDataCell(carrierName, font));
            orderTable.addCell(createDataCell(
                    new DecimalFormat("###,###₫").format(o.getTotalAmount()), font));
            orderTable.addCell(createDataCell(o.getStatus(), font));
            orderTable.addCell(createDataCell(
                    new SimpleDateFormat("dd/MM/yyyy").format(o.getCreatedAt()), font));
        }
        document.add(orderTable);
    }
}