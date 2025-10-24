package ute.shop.controller.users.payment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.config.VnPayConfig;
import ute.shop.entity.User;
import ute.shop.service.IOrderService;
import ute.shop.service.impl.OrderServiceImpl;

@WebServlet("/user/payment/vnpay_return")
public class PaymentVnpayReturnController extends HttpServlet {
	private final IOrderService orderService = new OrderServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ✅ Kiểm tra JWT (filter đã set user vào request)
		User user = (User) req.getAttribute("account");
		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		Map<String, String> fields = new HashMap<>();
		for (Enumeration<String> params = req.getParameterNames(); params.hasMoreElements();) {
			String fieldName = params.nextElement();
			String fieldValue = req.getParameter(fieldName);
			if (fieldValue != null && !fieldValue.isEmpty()) {
				fields.put(fieldName, fieldValue);
			}
		}

		String vnp_SecureHash = fields.remove("vnp_SecureHash");
		fields.remove("vnp_SecureHashType");

		String signValue = VnPayConfig.hashAllFields(fields);
		String vnp_ResponseCode = fields.get("vnp_ResponseCode");
		String orderRef = fields.get("vnp_TxnRef"); 
		String amount = fields.get("vnp_Amount");

		// ✅ Tách nhiều orderId
		List<Integer> orderIds = new ArrayList<>();
		if (orderRef != null && !orderRef.isEmpty()) {
			orderIds = Arrays.stream(orderRef.split("[-_,]")).map(idStr -> {
				try {
					return Integer.parseInt(idStr.trim());
				} catch (NumberFormatException e) {
					return null;
				}
			}).filter(Objects::nonNull).collect(Collectors.toList());
		}

		if (signValue.equals(vnp_SecureHash)) {
			if ("00".equals(vnp_ResponseCode)) {
				// ✅ Thanh toán thành công
				orderService.updateStatusForOrders(orderIds, "Mới");
				req.setAttribute("message", "✅ Thanh toán VNPay thành công. Tổng tiền: " + amount + " VNĐ.");
			} else {
				// ❌ Thanh toán thất bại
				orderService.updateStatusForOrders(orderIds, "Thanh toán VNPAY thất bại");
				req.setAttribute("message", "❌ Thanh toán thất bại. Mã lỗi: " + vnp_ResponseCode);
			}
		} else {
			req.setAttribute("message", "⚠️ Chữ ký không hợp lệ. Dữ liệu có thể bị chỉnh sửa!");
		}

		// ✅ Chuyển hướng về trang lịch sử đơn hàng
		resp.sendRedirect(req.getContextPath() + "/user/orders");
	}
}
