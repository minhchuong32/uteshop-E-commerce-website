package ute.shop.controller.users.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.config.VnPayConfig;
import ute.shop.entity.User;

@WebServlet("/user/payment/vnpay")
public class PaymentVnpayController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ✅ Kiểm tra JWT (user đã được set trong filter)
        User user = (User) req.getAttribute("account");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ✅ Lấy thông tin đơn hàng từ request (truyền từ frontend hoặc trang checkout)
        String totalStr = req.getParameter("paymentTotal");
        String orderIds = req.getParameter("orderIds");
        String shopNames = req.getParameter("shopNames");

        if (totalStr == null || orderIds == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin đơn hàng.");
            return;
        }

        BigDecimal total;
        try {
            total = new BigDecimal(totalStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Số tiền không hợp lệ.");
            return;
        }

        // ✅ Chuẩn bị dữ liệu VNPay
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", VnPayConfig.getTmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(total.multiply(BigDecimal.valueOf(100)).longValue())); // nhân 100
        vnp_Params.put("vnp_CurrCode", "VND");

        // Dùng orderIds làm mã giao dịch tạm
        vnp_Params.put("vnp_TxnRef", orderIds.replace(",", "-"));
        vnp_Params.put("vnp_OrderInfo", "Thanh toán đơn hàng " + orderIds + " - " + shopNames);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", req.getRemoteAddr());

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));
        cld.add(Calendar.MINUTE, 15);
        vnp_Params.put("vnp_ExpireDate", formatter.format(cld.getTime()));

     // ✅ Build query string & secure hash thống nhất
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder query = new StringBuilder();

        for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext();) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                     .append('=')
                     .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                }
            }
        }

        // ✅ Tạo chữ ký bằng chính hàm trong VnPayConfig
        String secureHash = VnPayConfig.hashAllFields(vnp_Params);
        query.append("&vnp_SecureHash=").append(secureHash);

        // ✅ Ghép URL
        String paymentUrl = VnPayConfig.getVnpUrl() + "?" + query;
        resp.sendRedirect(paymentUrl);

    }

    
}
