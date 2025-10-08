package ute.shop.dao;

import java.math.BigDecimal;
import java.util.List;

public interface IRevenueDao {
    BigDecimal getTotalRevenue(BigDecimal platformFeeRate);
    BigDecimal getTotalPlatformFee(BigDecimal platformFeeRate);
    List<Object[]> getRevenueByMonth(); 
    BigDecimal getTotalRevenueByShop(int shopId);
    List<Object[]> getRevenueByMonthByShop(int shopId);
}
