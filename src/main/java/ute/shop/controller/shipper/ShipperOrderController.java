package ute.shop.controller.shipper;

import ute.shop.entity.Delivery;
import ute.shop.entity.User;
import ute.shop.service.IDeliveryService;
import ute.shop.service.impl.DeliveryServiceImpl;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {
        "/shipper/orders",
        "/shipper/orders/delete",
        "/shipper/assign"
})
public class ShipperOrderController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IDeliveryService deliveryService = new DeliveryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        // ✅ Trường hợp: Xóa đơn hàng
        if (uri.endsWith("/delete")) {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                Integer id = Integer.parseInt(idStr);
                deliveryService.delete(id);
            }
            resp.sendRedirect(req.getContextPath() + "/shipper/orders");
            return;
        }

        // ✅ Trường hợp: Hiển thị danh sách đơn hàng
        HttpSession session = req.getSession(false);
        User shipperLogin = (session != null) ? (User) session.getAttribute("account") : null;

        if (shipperLogin == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Integer shipperId = shipperLogin.getUserId();

        // 1️⃣ Lấy các đơn đang tìm shipper hoặc đã gán nhưng chưa giao
        List<Delivery> unassigned = deliveryService.findUnassignedDeliveries();

        // 2️⃣ Các đơn thuộc shipper hiện tại
        List<Delivery> shipperDeliveries = deliveryService.getByShipper(shipperId);

        // 3️⃣ Phân loại danh sách
        List<Delivery> delivering = shipperDeliveries.stream()
                .filter(d -> "Đang giao".equalsIgnoreCase(d.getStatus()) ||
                             "Đã gán".equalsIgnoreCase(d.getStatus()))
                .collect(Collectors.toList());

        List<Delivery> finished = shipperDeliveries.stream()
                .filter(d -> "Đã giao".equalsIgnoreCase(d.getStatus()) ||
                             "Hủy".equalsIgnoreCase(d.getStatus()))
                .collect(Collectors.toList());

        req.setAttribute("unassigned", unassigned);
        req.setAttribute("delivering", delivering);
        req.setAttribute("finished", finished);
        req.setAttribute("shipperId", shipperId);

        req.setAttribute("page", "orders");
        req.setAttribute("view", "/views/shipper/orders.jsp");
        req.getRequestDispatcher("/WEB-INF/decorators/shipper.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        // ✅ Nhận đơn (assign)
        if (uri.endsWith("/assign")) {
            HttpSession session = req.getSession(false);
            User shipperLogin = (session != null) ? (User) session.getAttribute("account") : null;

            if (shipperLogin == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            String deliveryIdStr = req.getParameter("deliveryId");
            if (deliveryIdStr != null) {
                Integer deliveryId = Integer.parseInt(deliveryIdStr);
                Integer shipperId = shipperLogin.getUserId();

                deliveryService.assignToShipper(deliveryId, shipperId);
            }

            resp.sendRedirect(req.getContextPath() + "/shipper/orders");
            return;
        }

        // ✅ Cập nhật trạng thái đơn hàng
        if (uri.endsWith("/orders")) {
            String deliveryIdStr = req.getParameter("deliveryId");
            String status = req.getParameter("status");

            if (deliveryIdStr != null && status != null) {
                Integer deliveryId = Integer.parseInt(deliveryIdStr);
                deliveryService.updateStatus(deliveryId, status);
            }

            resp.sendRedirect(req.getContextPath() + "/shipper/orders");
        }
    }
}
