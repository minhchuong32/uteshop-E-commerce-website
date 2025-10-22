package ute.shop.service.impl;

import ute.shop.dao.impl.ComplaintMessageDaoImpl;
import ute.shop.entity.ComplaintMessage;
import ute.shop.service.IComplaintMessageService;

import java.util.List;

public class ComplaintMessageServiceImpl implements IComplaintMessageService {

	private final ComplaintMessageDaoImpl dao = new ComplaintMessageDaoImpl();

	public List<ComplaintMessage> findByComplaintId(int complaintId) {
		return dao.findByComplaintId(complaintId);
	}

	public ComplaintMessage insert(ComplaintMessage msg) {
		return dao.insert(msg);
	}
}
