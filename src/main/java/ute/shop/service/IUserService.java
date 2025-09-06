package ute.shop.service;

import java.util.List;

import ute.shop.models.*;

public interface IUserService {
	List<User> getAllUsers();

	User login(String username, String password);

	User findByEmail(String email);

	User getUserById(int id);

	void insert(User user);

	void update(User u);

	void delete(int id);

	boolean register(String username, String password, String email, String role, String status);

	boolean checkExistEmail(String email);

	boolean checkExistUsername(String username);

	void updatePassword(String email, String newPassword);

	void updateStatus(int id, String status);
}
