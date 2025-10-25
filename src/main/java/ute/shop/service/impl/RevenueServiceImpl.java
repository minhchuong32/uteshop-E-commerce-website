package ute.shop.service.impl;

import ute.shop.dao.IRevenueDao;
import ute.shop.dao.impl.RevenueDaoImpl;
import ute.shop.service.IRevenueService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

public class RevenueServiceImpl implements IRevenueService {

	private final IRevenueDao dao = new RevenueDaoImpl();

	// Tính tổng doanh thu từ tất cả các đơn hàng đã giao
	// Nếu tổng doanh thu là 100,000,000 và platformFeeRate = 0.1 (10%),
	// → kết quả trả về = 100,000,000 - (100,000,000 * 0.1) = 90,000,000.
	public BigDecimal getTotalRevenueAfterFee(BigDecimal platformFeeRate) {
		return dao.getTotalRevenue(platformFeeRate);
	}

	// Tính phí nền tảng thu được (phần trăm doanh thu của người bán trả cho sàn):
	// totalAmount * platformFeeRate
	public BigDecimal getTotalPlatformFee(BigDecimal platformFeeRate) {
		return dao.getTotalPlatformFee(platformFeeRate);
	}

	// Lấy doanh thu theo từng tháng trong năm hiện tại.
	@Override
	public List<Object[]> getRevenueByMonth() {
		return dao.getRevenueByMonth();
	}

	// Lấy doanh thu trong khoảng thời gian hoặc theo cửa hàng cụ thể.
	@Override
	public List<Object[]> getRevenueByFilter(Date startDate, Date endDate, Integer shopId) {
		return dao.getRevenueByFilter(startDate, endDate, shopId);
	}

	// Object[]{ month, revenue }, ví dụ: {5, 12000000}
	@Override
	public String analyzeGrowthAndForecast(List<Object[]> monthlyData) {
		if (monthlyData == null || monthlyData.isEmpty())
			return "Chưa có dữ liệu doanh thu.";

		int n = monthlyData.size();
		BigDecimal lastMonth = (BigDecimal) monthlyData.get(n - 1)[1];
		BigDecimal prevMonth = n > 1 ? (BigDecimal) monthlyData.get(n - 2)[1] : BigDecimal.ZERO;

		BigDecimal growthRate = BigDecimal.ZERO;
		//		 % tăng trưởng = [(doanh thu lastMonth - prevMonth) / prevMonth] * 100 
		if (prevMonth.compareTo(BigDecimal.ZERO) > 0) {
			growthRate = lastMonth.subtract(prevMonth).divide(prevMonth, 2, RoundingMode.HALF_UP)
					.multiply(BigDecimal.valueOf(100));
		}

		// Dự báo = trung bình 3 tháng gần nhất
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
				"Doanh thu tháng %d đạt %,.0f₫, tăng trưởng %.2f%% so với tháng trước. "
						+ "Dự báo tháng kế tiếp đạt khoảng %,.0f₫ nếu xu hướng hiện tại tiếp tục.",
				((Number) monthlyData.get(n - 1)[0]).intValue(), lastMonth, growthRate, avg3);
	}

	// Vendor DashBoard
	// Tính doanh thu riêng của từng shop dựa trên chi tiết đơn hàng (OrderDetail).
	@Override
	public BigDecimal getTotalRevenue(int shopId) {
		return dao.getTotalRevenueByShop(shopId);
	}

	// Trả về danh sách doanh thu theo tháng cho shop cụ thể
	@Override
	public List<Object[]> getRevenueByMonthByShop(int shopId) {
		return dao.getRevenueByMonthByShop(shopId);
	}
	
	

}
