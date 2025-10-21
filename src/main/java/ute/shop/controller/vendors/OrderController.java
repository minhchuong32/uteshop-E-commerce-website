package ute.shop.controller.vendors;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Delivery;
import ute.shop.entity.Notification;
import ute.shop.entity.Order;
import ute.shop.entity.Shop;
import ute.shop.entity.User;
import ute.shop.service.IDeliveryService;
import ute.shop.service.impl.DeliveryServiceImpl;
import ute.shop.service.impl.NotificationServiceImpl;
import ute.shop.service.impl.OrderServiceImpl;

@WebServlet(urlPatterns = { "/vendor/orders", "/vendor/orders/add", "/vendor/orders/detail", "/vendor/orders/delete",
		"/vendor/orders/action" // Dành cho confirm / cancel
})
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private OrderServiceImpl orderService = new OrderServiceImpl();
	private IDeliveryService deliveryService = new DeliveryServiceImpl();
	private NotificationServiceImpl notificationService = new NotificationServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI();


		// ✅ GET SHOP FROM REQUEST ATTRIBUTE (set by JwtSecurityFilter)
		Shop shop = (Shop) req.getAttribute("currentShop");

		// ✅ NO NEED to check if shop is null. The filter guarantees it exists
		// by the time the request reaches this controller.
		int shopId = shop.getShopId();

		if (uri.endsWith("/orders")) {
			// ... (The rest of this logic is correct and does not need to change)
			List<Order> newOrders = orderService.getOrdersByShopAndStatuses(shopId, List.of("Mới"));
			List<Order> confirmedOrders = orderService.getOrdersByShopAndStatuses(shopId,
					List.of("Đã xác nhận", "Đang giao"));
			List<Order> deliveredOrders = orderService.getOrdersByShopAndStatuses(shopId, List.of("Đã giao"));
			List<Order> canceledOrders = orderService.getOrdersByShopAndStatuses(shopId, List.of("Đã hủy"));

			Map<Integer, User> shippersMap = new HashMap<>();
			for (Order o : confirmedOrders) {
				User shipper = orderService.getOrderShipper(o);
				if (shipper != null)
					shippersMap.put(o.getOrderId(), shipper);
			}
			for (Order o : deliveredOrders) {
				User shipper = orderService.getOrderShipper(o);
				if (shipper != null)
					shippersMap.put(o.getOrderId(), shipper);
			}
			for (Order o : canceledOrders) {
				User shipper = orderService.getOrderShipper(o);
				if (shipper != null)
					shippersMap.put(o.getOrderId(), shipper);
			}
			req.setAttribute("shippersMap", shippersMap);

			req.setAttribute("newOrders", newOrders);
			req.setAttribute("confirmedOrders", confirmedOrders);
			req.setAttribute("deliveredOrders", deliveredOrders);
			req.setAttribute("canceledOrders", canceledOrders);

			req.setAttribute("page", "orders");
			req.setAttribute("view", "/views/vendor/orders/list.jsp");
			req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);

		} else if (uri.endsWith("/add")) {
			req.setAttribute("page", "orders");
			req.setAttribute("view", "/views/vendor/orders/add.jsp");
			req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);

		} else if (uri.endsWith("/detail")) {
			String idParam = req.getParameter("id");
			if (idParam != null && !idParam.isEmpty()) {
				int id = Integer.parseInt(idParam);
				Order o = orderService.getById(id);
				req.setAttribute("order", o);

				// Lấy shipper
				User shipper = orderService.getOrderShipper(o);
				req.setAttribute("shipper", shipper);

				req.setAttribute("page", "orders");
				req.setAttribute("view", "/views/vendor/orders/detail.jsp");
				req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
			} else {
				resp.sendRedirect(req.getContextPath() + "/vendor/orders");
			}
		} else if (uri.endsWith("/delete")) {
			String idParam = req.getParameter("id");
			if (idParam != null && !idParam.isEmpty()) {
				try {
					int id = Integer.parseInt(idParam);
					orderService.delete(id);
				} catch (NumberFormatException e) {
					System.out.println("Invalid order id: " + idParam);
				}
			}
			resp.sendRedirect(req.getContextPath() + "/vendor/orders");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI();

		if (uri.endsWith("/add")) {
			int userId = Integer.parseInt(req.getParameter("user_id"));
			BigDecimal totalAmount = new BigDecimal(req.getParameter("total_amount"));
			String status = req.getParameter("status");
			String paymentMethod = req.getParameter("payment_method");

			Order order = new Order();
			User u = new User();
			u.setUserId(userId);
			order.setUser(u);

			order.setTotalAmount(totalAmount);
			order.setStatus(status);
			order.setPaymentMethod(paymentMethod);

//            orderService.insert(order); 
			resp.sendRedirect(req.getContextPath() + "/vendor/orders");

		} else if (uri.endsWith("/detail")) {
			String idParam = req.getParameter("id");
			if (idParam != null && !idParam.isEmpty()) {
				int id = Integer.parseInt(idParam);
				Order o = orderService.getById(id);
				req.setAttribute("order", o);

				// Lấy shipper
				User shipper = orderService.getOrderShipper(o);
				req.setAttribute("shipper", shipper);

				req.setAttribute("page", "orders");
				req.setAttribute("view", "/views/vendor/orders/detail.jsp");
				req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
			} else {
				resp.sendRedirect(req.getContextPath() + "/vendor/orders");
			}
		} else if (uri.endsWith("/action")) {
			// Xử lý confirm / cancel
			String action = req.getParameter("action");
			String idParam = req.getParameter("id");

			if (idParam != null && !idParam.isEmpty()) {
				int orderId = Integer.parseInt(idParam);
				Order order = orderService.getById(orderId);
				if (order != null) {
					switch (action) {
					case "confirm":
						order.setStatus("Đã xác nhận");
						orderService.update(order);
						// TODO: gửi notification đến user
						Notification n1 = Notification.builder().user(order.getUser())
								.title("Đơn hàng #" + order.getOrderId() + " đã được xác nhận")
								.message("Cửa hàng đã xác nhận đơn hàng của bạn. Đơn đang được chuẩn bị để giao.")
								.build();
						notificationService.insert(n1);

						if (order.getDeliveries() != null) {
							for (Delivery d : order.getDeliveries()) {
								deliveryService.updateStatus(d.getDeliveryId(), "Tìm Shipper");
							}
						}
						break;
					case "cancel":
						String reason = req.getParameter("reason");
						order.setStatus("Đã hủy");
						orderService.update(order);
						// TODO: gửi notification đến user
						String cancelMsg = "Đơn hàng của bạn đã bị hủy bởi cửa hàng.";
						if (reason != null && !reason.trim().isEmpty()) {
							cancelMsg += " Lý do: " + reason.trim();
						}

						Notification cancelNoti = Notification.builder().user(order.getUser())
								.title("Đơn hàng #" + order.getOrderId() + " đã bị hủy").message(cancelMsg).build();
						notificationService.insert(cancelNoti);

						if (order.getDeliveries() != null) {
							for (Delivery d : order.getDeliveries()) {
								deliveryService.updateStatus(d.getDeliveryId(), "Đã hủy");
							}
						}
						break;
					default:
						System.out.println("Unknown action: " + action);
					}
				}
			}
			resp.sendRedirect(req.getContextPath() + "/vendor/orders");
		}
	}
}
