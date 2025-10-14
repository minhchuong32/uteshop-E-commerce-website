package ute.shop.service;

import java.util.List;

import ute.shop.entity.ComplaintMessage;

public interface IComplaintMessageService {
	List<ComplaintMessage> findByComplaintId(int complaintId);

	void insert(ComplaintMessage message);
}
