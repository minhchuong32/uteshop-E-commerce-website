package ute.shop.dao;

import java.util.List;

public interface IComplaintAnalyticsDao {
    long countAll();
    List<Object[]> countByStatus();
    List<Object[]> countByMonth();
    List<Object[]> topUsers(int limit);
}
