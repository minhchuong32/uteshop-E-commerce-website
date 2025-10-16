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

        User user = (User) req.getSession().getAttribute("account");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ✅ Lấy thông báo thành công nếu có
        String success = (String) req.getSession().getAttribute("success");
        if (success != null) {
            req.setAttribute("successMessage", success);
            req.getSession().removeAttribute("success");
        }
        String status = req.getParameter("status");
        
        List<Order> orders;
        if (status != null && !status.isEmpty()) {
            orders = orderService.getOrdersByUserAndStatus(user.getUserId(), status);
        } else {
            orders = orderService.findByUser(user);
        }
        
        // ✅ Gán cờ "reviewed" cho sản phẩm đã giao
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
