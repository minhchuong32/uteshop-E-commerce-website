package ute.shop.factory;

import ute.shop.service.payment.IPaymentService;

public interface PaymentAbstractFactory {

    IPaymentService createPaymentService();

}