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
	            .collect(Collectors.groupingBy(
	                    item -> item.getProductVariant().getProduct().getShop().getShopId()));

	    // Lấy danh sách khuyến mãi hợp lệ cho từng shop
	    Map<Integer, List<Promotion>> promosByShop = new HashMap<>();
	    for (Integer shopId : itemsByShop.keySet()) {
	        List<Promotion> promos = promotionService.getValidPromotionsByShop(shopId);
	        promosByShop.put(shopId, promos);
	    }

	    req.setAttribute("itemsByShop", itemsByShop);
	    req.setAttribute("promosByShop", promosByShop);
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
        String payment = req.getParameter("payment");
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

        // Gom theo shop
        Map<Integer, List<CartItem>> itemsByShop = selectedItems.stream()
                .collect(Collectors.groupingBy(
                        i -> i.getProductVariant().getProduct().getShop().getShopId()));

        BigDecimal shippingFee = new BigDecimal("30000");
        boolean allSuccess = true;

        // ---- Lặp qua từng shop để tạo đơn riêng ----
        for (Map.Entry<Integer, List<CartItem>> entry : itemsByShop.entrySet()) {
            Integer shopId = entry.getKey();
            List<CartItem> shopItems = entry.getValue();

            // Tính tổng tiền của shop
            BigDecimal subtotal = shopItems.stream()
                    .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Áp dụng khuyến mãi riêng từng shop
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
                } catch (Exception ignored) {
                }
            }

            BigDecimal total = subtotal.add(shippingFee).subtract(discount);
            if (total.compareTo(BigDecimal.ZERO) < 0) total = BigDecimal.ZERO;

            // ---- Tạo đơn hàng ----
            Order order = new Order();
            order.setUser(user);
            Shop shop = shopservice.getReferenceById(shopId);
            order.setShop(shop);
            order.setPaymentMethod(payment);
            order.setStatus("Mới");
            order.setCreatedAt(new Date());
            order.setAddress(address);
            order.setTotalAmount(total);

            // Thêm chi tiết đơn hàng
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
                // Xóa giỏ hàng cho sản phẩm đã đặt
                shopItems.forEach(ci -> cartService.removeFromCart(ci.getCartItemId()));
            } else {
                allSuccess = false;
            }
        }

        if (allSuccess) {
        	// 🧭 Chuyển hướng theo phương thức thanh toán
            if ("COD".equalsIgnoreCase(payment)) {
                req.getSession().setAttribute("success", "🎉 Đặt hàng thành công! Cảm ơn bạn đã mua sắm tại UTE Shop.");
                resp.sendRedirect(req.getContextPath() + "/user/orders");
                return;
            } else if ("Momo".equalsIgnoreCase(payment)) {
                resp.sendRedirect(req.getContextPath() + "/user/payment/momo");
                return;
            } else if ("VNPay".equalsIgnoreCase(payment)) {
                resp.sendRedirect(req.getContextPath() + "/user/payment/vnpay");
                return;
            } else {
                req.getSession().setAttribute("error", "Phương thức thanh toán không hợp lệ!");
                resp.sendRedirect(req.getContextPath() + "/user/checkout");
                return;
            }
        } else {
            req.setAttribute("error", "❌ Có lỗi xảy ra khi đặt hàng một số shop, vui lòng thử lại!");
            doGet(req, resp);
        }
	}
}
