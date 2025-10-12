package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.Order;
import ute.shop.entity.User;
import ute.shop.service.IOrderService;
import ute.shop.service.impl.OrderServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/user/orders"})
public class OrderHistoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IOrderService orderService = new OrderServiceImpl();

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
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/views/user/order/history.jsp").forward(req, resp);
    }
}
