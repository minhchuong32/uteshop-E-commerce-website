package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.Delivery;
import ute.shop.service.impl.DeliveryServiceImpl;
import ute.shop.utils.PDFGenerator;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { 
    "/admin/deliveries", 
    "/admin/deliveries/note", 
    "/admin/deliveries/delete"
})
@MultipartConfig
public class DeliveryController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final DeliveryServiceImpl deliveryService = new DeliveryServiceImpl();
    private static final String RELATIVE_DIR = "/uploads/delivery_notes"; // nằm trong webapp

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        // === Trang danh sách + biểu đồ ===
        if (uri.endsWith("/deliveries")) {
            List<Delivery> deliveries = deliveryService.findAll();
            List<Object[]> stats = deliveryService.getPerformanceStats();

            req.setAttribute("deliveries", deliveries);
            req.setAttribute("stats", stats);
            req.setAttribute("page", "deliveries");
            req.setAttribute("view", "/views/admin/deliveries.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
        }

        // === Xóa phiếu và file PDF ===
        else if (uri.endsWith("/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Delivery d = deliveryService.getById(id);

            if (d != null && d.getDeliveryNote() != null) {
                String filePath = getServletContext().getRealPath(d.getDeliveryNote());
                File f = new File(filePath);

                System.out.println("🗑️ Đang kiểm tra file trước khi xóa: " + filePath);
                if (f.exists()) {
                    boolean deleted = f.delete();
                    System.out.println("✅ Xóa file: " + (deleted ? "Thành công" : "Thất bại"));
                } else {
                    System.out.println("⚠️ Không tìm thấy file để xóa!");
                }
            }

            deliveryService.delete(id);
            System.out.println("🗂️ Đã xóa record delivery có ID = " + id);
            resp.sendRedirect(req.getContextPath() + "/admin/deliveries");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getRequestURI().endsWith("/note")) {
            try {
                int id = Integer.parseInt(req.getParameter("deliveryId"));
                Delivery d = deliveryService.getById(id);

                if (d == null) {
                    req.getSession().setAttribute("error", "Không tìm thấy phiếu giao hàng!");
                    resp.sendRedirect(req.getContextPath() + "/admin/deliveries");
                    return;
                }

                // === Lấy và kiểm tra thư mục thật trong webapp ===
                String realPath = getServletContext().getRealPath(RELATIVE_DIR);
                File folder = new File(realPath);

                System.out.println("\n========== 🧾 KIỂM TRA THƯ MỤC LƯU FILE ==========");
                System.out.println("📁 Thư mục tuyệt đối: " + realPath);
                System.out.println("📂 Thư mục tồn tại: " + folder.exists());
                System.out.println("✏️ Quyền ghi: " + folder.canWrite());
                System.out.println("👀 Quyền đọc: " + folder.canRead());

                if (!folder.exists()) {
                    boolean created = folder.mkdirs();
                    System.out.println("🛠️ Tạo thư mục mới: " + (created ? "OK" : "FAILED"));
                }

                // === Tạo file PDF ===
                System.out.println("\n========== 🧾 TẠO FILE PDF ==========");
//                String pdfPath = PDFGenerator.generateDeliveryNote(d, realPath);
                String pdfPath = null;
                String fileName = new File(pdfPath).getName();
                String relativePath = RELATIVE_DIR + "/" + fileName;

                System.out.println("📄 File PDF tạo ra: " + pdfPath);
                File f = new File(pdfPath);
                System.out.println("📦 File tồn tại sau khi tạo: " + f.exists());
                System.out.println("📏 Kích thước (bytes): " + (f.exists() ? f.length() : "N/A"));

                // === Lưu đường dẫn vào DB ===
                d.setDeliveryNote(relativePath);
                deliveryService.save(d);

                // === Hiển thị đường dẫn web ===
                String webLink = req.getContextPath() + relativePath;
                System.out.println("\n========== 🌐 THÔNG TIN WEB LINK ==========");
                System.out.println("🔗 Đường dẫn tương đối lưu DB: " + relativePath);
                System.out.println("🌍 Đường dẫn truy cập web: " + webLink);
                System.out.println("============================================\n");

                // === Gửi thông báo ===
                req.getSession().setAttribute("message", "Tạo phiếu giao hàng thành công!");
                req.getSession().setAttribute("fileName", fileName);
                req.getSession().setAttribute("downloadLink", webLink);

            } catch (Exception e) {
                e.printStackTrace();
                req.getSession().setAttribute("error", "Lỗi khi tạo PDF: " + e.getMessage());
            }

            resp.sendRedirect(req.getContextPath() + "/admin/deliveries");
        }
    }
}
