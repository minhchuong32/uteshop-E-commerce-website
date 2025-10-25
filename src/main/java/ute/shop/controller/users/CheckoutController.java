package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.ICarrierService;
import ute.shop.service.ICartItemService;
import ute.shop.service.IDeliveryService;
import ute.shop.service.IOrderService;
import ute.shop.service.IPromotionService;
import ute.shop.service.IShippingAddressService;
import ute.shop.service.IShopService;
import ute.shop.service.impl.CarrierServiceImpl;
import ute.shop.service.impl.CartItemServiceImpl;
import ute.shop.service.impl.DeliveryServiceImpl;
import ute.shop.service.impl.OrderServiceImpl;
import ute.shop.service.impl.PromotionServiceImpl;
import ute.shop.service.impl.ShippingAddressServiceImpl;
import ute.shop.service.impl.ShopServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
	private final IShippingAddressService addressService = new ShippingAddressServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getAttribute("account");
		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		//  Lấy danh sách địa chỉ giao hàng của user
	    List<ShippingAddress> addresses = addressService.getAddressesByUser(user.getUserId());
	    req.setAttribute("addresses", addresses);

	    // 🔹 Tìm địa chỉ mặc định
	    ShippingAddress defaultAddress = addresses.stream()
	    		.filter(ShippingAddress::getIsDefault)
	            .findFirst()
	            .orElse(null);
	    req.setAttribute("defaultAddress", defaultAddress);

	    if (defaultAddress == null) {
	        req.setAttribute("noDefaultAddress", true);
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
		User user = (User) req.getAttribute("account");
	    if (user == null) {
	        resp.sendRedirect(req.getContextPath() + "/login");
	        return;
	    }

	    req.setCharacterEncoding("UTF-8");
	    String[] selectedIds = req.getParameterValues("selectedItems");
	    String payment = req.getParameter("paymentMethod").toUpperCase();

	    // Lấy địa chỉ giao hàng
	    String addressIdStr = req.getParameter("selectedAddressId");
	    ShippingAddress shippingAddress = null;
	    if (addressIdStr != null && !addressIdStr.isEmpty()) {
	        shippingAddress = addressService.getById(Integer.parseInt(addressIdStr));
	    }
	    if (shippingAddress == null) {
	        req.setAttribute("error", "Vui lòng chọn hoặc thêm địa chỉ giao hàng!");
	        doGet(req, resp);
	        return;
	    }

	    // Lấy sản phẩm được chọn
	    List<CartItem> selectedItems = cartService.getCartByIds(selectedIds);
	    if (selectedItems.isEmpty()) {
	        req.setAttribute("error", "Không tìm thấy sản phẩm được chọn!");
	        doGet(req, resp);
	        return;
	    }

	    // Lấy đơn vị vận chuyển
	    Carrier carrier = null;
	    BigDecimal shippingFee = new BigDecimal("30000"); // mặc định
	    String carrierIdStr = req.getParameter("carrierId");
	    if (carrierIdStr != null && !carrierIdStr.isEmpty()) {
	        carrier = carrierService.findById(Integer.parseInt(carrierIdStr));
	        if (carrier != null) shippingFee = carrier.getCarrierFee();
	    }

	    // Gom theo shop
	    Map<Integer, List<CartItem>> itemsByShop = selectedItems.stream()
	            .collect(Collectors.groupingBy(i -> i.getProductVariant().getProduct().getShop().getShopId()));

	    BigDecimal allShopTotal = BigDecimal.ZERO;
	    boolean allSuccess = true;

	    // Chuẩn bị list lưu thông tin để truyền sang VNPay
	    StringBuilder orderIds = new StringBuilder();
	    StringBuilder shopNames = new StringBuilder();
	    
	    for (Map.Entry<Integer, List<CartItem>> entry : itemsByShop.entrySet()) {
	        Integer shopId = entry.getKey();
	        List<CartItem> shopItems = entry.getValue();

	        BigDecimal subtotal = shopItems.stream()
	                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
	                .reduce(BigDecimal.ZERO, BigDecimal::add);

	        // Áp dụng khuyến mãi
	        BigDecimal discountTotal = BigDecimal.ZERO;
	        for (CartItem item : shopItems) {
	            String promoParam = req.getParameter("promotionId_product[" + item.getProductVariant().getProduct().getProductId() + "]");
	            if (promoParam != null && !promoParam.isEmpty()) {
	                Promotion promo = promotionService.findById(Integer.parseInt(promoParam));
	                if (promo != null) {
	                    BigDecimal price = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
	                    BigDecimal discount = "percent".equalsIgnoreCase(promo.getDiscountType())
	                            ? price.multiply(promo.getValue().divide(BigDecimal.valueOf(100)))
	                            : promo.getValue();
	                    discountTotal = discountTotal.add(discount.min(price));
	                }
	            }
	        }

	        BigDecimal total = subtotal.add(shippingFee).subtract(discountTotal).max(BigDecimal.ZERO);
	        allShopTotal = allShopTotal.add(total);

	        // Tạo Order
	        Order order = new Order();
	        order.setUser(user);
	        order.setShop(shopservice.getById(shopId));
			order.setPaymentMethod(payment);
			switch (payment) {
			case "COD" -> order.setStatus("Mới");
			case "MOMO" -> order.setStatus("Chờ thanh toán MOMO");
			case "VNPAY" -> order.setStatus("Chờ thanh toán VNPAY");
			default -> {
				order.setStatus("Mới");
				}
			}

	        order.setCreatedAt(new Date());
	        order.setShippingAddress(shippingAddress);
	        order.setTotalAmount(total);

	        // Tạo OrderDetail
	        shopItems.forEach(ci -> {
	            OrderDetail od = new OrderDetail();
	            od.setOrder(order);
	            od.setProductVariant(ci.getProductVariant());
	            od.setQuantity(ci.getQuantity());
	            od.setPrice(ci.getPrice());
	            order.getOrderDetails().add(od);
	        });

	        // Tạo Delivery
	        Delivery delivery = new Delivery();
	        delivery.setOrder(order);
	        delivery.setCreatedAt(new Date());
	        switch (payment) {
			case "COD" -> delivery.setStatus("Mới");
			case "MOMO" -> delivery.setStatus("Chờ thanh toán MOMO");
			case "VNPAY" -> delivery.setStatus("Chờ thanh toán VNPAY");
			default -> {
				delivery.setStatus("Mới");
				}
			}
	        delivery.setCarrier(carrier);
	        order.getDeliveries().add(delivery);

	        // Lưu Order + Details + Delivery
	        Order savedOrder = orderService.save(order);
	        if (savedOrder != null) {
	            shopItems.forEach(ci -> cartService.removeFromCart(ci.getCartItemId()));
	            
	            if (orderIds.length() > 0) {
	                orderIds.append(",");
	                shopNames.append(",");
	            }
	            orderIds.append(savedOrder.getOrderId());
	            shopNames.append(savedOrder.getShop().getName());
	        } else {
	            allSuccess = false;
	        }
	    }

	    if (allSuccess) {
	        switch (payment) {
	            case "COD" -> resp.sendRedirect(req.getContextPath() + "/user/orders");
	            case "MOMO" -> resp.sendRedirect(req.getContextPath() + "/user/payment/momo?paymentTotal="
	                    + allShopTotal + "&orderIds=" + orderIds + "&shopNames=" + shopNames);
	            case "VNPAY" -> {
	                String redirectUrl = req.getContextPath() + "/user/payment/vnpay"
	                    + "?paymentTotal=" + allShopTotal
	                    + "&orderIds=" + URLEncoder.encode(orderIds.toString(), StandardCharsets.UTF_8)
	                    + "&shopNames=" + URLEncoder.encode(shopNames.toString(), StandardCharsets.UTF_8);
	                resp.sendRedirect(redirectUrl);
	            }
	            default -> {
	                req.setAttribute("error", "Phương thức thanh toán không hợp lệ!");
	                resp.sendRedirect(req.getContextPath() + "/user/checkout");
	            }
	        }
	    }

	}
}