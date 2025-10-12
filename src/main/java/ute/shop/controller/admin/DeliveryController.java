package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.Delivery;
import ute.shop.entity.Order;
import ute.shop.service.IDeliveryService;
import ute.shop.service.IOrderService;
import ute.shop.service.impl.DeliveryServiceImpl;
import ute.shop.service.impl.OrderServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
        "/admin/deliveries",
        "/admin/deliveries/note",
        "/admin/deliveries/delete",
        "/admin/deliveries/update"
})
public class DeliveryController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IDeliveryService deliveryService = new DeliveryServiceImpl();
    private final IOrderService orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        // === Trang danh sách ===
        if (uri.endsWith("/deliveries")) {
            List<Delivery> deliveries = deliveryService.findAll();
            List<Object[]> stats = deliveryService.getPerformanceStats();

            req.setAttribute("deliveries", deliveries);
            req.setAttribute("stats", stats);
            req.setAttribute("view", "/views/admin/deliveries.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
        }

        // === Xóa phiếu giao hàng ===
        else if (uri.endsWith("/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Delivery d = deliveryService.getById(id);
            if (d != null) {
                int orderId = d.getOrder().getOrderId();
                deliveryService.delete(id);
                orderService.delete(orderId);
                System.out.println("Đã xóa Delivery ID = " + id + " và Order ID = " + orderId);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/deliveries");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        // === Cập nhật ghi chú ===
        if (uri.endsWith("/note")) {
            int id = Integer.parseInt(req.getParameter("deliveryId"));
            String noteText = req.getParameter("noteText");

            Delivery d = deliveryService.getById(id);
            if (d != null && noteText != null) {
                d.setNoteText(noteText.trim());
                deliveryService.save(d);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/deliveries");
        }

        // === Chỉnh sửa thông tin giao hàng ===
        else if (uri.endsWith("/update")) {
            try {
                int deliveryId = Integer.parseInt(req.getParameter("deliveryId"));
                String status = req.getParameter("status");
                String address = req.getParameter("address");
                String payment = req.getParameter("paymentMethod");

                Delivery d = deliveryService.getById(deliveryId);
                if (d != null) {
                    // Cập nhật bảng delivery
                    d.setStatus(status);
                    deliveryService.save(d);

                    // Cập nhật bảng order
                    Order o = d.getOrder();
                    if (o != null) {
                        o.setAddress(address);
                        o.setPaymentMethod(payment);
                        orderService.update(o);
                    }
                    req.getSession().setAttribute("message", "Cập nhật thông tin giao hàng thành công!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                req.getSession().setAttribute("error", "Lỗi khi cập nhật thông tin!");
            }

            resp.sendRedirect(req.getContextPath() + "/admin/deliveries");
        }
    }
}
