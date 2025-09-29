package ute.shop.service.impl;

import ute.shop.dao.IContactDao;
import ute.shop.dao.impl.ContactDaoImpl;
import ute.shop.entity.Contact;
import ute.shop.service.IContactService;

public class ContactServiceImpl implements IContactService {
    private final IContactDao dao = new ContactDaoImpl();

    @Override
    public boolean insert(Contact c) {
        return dao.insert(c);
    }
}
