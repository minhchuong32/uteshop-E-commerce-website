package ute.shop.dao;

import java.util.List;
import ute.shop.models.User;

public interface IUserDao {
	List<User> findAll();
	
	void Insert(User user); 
	
	boolean Update(User user);
	
	void Delete(int id);
	
	User findByEmail(String email);
	
	User findByUsername(String username);

	User getUserById(int id);
	
	boolean updatePassword(String email, String newPassword);
	
	void updateStatus(int id, String status);
	
	boolean UpdatePwd(User user, boolean changePwd);
}
