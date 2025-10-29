package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.Order;
import ute.shop.entity.OrderDetail;
import ute.shop.entity.Product;
import ute.shop.entity.User;
import ute.shop.service.IOrderService;
import ute.shop.service.IReviewService;
import ute.shop.service.impl.OrderServiceImpl;
import ute.shop.service.impl.ReviewServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/user/orders"})
public class OrderHistoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IOrderService orderService = new OrderServiceImpl();
    private final IReviewService reviewService = new ReviewServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ✅ Step 1: Get User from request (already correct)
        User user = (User) req.getAttribute("account");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ✅ Step 2: Handle flash messages from URL parameters (The JWT Way)
        // This replaces the session logic.
        String reviewStatus = req.getParameter("review_status");
        String orderStatus = req.getParameter("order_status");
        if ("success".equals(orderStatus)) {
            req.setAttribute("successMessage", "Đặt hàng thành công! Cảm ơn bạn đã mua sắm.");
        } else if ("fail".equals(orderStatus)) {
            req.setAttribute("errorMessage", "Đặt hàng thất bại. Vui lòng thử lại.");
        }
        
		if ("success".equals(reviewStatus)) {
			req.setAttribute("successMessage", "Cảm ơn bạn đã đánh giá sản phẩm!");
		} else if ("fail".equals(reviewStatus)) {
			req.setAttribute("errorMessage", "Đánh giá sản phẩm thất bại. Vui lòng thử lại.");
		}
        
        // This part for filtering orders is also correct.
        String orderStatusFilter = req.getParameter("status");
        
        List<Order> orders;
        if (orderStatusFilter != null && !orderStatusFilter.isEmpty()) {
            orders = orderService.getOrdersByUserAndStatus(user.getUserId(), orderStatusFilter);
        } else {
            orders = orderService.findByUser(user);
        }
        
        // ✅ Step 3: The rest of the logic is already stateless and correct.
        for (Order order : orders) {
            if ("Đã giao".equalsIgnoreCase(order.getStatus())) {
                for (OrderDetail detail : order.getOrderDetails()) {
                    if (detail.getProductVariant() != null && detail.getProductVariant().getProduct() != null) {
                        Product product = detail.getProductVariant().getProduct();
                        boolean reviewed = reviewService.hasReviewed(user, product);
                        product.setReviewed(reviewed);
                    }
                }
            }
        }
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/views/user/order/history.jsp").forward(req, resp);
    }
}

