package ute.shop.service.payment;


public interface IPaymentService {

    String createPaymentUrl(PaymentContext context) throws Exception;

}