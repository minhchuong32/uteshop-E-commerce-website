package ute.shop.service;

import ute.shop.entity.Contact;
import java.util.List;

public interface IContactService {
    List<Contact> findAll();
    Contact findById(int contactId);
    boolean delete(int contactId);
    boolean insert(Contact c);
}