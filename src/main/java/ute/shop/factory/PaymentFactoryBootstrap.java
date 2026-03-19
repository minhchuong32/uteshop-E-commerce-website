package ute.shop.factory;

import ute.shop.factory.impl.VnPayFactory;
import ute.shop.factory.impl.CodFactory;
import ute.shop.factory.impl.MomoFactory;

public class PaymentFactoryBootstrap {

    static {
        try {
            Class.forName(VnPayFactory.class.getName());
            Class.forName(CodFactory.class.getName());
            Class.forName(MomoFactory.class.getName());
            //
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}