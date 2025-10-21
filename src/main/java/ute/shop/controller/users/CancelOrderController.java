package ute.shop.controller.users;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Order;
import ute.shop.entity.User;
import ute.shop.service.IOrderService;
import ute.shop.service.impl.OrderServiceImpl;

@WebServlet(urlPatterns = { "/user/orders/cancel" })
public class CancelOrderController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IOrderService orderService = new OrderServiceImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("account");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int orderId = Integer.parseInt(req.getParameter("orderId"));
        Order order = orderService.getById(orderId);

        if (order != null && "Mới".equals(order.getStatus())) {
            orderService.updateStatus(orderId, "Đã hủy");
            req.getSession().setAttribute("success", "Đơn hàng #" + orderId + " đã được hủy thành công.");
        } else {
            req.getSession().setAttribute("success", "Không thể hủy đơn hàng này.");
        }

        resp.sendRedirect(req.getContextPath() + "/user/orders");
	}

}
