package ute.shop.service;

import java.math.BigDecimal;
import java.util.List;

public interface IRevenueService {
    BigDecimal getTotalRevenueAfterFee();
    BigDecimal getTotalPlatformFee();
    List<Object[]> getRevenueByMonth();
}
	