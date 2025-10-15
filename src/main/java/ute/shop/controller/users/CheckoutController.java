//package ute.shop.controller.users;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//import ute.shop.dao.IShopDao;
//import ute.shop.entity.*;
//import ute.shop.service.ICartItemService;
//import ute.shop.service.IOrderService;
//import ute.shop.service.IPromotionService;
//import ute.shop.service.IShopService;
//import ute.shop.service.impl.CartItemServiceImpl;
//import ute.shop.service.impl.OrderServiceImpl;
//import ute.shop.service.impl.PromotionServiceImpl;
//import ute.shop.service.impl.ShopServiceImpl;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@WebServlet(urlPatterns = { "/user/checkout" })
//public class CheckoutController extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	private final IOrderService orderService = new OrderServiceImpl();
//	private final ICartItemService cartService = new CartItemServiceImpl();
//	private final IPromotionService promotionService = new PromotionServiceImpl();
//	private final IShopService shopservice = new ShopServiceImpl();
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		User user = (User) req.getSession().getAttribute("account");
//		if (user == null) {
//			resp.sendRedirect(req.getContextPath() + "/login");
//			return;
//		}
//
//		String[] selectedParams = req.getParameterValues("selectedItems");
//		List<CartItem> cartItems;
//
//		if (selectedParams != null && selectedParams.length > 0) {
//			cartItems = cartService.getCartByIds(selectedParams);
//			System.out.println("🛒 selectedItems = " + String.join(",", selectedParams));
//			System.out.println("📦 Số lượng ID: " + selectedParams.length);
//		} else {
//			cartItems = cartService.getCartByUser(user);
//		}
//		System.out.println("🧺 Số lượng item lấy được: " + cartItems.size());
//
//		if (cartItems.isEmpty()) {
//			req.setAttribute("message", "Giỏ hàng trống hoặc không có sản phẩm hợp lệ!");
//			req.getRequestDispatcher("/views/user/order/checkout.jsp").forward(req, resp);
//			return;
//		}
//
//		// Gom sản phẩm theo shop
//		Map<Integer, List<CartItem>> itemsByShop = cartItems.stream()
//				.collect(Collectors.groupingBy(item -> item.getProductVariant().getProduct().getShop().getShopId()));
//
//		// Lấy danh sách khuyến mãi hợp lệ cho từng shop
//		Map<Integer, List<Promotion>> promosByShop = new HashMap<>();
//		for (Integer shopId : itemsByShop.keySet()) {
//			List<Promotion> promos = promotionService.getValidPromotionsByShop(shopId);
//			promosByShop.put(shopId, promos);
//		}
//
//		req.setAttribute("itemsByShop", itemsByShop);
//		req.setAttribute("promosByShop", promosByShop);
//		req.getRequestDispatcher("/views/user/order/checkout.jsp").forward(req, resp);
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		User user = (User) req.getSession().getAttribute("account");
//		if (user == null) {
//			resp.sendRedirect(req.getContextPath() + "/login");
//			return;
//		}
//
//		req.setCharacterEncoding("UTF-8");
//
//		String[] selectedIds = req.getParameterValues("selectedItems");
//		String payment = req.getParameter("paymentMethod").toLowerCase();
//		String address = req.getParameter("address");
//
//		if (selectedIds == null || selectedIds.length == 0) {
//			req.setAttribute("error", "Vui lòng chọn sản phẩm cần thanh toán!");
//			doGet(req, resp);
//			return;
//		}
//
//		// Lấy item từ DB
//		List<CartItem> selectedItems = cartService.getCartByIds(selectedIds);
//		if (selectedItems.isEmpty()) {
//			req.setAttribute("error", "Không tìm thấy sản phẩm được chọn!");
//			doGet(req, resp);
//			return;
//		}
//
//		// Gom theo shop
//		Map<Integer, List<CartItem>> itemsByShop = selectedItems.stream()
//				.collect(Collectors.groupingBy(i -> i.getProductVariant().getProduct().getShop().getShopId()));
//
//		BigDecimal shippingFee = new BigDecimal("30000");
//		boolean allSuccess = true;
//		BigDecimal allShopTotal = BigDecimal.ZERO;
//		// ---- Lặp qua từng shop để tạo đơn riêng ----
//		for (Map.Entry<Integer, List<CartItem>> entry : itemsByShop.entrySet()) {
//			Integer shopId = entry.getKey();
//			List<CartItem> shopItems = entry.getValue();
//
//			// Tính tổng tiền của shop
//			BigDecimal subtotal = shopItems.stream()
//					.map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
//					.reduce(BigDecimal.ZERO, BigDecimal::add);
//
//			// Áp dụng khuyến mãi riêng từng shop
//			String promoParam = req.getParameter("promotionId[" + shopId + "]");
//			BigDecimal discount = BigDecimal.ZERO;
//			if (promoParam != null && !promoParam.isEmpty()) {
//				try {
//					int promoId = Integer.parseInt(promoParam);
//					Promotion promo = promotionService.findById(promoId);
//					if (promo != null && promo.getShop().getShopId().equals(shopId)) {
//						if ("percent".equalsIgnoreCase(promo.getDiscountType())) {
//							discount = subtotal.multiply(promo.getValue().divide(BigDecimal.valueOf(100)));
//						} else if ("fixed".equalsIgnoreCase(promo.getDiscountType())) {
//							discount = promo.getValue();
//						}
//					}
//				} catch (Exception ignored) {
//				}
//			}
//
//			BigDecimal total = subtotal.add(shippingFee).subtract(discount);
//			if (total.compareTo(BigDecimal.ZERO) < 0)
//				total = BigDecimal.ZERO;
//			
//			allShopTotal = allShopTotal.add(total); 
//			// ---- Tạo đơn hàng ----
//			Order order = new Order();
//			order.setUser(user);
//			Shop shop = shopservice.getReferenceById(shopId);
//			order.setShop(shop);
//			order.setPaymentMethod(payment);
//			order.setStatus("Mới");
//			order.setCreatedAt(new Date());
//			order.setAddress(address);
//			order.setTotalAmount(total);
//
//			// Thêm chi tiết đơn hàng
//			for (CartItem ci : shopItems) {
//				OrderDetail od = new OrderDetail();
//				od.setOrder(order);
//				od.setProductVariant(ci.getProductVariant());
//				od.setQuantity(ci.getQuantity());
//				od.setPrice(ci.getPrice());
//				order.getOrderDetails().add(od);
//			}
//
//			boolean success = orderService.insert(order);
//			if (success) {
//				// Xóa giỏ hàng cho sản phẩm đã đặt
//				shopItems.forEach(ci -> cartService.removeFromCart(ci.getCartItemId()));
//			} else {
//				allSuccess = false;
//			}
//		}
//
//		if (allSuccess) {
//			req.getSession().setAttribute("paymentTotal", allShopTotal);
//
//			switch (payment) {
//			case "cod" -> {
//				req.getSession().setAttribute("success", "🎉 Đặt hàng thành công!");
//				resp.sendRedirect(req.getContextPath() + "/user/orders");
//			}
//			case "momo" -> resp.sendRedirect(req.getContextPath() + "/user/payment/momo");
//			case "vnpay" -> resp.sendRedirect(req.getContextPath() + "/user/payment/vnpay");
//			default -> {
//				req.getSession().setAttribute("error", "Phương thức thanh toán không hợp lệ!");
//				resp.sendRedirect(req.getContextPath() + "/user/checkout");
//			}
//			}
//		} else {
//			req.setAttribute("error", "❌ Có lỗi xảy ra khi đặt hàng một số shop, vui lòng thử lại!");
//			doGet(req, resp);
//		}
//	}
//}


package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.dao.IShopDao;
import ute.shop.entity.*;
import ute.shop.service.ICartItemService;
import ute.shop.service.IOrderService;
import ute.shop.service.IPromotionService;
import ute.shop.service.IShopService;
import ute.shop.service.impl.CartItemServiceImpl;
import ute.shop.service.impl.OrderServiceImpl;
import ute.shop.service.impl.PromotionServiceImpl;
import ute.shop.service.impl.ShopServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = { "/user/checkout" })
public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final IOrderService orderService = new OrderServiceImpl();
	private final ICartItemService cartService = new CartItemServiceImpl();
	private final IPromotionService promotionService = new PromotionServiceImpl();
	private final IShopService shopservice = new ShopServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			User user = (User) req.getSession().getAttribute("account");
			if (user == null) {
				resp.sendRedirect(req.getContextPath() + "/login");
				return;
			}

			String[] selectedParams = req.getParameterValues("selectedItems");
			List<CartItem> cartItems;

			if (selectedParams != null && selectedParams.length > 0) {
				cartItems = cartService.getCartByIds(selectedParams);
				System.out.println("🛒 selectedItems = " + String.join(",", selectedParams));
				System.out.println("📦 Số lượng ID: " + selectedParams.length);
			} else {
				cartItems = cartService.getCartByUser(user);
			}
			System.out.println("🧺 Số lượng item lấy được: " + cartItems.size());

			if (cartItems.isEmpty()) {
				req.setAttribute("message", "Giỏ hàng trống hoặc không có sản phẩm hợp lệ!");
				req.getRequestDispatcher("/views/user/order/checkout.jsp").forward(req, resp);
				return;
			}

			// Gom sản phẩm theo shop
			Map<Integer, List<CartItem>> itemsByShop = cartItems.stream()
					.collect(Collectors.groupingBy(item -> item.getProductVariant().getProduct().getShop().getShopId()));

			// Lấy danh sách khuyến mãi hợp lệ cho từng shop
			Map<Integer, List<Promotion>> promosByShop = new HashMap<>();
			for (Integer shopId : itemsByShop.keySet()) {
				List<Promotion> promos = promotionService.getValidPromotionsByShop(shopId);
				promosByShop.put(shopId, promos);
			}

			req.setAttribute("itemsByShop", itemsByShop);
			req.setAttribute("promosByShop", promosByShop);
			req.getRequestDispatcher("/views/user/order/checkout.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "❌ Lỗi trong quá trình tải trang thanh toán: " + e.getMessage());
			req.getRequestDispatcher("/views/error.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			User user = (User) req.getSession().getAttribute("account");
			if (user == null) {
				resp.sendRedirect(req.getContextPath() + "/login");
				return;
			}

			req.setCharacterEncoding("UTF-8");

			String[] selectedIds = req.getParameterValues("selectedItems");
			String payment = req.getParameter("paymentMethod");
			String address = req.getParameter("address");

			if (payment == null) payment = "cod"; // fallback mặc định
			payment = payment.toLowerCase();

			if (selectedIds == null || selectedIds.length == 0) {
				req.setAttribute("error", "Vui lòng chọn sản phẩm cần thanh toán!");
				doGet(req, resp);
				return;
			}

			List<CartItem> selectedItems = cartService.getCartByIds(selectedIds);
			if (selectedItems.isEmpty()) {
				req.setAttribute("error", "Không tìm thấy sản phẩm được chọn!");
				doGet(req, resp);
				return;
			}

			Map<Integer, List<CartItem>> itemsByShop = selectedItems.stream()
					.collect(Collectors.groupingBy(i -> i.getProductVariant().getProduct().getShop().getShopId()));

			BigDecimal shippingFee = new BigDecimal("30000");
			boolean allSuccess = true;
			BigDecimal allShopTotal = BigDecimal.ZERO;

			for (Map.Entry<Integer, List<CartItem>> entry : itemsByShop.entrySet()) {
				try {
					Integer shopId = entry.getKey();
					List<CartItem> shopItems = entry.getValue();

					// Tính tổng tiền
					BigDecimal subtotal = shopItems.stream()
							.map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
							.reduce(BigDecimal.ZERO, BigDecimal::add);

					// Áp dụng khuyến mãi
					String promoParam = req.getParameter("promotionId[" + shopId + "]");
					BigDecimal discount = BigDecimal.ZERO;

					if (promoParam != null && !promoParam.isEmpty()) {
						try {
							int promoId = Integer.parseInt(promoParam);
							Promotion promo = promotionService.findById(promoId);
							if (promo != null && promo.getShop().getShopId().equals(shopId)) {
								if ("percent".equalsIgnoreCase(promo.getDiscountType())) {
									discount = subtotal.multiply(promo.getValue().divide(BigDecimal.valueOf(100)));
								} else if ("fixed".equalsIgnoreCase(promo.getDiscountType())) {
									discount = promo.getValue();
								}
							}
						} catch (NumberFormatException e) {
							System.err.println("⚠️ Lỗi parse promotionId cho shop " + shopId);
						}
					}

					BigDecimal total = subtotal.add(shippingFee).subtract(discount);
					if (total.compareTo(BigDecimal.ZERO) < 0)
						total = BigDecimal.ZERO;

					allShopTotal = allShopTotal.add(total);

					Order order = new Order();
					order.setUser(user);
					order.setShop(shopservice.getReferenceById(shopId));
					order.setPaymentMethod(payment);
					order.setStatus("Mới");
					order.setCreatedAt(new Date());
					order.setAddress(address);
					order.setTotalAmount(total);

					for (CartItem ci : shopItems) {
						OrderDetail od = new OrderDetail();
						od.setOrder(order);
						od.setProductVariant(ci.getProductVariant());
						od.setQuantity(ci.getQuantity());
						od.setPrice(ci.getPrice());
						order.getOrderDetails().add(od);
					}

					boolean success = orderService.insert(order);
					if (success) {
						shopItems.forEach(ci -> cartService.removeFromCart(ci.getCartItemId()));
					} else {
						allSuccess = false;
						System.err.println("❌ Không thể tạo đơn hàng cho shopId=" + shopId);
					}
				} catch (Exception ex) {
					allSuccess = false;
					ex.printStackTrace();
				}
			}

			if (allSuccess) {
				req.getSession().setAttribute("paymentTotal", allShopTotal);

				switch (payment) {
					case "cod" -> {
						req.getSession().setAttribute("success", "🎉 Đặt hàng thành công!");
						resp.sendRedirect(req.getContextPath() + "/user/orders");
					}
					case "momo" -> resp.sendRedirect(req.getContextPath() + "/user/payment/momo");
					case "vnpay" -> resp.sendRedirect(req.getContextPath() + "/user/payment/vnpay");
					default -> {
						req.getSession().setAttribute("error", "Phương thức thanh toán không hợp lệ!");
						resp.sendRedirect(req.getContextPath() + "/user/checkout");
					}
				}
			} else {
				req.setAttribute("error", "❌ Có lỗi xảy ra khi đặt hàng một số shop, vui lòng thử lại!");
				doGet(req, resp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "❌ Lỗi khi xử lý đơn hàng: " + e.getMessage());
			req.getRequestDispatcher("/views/error.jsp").forward(req, resp);
		}
	}
}
