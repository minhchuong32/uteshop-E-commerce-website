package ute.shop.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IRevenueDao {
    BigDecimal getTotalRevenue(BigDecimal platformFeeRate);
    BigDecimal getTotalPlatformFee(BigDecimal platformFeeRate);
    List<Object[]> getRevenueByMonth(); 
<<<<<<< HEAD
    List<Object[]> getRevenueByFilter(Date startDate, Date endDate, Integer shopId);
=======
    BigDecimal getTotalRevenueByShop(int shopId);
    List<Object[]> getRevenueByMonthByShop(int shopId);
>>>>>>> 8ea3bf959a76adc8b733df8eb3621fbf2ec65abe
}
