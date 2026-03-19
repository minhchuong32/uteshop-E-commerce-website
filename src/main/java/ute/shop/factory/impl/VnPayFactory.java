package ute.shop.factory.impl;

import ute.shop.factory.PaymentAbstractFactory;
import ute.shop.factory.PaymentFactoryProducer;
import ute.shop.service.payment.IPaymentService;
import ute.shop.service.payment.impl.VnPayServiceImpl;

public class VnPayFactory implements PaymentAbstractFactory {

    static {
        PaymentFactoryProducer.register("VNPAY", new VnPayFactory());
    }

    @Override
    public IPaymentService createPaymentService() {
        return new VnPayServiceImpl();
    }
}