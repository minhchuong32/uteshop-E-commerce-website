package ute.shop.service.impl;

import ute.shop.dao.impl.RevenueDaoImpl;
import ute.shop.service.IRevenueService;

import java.math.BigDecimal;
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
