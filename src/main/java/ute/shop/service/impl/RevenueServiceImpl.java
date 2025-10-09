package ute.shop.service.impl;

import ute.shop.dao.impl.RevenueDaoImpl;
import ute.shop.service.IRevenueService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

public class RevenueServiceImpl implements IRevenueService {

    private final RevenueDaoImpl dao = new RevenueDaoImpl();

    // Giữ bản cũ nhưng thêm overload nhận tham số động
    public BigDecimal getTotalRevenueAfterFee(BigDecimal platformFeeRate) {
        return dao.getTotalRevenue(platformFeeRate);
    }

    public BigDecimal getTotalPlatformFee(BigDecimal platformFeeRate) {
        return dao.getTotalPlatformFee(platformFeeRate);
    }

    @Override
    public BigDecimal getTotalRevenueAfterFee() {
        return getTotalRevenueAfterFee(new BigDecimal("0.10"));
    }

    @Override
    public BigDecimal getTotalPlatformFee() {
        return getTotalPlatformFee(new BigDecimal("0.10"));
    }

    @Override
    public List<Object[]> getRevenueByMonth() {
        return dao.getRevenueByMonth();
    }
    

    @Override
    public List<Object[]> getRevenueByFilter(Date startDate, Date endDate, Integer shopId) {
        return dao.getRevenueByFilter(startDate, endDate, shopId);
    }
    
    @Override
    public String analyzeGrowthAndForecast(List<Object[]> monthlyData) {
        if (monthlyData == null || monthlyData.isEmpty()) return "Chưa có dữ liệu doanh thu.";

        int n = monthlyData.size();
        BigDecimal lastMonth = (BigDecimal) monthlyData.get(n - 1)[1];
        BigDecimal prevMonth = n > 1 ? (BigDecimal) monthlyData.get(n - 2)[1] : BigDecimal.ZERO;

        BigDecimal growthRate = BigDecimal.ZERO;
        if (prevMonth.compareTo(BigDecimal.ZERO) > 0) {
            growthRate = lastMonth.subtract(prevMonth)
                    .divide(prevMonth, 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        // Dự báo đơn giản: trung bình 3 tháng gần nhất
        BigDecimal avg3 = BigDecimal.ZERO;
        if (n >= 3) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int i = n - 3; i < n; i++) {
                sum = sum.add((BigDecimal) monthlyData.get(i)[1]);
            }
            avg3 = sum.divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP);
        } else {
            avg3 = lastMonth;
        }

        return String.format(
            "Doanh thu tháng %d đạt %,.0f₫, tăng trưởng %.2f%% so với tháng trước. " +
            "Dự báo tháng kế tiếp đạt khoảng %,.0f₫ nếu xu hướng hiện tại tiếp tục.",
            ((Number) monthlyData.get(n - 1)[0]).intValue(),
            lastMonth,
            growthRate,
            avg3
        );
    }


    //Vendor DashBoard
    @Override
    public BigDecimal getTotalRevenue(int shopId) {
    	return dao.getTotalRevenueByShop(shopId);
    }

	@Override
	public List<Object[]> getRevenueByMonthByShop(int shopId) {
		return dao.getRevenueByMonthByShop(shopId);
	}

}
