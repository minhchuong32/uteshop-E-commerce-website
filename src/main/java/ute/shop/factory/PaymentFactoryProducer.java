package ute.shop.factory;
import java.util.HashMap;
import java.util.Map;

public class PaymentFactoryProducer {

    private static final Map<String, PaymentAbstractFactory> factories = new HashMap<>();

    public static void register(String type, PaymentAbstractFactory factory) {
        factories.put(type.toUpperCase(), factory);
    }

    public static PaymentAbstractFactory getFactory(String type) {
        return factories.get(type.toUpperCase());
    }
}