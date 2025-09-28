package ute.shop.service;

import java.util.List;

import ute.shop.models.User;

public interface IUserService {
	List<User> getAllUsers();

	User login(String username, String password);

	User findByEmail(String email);

	User getUserById(int id);

	void insert(User user);

	boolean update(User u);

	void delete(int id);

	boolean register(String username, String password, String email, String role, String status);

	boolean checkExistEmail(String email);

	boolean checkExistUsername(String username);

	boolean updatePassword(String email, String newPassword);

	void updateStatus(int id, String status);
	
	String hashPassword(String password);
	
	boolean UpdatePwd(User user, boolean pwd);
}
