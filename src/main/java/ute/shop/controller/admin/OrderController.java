package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.*;
import ute.shop.service.impl.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/admin/orders", "/admin/orders/form", "/admin/orders/delete", "/admin/orders/edit" })
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
                    List<ShippingAddress> addresses = shippingAddressService.getAddressesByUser(order.getUser().getUserId());
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
                req.getSession().setAttribute("message", "Đã xóa Delivery ID = " + deliveryId + " và Order ID = " + orderId);
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
            String shippingAddressIdParam = req.getParameter("shippingAddressId"); // Mới: Lấy ID địa chỉ

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
                order.setShippingAddress(shippingAddress); // Mới: Cập nhật đối tượng địa chỉ
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
            resp.sendRedirect(req.getContextPath() + "/admin/orders/form?orderId=" + orderId + "&deliveryId=" + deliveryIdParam);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.getSession().setAttribute("error", "ID không hợp lệ!");
            resp.sendRedirect(req.getContextPath() + "/admin/orders");
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("error", "Lỗi khi cập nhật: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/admin/orders/form?orderId=" + orderIdParam + "&deliveryId=" + deliveryIdParam);
        }
    }
}