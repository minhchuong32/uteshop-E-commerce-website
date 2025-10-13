package ute.shop.service.impl;

import ute.shop.dao.impl.ComplaintDaoImpl;
import ute.shop.entity.Complaint;
import ute.shop.service.IComplaintService;
import java.util.List;

public class ComplaintServiceImpl implements IComplaintService {

    private final ComplaintDaoImpl dao = new ComplaintDaoImpl();

    @Override
    public List<Complaint> findAll() {
        return dao.findAll();
    }

    @Override
    public Complaint findById(int id) {
        return dao.findById(id);
    }

    @Override
    public void insert(Complaint c) {
        dao.insert(c);
    }

    @Override
    public void update(Complaint c) {
        dao.update(c);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
    
    @Override
    public List<Complaint> findByUserId(int userId) {
        return dao.findByUserId(userId);
    }

	@Override
	public long countAll() {
		return dao.countAll();
	}

}
