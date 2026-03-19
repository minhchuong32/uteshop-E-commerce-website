package ute.shop.service.payment.impl;

import ute.shop.service.payment.IPaymentService;
import ute.shop.service.payment.PaymentContext;

public class MomoServiceImpl implements IPaymentService {

    @Override
    public String createPaymentUrl(PaymentContext context) throws Exception {

       return context.getContextPath() + "/user/payment/momo"
                + "?paymentTotal=" + context.getTotal()
                + "&orderIds=" + context.getOrderIds()
                + "&shopNames=" + context.getShopNames();
    }
}