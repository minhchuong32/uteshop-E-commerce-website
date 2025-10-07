package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.ICartItemService;
import ute.shop.service.IOrderService;
import ute.shop.service.impl.CartItemServiceImpl;
import ute.shop.service.impl.OrderServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(urlPatterns = {"/user/checkout"})
public class CheckoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IOrderService orderService = new OrderServiceImpl();
    private final ICartItemService cartService = new CartItemServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.getRequestDispatcher("/views/user/order/checkout.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("account");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Lấy thông tin từ form
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String payment = req.getParameter("payment");

        // Lấy giỏ hàng từ DB
        List<CartItem> cartItems = cartService.getCartByUser(user);
        if (cartItems == null || cartItems.isEmpty()) {
            req.setAttribute("error", "Giỏ hàng của bạn đang trống!");
            req.getRequestDispatcher("/views/user/cart.jsp").forward(req, resp);
            return;
        }

     // Tính tổng tiền
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            // Lấy giá từ variant nếu có, không lấy từ product
            BigDecimal price = (item.getProductVariant() != null) 
                    ? item.getProductVariant().getPrice() 
                    : item.getProductVariant().getPrice(); 

            totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));
        }


        // Tạo order
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(totalAmount);
        order.setPaymentMethod(payment);
        order.setStatus("new");
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        boolean success = orderService.insert(order);

        if (success) {
            // Xóa giỏ hàng
            for (CartItem item : cartItems) {
                cartService.removeFromCart(item.getCartItemId());
            }

            resp.sendRedirect(req.getContextPath() + "/user/orders");
        } else {
            req.setAttribute("error", "Đặt hàng thất bại, vui lòng thử lại!");
            req.getRequestDispatcher("/views/user/checkout.jsp").forward(req, resp);
        }
    }
}
