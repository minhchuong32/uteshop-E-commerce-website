package ute.shop.controller.vendors;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ute.shop.entity.Order;
import ute.shop.entity.User;
import ute.shop.service.impl.OrderServiceImpl;

@WebServlet(urlPatterns = {
        "/vendor/products",
        "/vendor/products/add",
        "/vendor/products/edit",
        "/vendor/products/delete"
})
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        if (uri.endsWith("/orders")) {
            // Danh sách order
            List<Order> orders = orderService.getAll();
            req.setAttribute("products", orders);
            req.setAttribute("page", "orders");
            req.setAttribute("view", "/views/vendor/products/list.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);

        } else if (uri.endsWith("/add")) {
            req.setAttribute("page", "orders");
            req.setAttribute("view", "/views/vendor/products/add.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);

        } else if (uri.endsWith("/edit")) {
            String idParam = req.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                Order o = orderService.getById(id);
                
                req.setAttribute("order", o);
                req.setAttribute("page", "orders");
                req.setAttribute("view", "/views/vendor/products/edit.jsp");
                req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/vendor/products");
            }

        } else if (uri.endsWith("/delete")) {
            String idParam = req.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam);
                    orderService.delete(id);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid order id: " + idParam);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/vendor/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        if (uri.endsWith("/add")) {
            int userId = Integer.parseInt(req.getParameter("user_id"));
            BigDecimal totalAmount = new BigDecimal(req.getParameter("total_amount"));
            String status = req.getParameter("status");
            String paymentMethod = req.getParameter("payment_method");

            Order order = new Order();

            // Tạo user "ảo" chỉ set id
            User u = new User();
            u.setUserId(userId);
            order.setUser(u);

            order.setTotalAmount(totalAmount);
            order.setStatus(status);
            order.setPaymentMethod(paymentMethod);

            orderService.insert(order);  // Hibernate sẽ map user_id = userId
            resp.sendRedirect(req.getContextPath() + "/vendor/products");
        


        } else if (uri.endsWith("/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            BigDecimal totalAmount = new BigDecimal(req.getParameter("total_amount"));
            String status = req.getParameter("status");
            String paymentMethod = req.getParameter("payment_method");

            Order order = orderService.getById(id);
            if (order != null) {
                order.setTotalAmount(totalAmount);
                order.setStatus(status);
                order.setPaymentMethod(paymentMethod);
                orderService.update(order);
            }
            resp.sendRedirect(req.getContextPath() + "/vendor/products");

        }
    }
}
