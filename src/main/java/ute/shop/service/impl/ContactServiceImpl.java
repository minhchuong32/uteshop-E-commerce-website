package ute.shop.service.impl;

import ute.shop.dao.impl.ContactDaoImpl;
import ute.shop.models.Contact;
import ute.shop.service.IContactService;

public class ContactServiceImpl implements IContactService {
    private ContactDaoImpl dao = new ContactDaoImpl();

    public boolean insert(Contact c) {
        return dao.insert(c);
    }
}
