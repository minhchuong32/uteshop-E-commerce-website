package ute.shop.service.impl;

import ute.shop.dao.impl.ComplaintMessageDaoImpl;
import ute.shop.entity.ComplaintMessage;
import java.util.List;

public class ComplaintMessageServiceImpl {

    private final ComplaintMessageDaoImpl dao = new ComplaintMessageDaoImpl();

    public List<ComplaintMessage> findByComplaintId(int complaintId) {
        return dao.findByComplaintId(complaintId);
    }
    


    public void insert(ComplaintMessage msg) {
        dao.insert(msg);
    }
}
