package ute.shop.controller.users.payment;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
public class PaymentVnpayReturnController extends HttpServlet{
	private IOrderService orderService = new OrderServiceImpl(); // hoặc inject qua constructor
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getAttribute("account");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
		
		Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = req.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = req.getParameter(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                fields.put(fieldName, fieldValue);
            }
        }

        // Lấy mã checksum VNPay gửi về
        String vnp_SecureHash = req.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        // Tạo chuỗi dữ liệu để xác thực
        String signValue = VnPayConfig.hashAllFields(fields);

        // Lấy thông tin cơ bản
        String vnp_ResponseCode = req.getParameter("vnp_ResponseCode");
        String orderId = req.getParameter("vnp_TxnRef");

        if (signValue.equals(vnp_SecureHash)) {
            // ✅ Checksum hợp lệ
            if ("00".equals(vnp_ResponseCode)) {
                // Thanh toán thành công
                orderService.updateStatus(Integer.parseInt(orderId), "Đã thanh toán (VNPay)");
                req.setAttribute("message", "Thanh toán thành công cho đơn hàng #" + orderId);
            } else {
                // Thanh toán thất bại
                orderService.updateStatus(Integer.parseInt(orderId), "Chờ thanh toán VNPAY thất bại");
                req.setAttribute("message", "Thanh toán thất bại. Mã lỗi: " + vnp_ResponseCode);
            }
        } else {
            req.setAttribute("message", "Chữ ký không hợp lệ (có thể bị sửa dữ liệu).");
        }

        req.getRequestDispatcher("/views/user/payment/payment-result.jsp").forward(req, resp);
	}
}
