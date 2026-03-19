package ute.shop.service.payment;

import java.math.BigDecimal;

public class PaymentContext {

    private BigDecimal total;
    private String orderIds;
    private String shopNames;
    private String contextPath;

    public PaymentContext(BigDecimal total, String orderIds, String shopNames, String contextPath) {
        this.total = total;
        this.orderIds = orderIds;
        this.shopNames = shopNames;
        this.contextPath = contextPath;
    }

    public BigDecimal getTotal() { return total; }
    public String getOrderIds() { return orderIds; }
    public String getShopNames() { return shopNames; }
    public String getContextPath() { return contextPath; }
}