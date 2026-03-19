package ute.shop.factory.impl;

import ute.shop.factory.PaymentAbstractFactory;
import ute.shop.factory.PaymentFactoryProducer;
import ute.shop.service.payment.IPaymentService;
import ute.shop.service.payment.impl.CodServiceImpl;

public class CodFactory implements PaymentAbstractFactory {

    static {
        PaymentFactoryProducer.register("COD", new CodFactory());
    }

    @Override
    public IPaymentService createPaymentService() {
        return new CodServiceImpl();
    }
}