package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.Carrier;
import ute.shop.entity.Delivery;
import ute.shop.entity.Order;
import ute.shop.entity.User;
import ute.shop.service.ICarrierService;
import ute.shop.service.IDeliveryService;
import ute.shop.service.IOrderService;
import ute.shop.service.IUserService;
import ute.shop.service.impl.CarrierServiceImpl;
import ute.shop.service.impl.DeliveryServiceImpl;
import ute.shop.service.impl.OrderServiceImpl;
import ute.shop.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/admin/orders", "/admin/orders/form", "/admin/orders/delete", "/admin/orders/edit" })
public class OrderController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final IDeliveryService deliveryService = new DeliveryServiceImpl();
	private final IOrderService orderService = new OrderServiceImpl();
	private final ICarrierService carrierService = new CarrierServiceImpl();
	private final IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();

		List<Order> orders = orderService.findAllForAdmin();
		List<User> shippers = userService.getUsersByRole("shipper");
		List<Carrier> carriers = carrierService.findAll();
		List<Object[]> stats = deliveryService.getPerformanceStats();

		req.setAttribute("orders", orders);
		req.setAttribute("shippers", shippers);
		req.setAttribute("carriers", carriers);
		// === Trang danh sách đơn hàng ===
		if (uri.endsWith("/orders")) {
			req.setAttribute("stats", stats);
			req.setAttribute("page", "orders");
			req.setAttribute("view", "/views/admin/orders/list.jsp");
			req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
		}

		else if (uri.endsWith("/form")) {
			String orderIdParam = req.getParameter("orderId");
			String deliveryIdParam = req.getParameter("deliveryId");

			if (orderIdParam == null || deliveryIdParam == null) {
				resp.sendRedirect(req.getContextPath() + "/admin/orders");
				return;
			}

			// Lấy order
			int orderId = Integer.parseInt(orderIdParam);
			Order order = orderService.getById(orderId);

			// Lấy delivery
			int deliveryId = Integer.parseInt(deliveryIdParam);
			Delivery delivery = deliveryService.getById(deliveryId);

			req.setAttribute("order", order);
			req.setAttribute("delivery", delivery);

			req.setAttribute("page", "orders");
			req.setAttribute("view", "/views/admin/orders/form.jsp");
			req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
		}

		// === Xóa ===
		else if (uri.endsWith("/delete")) {
			int deliveryId = Integer.parseInt(req.getParameter("deliveryId"));
			int orderId = Integer.parseInt(req.getParameter("orderId"));

			deliveryService.delete(deliveryId);
			orderService.delete(orderId);
			req.getSession().setAttribute("message",
					"Đã xóa Delivery ID = " + deliveryId + " và Order ID = " + orderId);

			resp.sendRedirect(req.getContextPath() + "/admin/orders");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		try {
			// Lấy các parameter từ form
			String orderIdParam = req.getParameter("orderId");
			String deliveryIdParam = req.getParameter("deliveryId");
			String shipperIdParam = req.getParameter("shipperId");
			String carrierIdParam = req.getParameter("carrierId");
			String orderStatusParam = req.getParameter("status"); // status của Order
			String deliveryStatusParam = req.getParameter("deliveryStatus"); // status của Delivery
			String address = req.getParameter("address");
			String payment = req.getParameter("paymentMethod");
			String noteText = req.getParameter("noteText");

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
			order.setAddress(address);
			order.setPaymentMethod(payment);
			if (orderStatusParam != null && !orderStatusParam.isEmpty()) {
				order.setStatus(orderStatusParam);
			}
			orderService.update(order);

			// Cập nhật Delivery nếu có
			if (deliveryIdParam != null && !deliveryIdParam.isEmpty()) {
				int deliveryId = Integer.parseInt(deliveryIdParam);
				Delivery delivery = deliveryService.getById(deliveryId);
				if (delivery != null) {
					// set shipper (bắt buộc)
					int shipperId = Integer.parseInt(shipperIdParam);
					User shipper = userService.getUserById(shipperId)
							.orElseThrow(() -> new RuntimeException("Shipper không tồn tại"));
					delivery.setShipper(shipper);

					// set carrier (optional)
					if (carrierIdParam != null && !carrierIdParam.isEmpty()) {
						int carrierId = Integer.parseInt(carrierIdParam);
						Carrier carrier = carrierService.findById(carrierId);
						delivery.setCarrier(carrier);
					} else {
						delivery.setCarrier(null);
					}

					// set Order để JPA merge relation đúng
					delivery.setOrder(order);

					// set Delivery status và note
					delivery.setStatus(deliveryStatusParam);
					delivery.setNoteText(noteText);

					deliveryService.save(delivery);

				}
			}

			req.getSession().setAttribute("message", "Cập nhật thành công!");
			resp.sendRedirect(req.getContextPath() + "/admin/orders/form?orderId=" + orderId);

		} catch (NumberFormatException e) {
			e.printStackTrace();
			req.getSession().setAttribute("error", "ID không hợp lệ!");
			resp.sendRedirect(req.getContextPath() + "/admin/orders");
		} catch (Exception e) {
			e.printStackTrace();
			req.getSession().setAttribute("error", "Lỗi khi cập nhật!");
			resp.sendRedirect(req.getContextPath() + "/admin/orders");
		}
	}

}
