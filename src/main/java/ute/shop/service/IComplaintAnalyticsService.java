package ute.shop.service;

import java.util.List;

public interface IComplaintAnalyticsService {
    long countAll();
    List<Object[]> countByStatus();
    List<Object[]> countByMonth();
    List<Object[]> topUsers(int limit);
}
