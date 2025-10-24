package ute.shop.service.impl;
import ute.shop.dao.IContactDao;
import ute.shop.dao.impl.ContactDaoImpl;
import ute.shop.entity.Contact;
import ute.shop.service.IContactService;

import java.util.List;

public class ContactServiceImpl implements IContactService {

    private final IContactDao contactDao = new ContactDaoImpl();

    @Override
    public List<Contact> findAll() {
        return contactDao.findAll();
    }

    @Override
    public Contact findById(int contactId) {
        return contactDao.findById(contactId);
    }

    @Override
    public boolean delete(int contactId) {
        return contactDao.delete(contactId);
    }

	@Override
	public boolean insert(Contact c) {
		return contactDao.insert(c);
	}
}