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

@WebServlet("/shipper/orders")
public class ShipperOrderController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IDeliveryService deliveryService = new DeliveryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy shipperId từ session
		HttpSession session = req.getSession(false);
		User shipperLogin = (session != null) ? (User) session.getAttribute("account") : null;
		Integer shipperId = (Integer) shipperLogin.getUserId();
		if (shipperId == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		List<Delivery> deliveries = deliveryService.getByShipper(shipperId);
		req.setAttribute("deliveries", deliveries);
		req.setAttribute("shipperId", shipperId);
		
		req.setAttribute("page", "orders");
		req.setAttribute("view", "/views/shipper/orders.jsp");
		req.getRequestDispatcher("/WEB-INF/decorators/shipper.jsp").forward(req, resp);
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String deliveryIdStr = req.getParameter("deliveryId");
		String status = req.getParameter("status");

		if (deliveryIdStr != null && status != null) {
			Integer deliveryId = Integer.parseInt(deliveryIdStr);
			deliveryService.updateStatus(deliveryId, status);
		}

		// Quay lại trang danh sách
		resp.sendRedirect(req.getContextPath() + "/shipper/orders");
	}
}
