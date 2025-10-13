package ute.shop.controller.users.payment;

import java.io.IOException;
import java.math.BigDecimal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user/payment/momo")
public class PaymentMomoController extends HttpServlet {
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	BigDecimal total = (BigDecimal) req.getSession().getAttribute("paymentTotal");
    if (total == null) {
        resp.sendRedirect(req.getContextPath() + "/user/checkout");
        return;
    }

    req.setAttribute("amount", total);
    req.getRequestDispatcher("/views/user/payment/payment-momo.jsp").forward(req, resp);
}
}
