package ute.shop.service;

import ute.shop.entity.Complaint;
import java.util.List;

public interface IComplaintService {
    List<Complaint> findAll();
    Complaint findById(int id);
    void insert(Complaint c);
    void update(Complaint c);
    void delete(int id);
    List<Complaint> findByUserId(int userId);
	long countAll();
}
