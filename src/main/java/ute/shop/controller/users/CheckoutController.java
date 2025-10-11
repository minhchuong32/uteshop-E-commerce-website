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

@WebServlet(urlPatterns = {"/user/checkout"})
public class CheckoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IOrderService orderService = new OrderServiceImpl();
    private final ICartItemService cartService = new CartItemServiceImpl();
    private final IPromotionService promotionService = new PromotionServiceImpl();
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = (User) req.getSession().getAttribute("account");
        if (user != null) {
            List<CartItem> cartItems = cartService.getCartByUser(user);
            req.setAttribute("cartItems", cartItems);
        }
        req.getRequestDispatcher("/views/user/order/checkout.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// ✅ Kiểm tra đăng nhập
    	User user = (User) req.getSession().getAttribute("account");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String payment = req.getParameter("payment");
        String[] selectedIds = req.getParameterValues("selectedItems");
        String promoIdParam = req.getParameter("promotionId");

        if (selectedIds == null || selectedIds.length == 0) {
            req.setAttribute("error", "Vui lòng chọn sản phẩm cần thanh toán!");
            doGet(req, resp);
            return;
        }

        // ✅ Lấy cart item được chọn
        List<CartItem> selectedItems = cartService.getCartByIds(selectedIds);

        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItem item : selectedItems) {
            BigDecimal price = item.getPrice() != null ? item.getPrice() : BigDecimal.ZERO;
            subtotal = subtotal.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // ✅ Áp dụng khuyến mãi
        BigDecimal discount = BigDecimal.ZERO;
        if (promoIdParam != null && !promoIdParam.isEmpty()) {
            Promotion promo = promotionService.findById(Integer.parseInt(promoIdParam));
            if (promo != null) {
                if ("percent".equalsIgnoreCase(promo.getDiscountType())) {
                    discount = subtotal.multiply(promo.getValue().divide(BigDecimal.valueOf(100)));
                } else if ("fixed".equalsIgnoreCase(promo.getDiscountType())) {
                    discount = promo.getValue();
                }
            }
        }

        BigDecimal total = subtotal.subtract(discount);
        if (total.compareTo(BigDecimal.ZERO) < 0) total = BigDecimal.ZERO;

        // ✅ Tạo Order
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(total);
        order.setPaymentMethod(payment);
        order.setStatus("new");
        order.setCreatedAt(new java.util.Date());
        order.setAddress(req.getParameter("address"));

        // ✅ Sinh OrderDetail
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
            // Xóa các item được chọn khỏi giỏ
            for (CartItem ci : selectedItems) {
                cartService.removeFromCart(ci.getCartItemId());
            }
            req.getSession().setAttribute("message", "Đặt hàng thành công!");
            resp.sendRedirect(req.getContextPath() + "/views/user/order/checkout.jsp");
        } else {
            req.setAttribute("error", "Đặt hàng thất bại, vui lòng thử lại!");
            doGet(req, resp);
        }
    }
}
