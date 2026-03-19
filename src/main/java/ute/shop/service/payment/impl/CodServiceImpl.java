package ute.shop.service.payment.impl;

import ute.shop.service.payment.IPaymentService;
import ute.shop.service.payment.PaymentContext;

public class CodServiceImpl implements IPaymentService {

    @Override
    public String createPaymentUrl(PaymentContext context) throws Exception {

        return context.getContextPath() + "/user/orders?order_status=success";
    }

}