package ute.shop.dao;

import java.util.List;

import ute.shop.entity.ComplaintMessage;

public interface IComplaintMessageDao {
	List<ComplaintMessage> findByComplaintId(int complaintId);

	void insert(ComplaintMessage message);
}
