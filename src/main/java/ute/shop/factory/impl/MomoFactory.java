package ute.shop.factory.impl;

import ute.shop.factory.PaymentAbstractFactory;
import ute.shop.factory.PaymentFactoryProducer;
import ute.shop.service.payment.IPaymentService;
import ute.shop.service.payment.impl.MomoServiceImpl;


public class MomoFactory implements PaymentAbstractFactory {

    static {
        PaymentFactoryProducer.register("Momo", new MomoFactory());
    }

    @Override
    public IPaymentService createPaymentService() {
        return new MomoServiceImpl();
    }
}