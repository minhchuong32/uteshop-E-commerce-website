package ute.shop.service;

import ute.shop.models.*;

public interface IUserService {
	User login(String username, String password);
	User findByEmail(String email);
	void insert(User user);
	boolean register(String username, String password, String email, String role, String status);
	boolean checkExistEmail(String email);
	boolean checkExistUsername(String username);
	void updatePassword(String email, String newPassword);
}
