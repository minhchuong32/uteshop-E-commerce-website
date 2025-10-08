package ute.shop.service.impl;

import ute.shop.dao.IComplaintAnalyticsDao;
import ute.shop.dao.impl.ComplaintAnalyticsDaoImpl;
import ute.shop.service.IComplaintAnalyticsService;

import java.util.List;

public class ComplaintAnalyticsServiceImpl implements IComplaintAnalyticsService {

    private final IComplaintAnalyticsDao dao = new ComplaintAnalyticsDaoImpl();

    @Override
    public long countAll() {
        return dao.countAll();
    }

    @Override
    public List<Object[]> countByStatus() {
        return dao.countByStatus();
    }

    @Override
    public List<Object[]> countByMonth() {
        return dao.countByMonth();
    }

    @Override
    public List<Object[]> topUsers(int limit) {
        return dao.topUsers(limit);
    }
}
