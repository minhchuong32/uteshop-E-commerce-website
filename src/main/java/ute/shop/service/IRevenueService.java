package ute.shop.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IRevenueService {
    BigDecimal getTotalRevenueAfterFee();
    BigDecimal getTotalPlatformFee();
    List<Object[]> getRevenueByMonth();

    List<Object[]> getRevenueByFilter(Date startDate, Date endDate, Integer shopId);
    String analyzeGrowthAndForecast(List<Object[]> monthlyData);

    BigDecimal getTotalRevenue(int shopId);
    List<Object[]> getRevenueByMonthByShop(int shopId);

}
	