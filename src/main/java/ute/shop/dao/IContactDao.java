package ute.shop.dao;

import ute.shop.entity.Contact;
import java.util.List;

public interface IContactDao {
	 boolean insert(Contact c);
	 List<Contact> findAll();
	 Contact findById(int contactId);
	 boolean delete(int contactId);
}