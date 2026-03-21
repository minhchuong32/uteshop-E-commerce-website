package ute.shop.adapter;

import java.math.BigDecimal;

public interface IPaymentGateway {
    /**
     * Tạo URL thanh toán từ thông tin đơn hàng chuẩn hóa.
     * Không quan tâm bên trong dùng VNPay hay MoMo.
     */
    String buildPaymentUrl(BigDecimal amount,
                           String orderRef,
                           String orderInfo,
                           String ipAddress);
}