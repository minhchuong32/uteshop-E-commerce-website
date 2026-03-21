package ute.shop.service.payment.impl;

import ute.shop.adapter.IPaymentGateway;
import ute.shop.adapter.VnPayAdapter;
import ute.shop.service.payment.IPaymentService;
import ute.shop.service.payment.PaymentContext;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class VnPayServiceImpl implements IPaymentService {

    // Dùng interface chung, không gọi thẳng VnPayConfig
    private final IPaymentGateway gateway = new VnPayAdapter();

    @Override
    public String createPaymentUrl(PaymentContext context) throws Exception {
        // Chỉ truyền dữ liệu chuẩn hóa, không biết VNPay hoạt động ra sao
        String rawUrl = gateway.buildPaymentUrl(
            context.getTotal(),
            context.getOrderIds(),
            "Thanh toan don hang " + context.getOrderIds(),
            "127.0.0.1"   // IP có thể truyền vào context nếu cần
        );

        // Trả về path để controller redirect
        return context.getContextPath() + "/user/payment/vnpay-redirect?url="
               + URLEncoder.encode(rawUrl, StandardCharsets.UTF_8);
    }
}