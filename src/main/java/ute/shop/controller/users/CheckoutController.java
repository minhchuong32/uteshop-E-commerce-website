package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.ICartItemService;
import ute.shop.service.IOrderService;
import ute.shop.service.IPromotionService;
import ute.shop.service.impl.CartItemServiceImpl;
import ute.shop.service.impl.OrderServiceImpl;
import ute.shop.service.impl.PromotionServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(urlPatterns = { "/user/checkout" })
public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final IOrderService orderService = new OrderServiceImpl();
	private final ICartItemService cartService = new CartItemServiceImpl();
	private final IPromotionService promotionService = new PromotionServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("account");
		if (user == null) {
	        resp.sendRedirect(req.getContextPath() + "/login");
	        return;
	    }

		 // ✅ Lấy danh sách ID được chọn từ form giỏ hàng
	    String[] selectedIds = req.getParameterValues("selectedItems");

	    List<CartItem> cartItems;
	    if (selectedIds != null && selectedIds.length > 0) {
	        // ✅ Chỉ lấy những item được tick
	        cartItems = cartService.getCartByIds(selectedIds);
	    } else {
	        // Nếu không có sản phẩm nào chọn → hiển thị rỗng
	        cartItems = List.of();
	    }

	    req.setAttribute("cartItems", cartItems);

	    // ✅ Nếu có sản phẩm, lấy khuyến mãi của shop đầu tiên
	    if (!cartItems.isEmpty()) {
	        int shopId = cartItems.get(0).getProductVariant().getProduct().getShop().getShopId();
	        List<Promotion> promotions = promotionService.getValidPromotionsByShop(shopId);
	        req.setAttribute("promotions", promotions);
	    } else {
	        req.setAttribute("promotions", List.of());
	    }

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
		String promoIdParam = req.getParameter("promotionId");

		if (selectedIds == null || selectedIds.length == 0) {
			req.setAttribute("error", "Vui lòng chọn sản phẩm cần thanh toán!");
			doGet(req, resp);
			return;
		}

		// Lấy danh sách item được chọn
		List<CartItem> selectedItems = cartService.getCartByIds(selectedIds);
		if (selectedItems.isEmpty()) {
			req.setAttribute("error", "Không tìm thấy sản phẩm được chọn!");
			doGet(req, resp);
			return;
		}

		// Tính tổng tiền
		BigDecimal subtotal = selectedItems.stream()
				.map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		// Áp dụng khuyến mãi
		BigDecimal discount = BigDecimal.ZERO;
		if (promoIdParam != null && !promoIdParam.isEmpty()) {
			try {
				Promotion promo = promotionService.findById(Integer.parseInt(promoIdParam));
				if (promo != null) {
					if ("percent".equalsIgnoreCase(promo.getDiscountType())) {
						discount = subtotal.multiply(promo.getValue().divide(BigDecimal.valueOf(100)));
					} else if ("fixed".equalsIgnoreCase(promo.getDiscountType())) {
						discount = promo.getValue();
					}
				}
			} catch (Exception ignored) {
			}
		}

		BigDecimal total = subtotal.subtract(discount);
		if (total.compareTo(BigDecimal.ZERO) < 0)
			total = BigDecimal.ZERO;

		// Tạo order
		Order order = new Order();
		order.setUser(user);
		order.setPaymentMethod(payment);
		order.setStatus("Mới");
		order.setCreatedAt(new java.util.Date());
		order.setAddress(address);
		order.setTotalAmount(total);

		// Thêm chi tiết đơn hàng
		for (CartItem ci : selectedItems) {
			OrderDetail od = new OrderDetail();
			od.setOrder(order);
			od.setProductVariant(ci.getProductVariant());
			od.setQuantity(ci.getQuantity());
			od.setPrice(ci.getPrice());
			order.getOrderDetails().add(od);
		}

		boolean success = orderService.insert(order);
		if (success) {
			// Xóa sản phẩm đã chọn khỏi giỏ
			selectedItems.forEach(i -> cartService.removeFromCart(i.getCartItemId()));

			req.getSession().setAttribute("message", "🎉 Đặt hàng thành công!");
			resp.sendRedirect(req.getContextPath() + "/user/orders");
		} else {
			req.setAttribute("error", "❌ Có lỗi khi đặt hàng!");
			doGet(req, resp);
		}
	}
}
