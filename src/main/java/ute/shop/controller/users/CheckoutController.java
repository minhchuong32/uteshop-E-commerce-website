package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.dao.IShopDao;
import ute.shop.entity.*;
import ute.shop.service.ICarrierService;
import ute.shop.service.ICartItemService;
import ute.shop.service.IDeliveryService;
import ute.shop.service.IOrderService;
import ute.shop.service.IPromotionService;
import ute.shop.service.IShopService;
import ute.shop.service.impl.CarrierServiceImpl;
import ute.shop.service.impl.CartItemServiceImpl;
import ute.shop.service.impl.DeliveryServiceImpl;
import ute.shop.service.impl.OrderServiceImpl;
import ute.shop.service.impl.PromotionServiceImpl;
import ute.shop.service.impl.ShopServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = { "/user/checkout" })
public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final IOrderService orderService = new OrderServiceImpl();
	private final ICartItemService cartService = new CartItemServiceImpl();
	private final IPromotionService promotionService = new PromotionServiceImpl();
	private final IShopService shopservice = new ShopServiceImpl();
	private final ICarrierService carrierService = new CarrierServiceImpl();
	private final IDeliveryService deliveryService = new DeliveryServiceImpl();


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

		// Thêm danh sách đơn vị vận chuyển

		List<Carrier> carriers = carrierService.findAll();
		req.setAttribute("carriers", carriers);

		// Với từng sản phẩm, nếu có khuyến mãi riêng → load thêm
		Map<Integer, List<Promotion>> promosByProduct = new HashMap<>();
		for (CartItem item : cartItems) {
			int productId = item.getProductVariant().getProduct().getProductId();
			List<Promotion> promos = promotionService.getValidPromotionsByProduct(productId);
			promosByProduct.put(productId, promos);
		}
		req.setAttribute("promosByProduct", promosByProduct);

		req.setAttribute("itemsByShop", itemsByShop);
		req.getRequestDispatcher("/views/user/order/checkout.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("account");
		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		req.setCharacterEncoding("UTF-8");

		String[] selectedIds = req.getParameterValues("selectedItems");
		String payment = req.getParameter("paymentMethod").toUpperCase();
		String address = req.getParameter("address");

		if (selectedIds == null || selectedIds.length == 0) {
			req.setAttribute("error", "Vui lòng chọn sản phẩm cần thanh toán!");
			doGet(req, resp);
			return;
		}

		// Lấy item từ DB
		List<CartItem> selectedItems = cartService.getCartByIds(selectedIds);
		if (selectedItems.isEmpty()) {
			req.setAttribute("error", "Không tìm thấy sản phẩm được chọn!");
			doGet(req, resp);
			return;
		}
		// Lấy đơn vị vận chuyển
		String carrierIdStr = req.getParameter("carrierId");
		Carrier carrier = null;
		BigDecimal shippingFee = BigDecimal.ZERO;
		if (carrierIdStr != null && !carrierIdStr.isEmpty()) {
			int carrierId = Integer.parseInt(carrierIdStr);
			carrier = carrierService.findById(carrierId);
			if (carrier != null)
				shippingFee = carrier.getCarrierFee();
		} else {
			shippingFee = new BigDecimal("30000"); // mặc định
		}

		// Gom theo shop
		Map<Integer, List<CartItem>> itemsByShop = selectedItems.stream()
				.collect(Collectors.groupingBy(i -> i.getProductVariant().getProduct().getShop().getShopId()));

		BigDecimal shippingFee1 = new BigDecimal("30000");
		boolean allSuccess = true;
		BigDecimal allShopTotal = BigDecimal.ZERO;
		// ---- Lặp qua từng shop để tạo đơn riêng ----
		for (Map.Entry<Integer, List<CartItem>> entry : itemsByShop.entrySet()) {
			Integer shopId = entry.getKey();
			List<CartItem> shopItems = entry.getValue();

			BigDecimal subtotal = shopItems.stream()
					.map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			// --- Áp dụng khuyến mãi từng sản phẩm ---
			BigDecimal productDiscount = BigDecimal.ZERO;
			for (CartItem item : shopItems) {
				String promoParam = req.getParameter(
						"promotionId_product[" + item.getProductVariant().getProduct().getProductId() + "]");
				if (promoParam != null && !promoParam.isEmpty()) {
					Promotion promo = promotionService.findById(Integer.parseInt(promoParam));
					if (promo != null) {
						BigDecimal price = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
						BigDecimal discount = BigDecimal.ZERO;

						if ("percent".equalsIgnoreCase(promo.getDiscountType())) {
							discount = price.multiply(promo.getValue().divide(BigDecimal.valueOf(100))).setScale(0,
									RoundingMode.HALF_UP);
						} else if ("fixed".equalsIgnoreCase(promo.getDiscountType())) {
							discount = promo.getValue();
						}

						if (discount.compareTo(price) > 0)
							discount = price; // tránh âm tiền
						productDiscount = productDiscount.add(discount);
					}
				}
			}

			BigDecimal total = subtotal.add(shippingFee1).subtract(productDiscount);
			if (total.compareTo(BigDecimal.ZERO) < 0)
				total = BigDecimal.ZERO;

			allShopTotal = allShopTotal.add(total);

			 // ----- Tạo Order -----
	        Order order = new Order();
	        order.setUser(user);
	        order.setShop(shopservice.getById(shopId));
	        order.setPaymentMethod(payment);
	        order.setStatus("Mới");
	        order.setCreatedAt(new Date());
	        order.setAddress(address);
	        order.setTotalAmount(total);

	        // ----- OrderDetails -----
	        for (CartItem ci : shopItems) {
	            OrderDetail od = new OrderDetail();
	            od.setOrder(order);
	            od.setProductVariant(ci.getProductVariant());
	            od.setQuantity(ci.getQuantity());
	            od.setPrice(ci.getPrice());
	            order.getOrderDetails().add(od);
	        }

	        // ----- Delivery -----
	        Delivery delivery = new Delivery();
	        delivery.setOrder(order);
	        delivery.setCreatedAt(new Date());
	        delivery.setStatus("Mới");
	        delivery.setCarrier(carrier);
	        order.getDeliveries().add(delivery);

	        // ----- Lưu toàn bộ trong 1 lần -----
	        Order savedOrder = orderService.save(order);

	        if (savedOrder != null) {
	            shopItems.forEach(ci -> cartService.removeFromCart(ci.getCartItemId()));
	        } else {
	            allSuccess = false;
	        }


		}

		if (allSuccess) {
			req.getSession().setAttribute("paymentTotal", allShopTotal);

			switch (payment) {
			case "COD" -> {
				req.getSession().setAttribute("success", "🎉 Đặt hàng thành công!");
				resp.sendRedirect(req.getContextPath() + "/user/orders");
			}
			case "MOMO" -> resp.sendRedirect(req.getContextPath() + "/user/payment/momo");
			case "VNPAY" -> resp.sendRedirect(req.getContextPath() + "/user/payment/vnpay");
			default -> {
				req.getSession().setAttribute("error", "Phương thức thanh toán không hợp lệ!");
				resp.sendRedirect(req.getContextPath() + "/user/checkout");
			}
			}
		} else {
			req.setAttribute("error", "❌ Có lỗi xảy ra khi đặt hàng một số shop, vui lòng thử lại!");
			doGet(req, resp);
		}
	}
}