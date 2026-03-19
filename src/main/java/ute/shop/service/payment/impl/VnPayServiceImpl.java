package ute.shop.service.payment.impl;

import ute.shop.service.payment.PaymentContext;
import ute.shop.service.payment.IPaymentService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class VnPayServiceImpl implements IPaymentService {

	 @Override
	    public String createPaymentUrl(PaymentContext context) throws Exception {

	        return context.getContextPath() + "/user/payment/vnpay"
	                + "?paymentTotal=" + context.getTotal()
	                + "&orderIds=" + URLEncoder.encode(context.getOrderIds(), StandardCharsets.UTF_8)
	                + "&shopNames=" + URLEncoder.encode(context.getShopNames(), StandardCharsets.UTF_8);
	    }
}