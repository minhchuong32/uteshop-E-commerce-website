package ute.shop.dao;

import java.util.List;
import ute.shop.models.User;

public interface IUserDao {
	List<User> findAll();
	
	void Insert(User user); 
	
	void Update(User user);
	
	void Delete(int id);
	
	User findByEmail(String email);
	
	User findByUsername(String username);

	User getUserById(int id);
	
	void updatePassword(String email, String newPassword);
	
	void updateStatus(int id, String status);
}
