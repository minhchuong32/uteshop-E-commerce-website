package ute.shop.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ute.shop.models.User;
import ute.shop.dao.IUserDao;
import ute.shop.dao.impl.UserDaoImpl;
import ute.shop.service.IUserService;

public class UserServiceImpl implements IUserService {

	private final IUserDao userDao = new UserDaoImpl();

	@Override
	public User login(String email, String password) {
		User user = this.findByEmail(email);

		if (user != null) {
			// Hash mật khẩu người dùng nhập
			String hashedInput = hashPassword(password);

			// So sánh với mật khẩu trong DB
			if (hashedInput.equals(user.getPassword())) {
				return user;
			}
		}
		return null;
	}

	// Hàm hash SHA-256 (nếu muốn bảo mật tốt hơn thì nên thay bằng BCrypt)
	private String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(password.getBytes());

			// Convert byte[] -> hex string
			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02X", b)); // viết hoa hex
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Lỗi hash mật khẩu", e);
		}
	}

	@Override
	public void insert(User user) {
		// Hash password trước khi lưu
		user.setPassword(hashPassword(user.getPassword()));
		userDao.Insert(user);
	}

	@Override
	public boolean register(String username, String password, String email, String role, String status) {
		// Kiểm tra email và username có tồn tại không
		if (checkExistEmail(email) || checkExistUsername(username)) {
			return false; // không đăng ký được
		}

		try {
			// Hash password
			String hashedPassword = hashPassword(password);

			// Tạo đối tượng User mới
			User user = new User();
			user.setEmail(email);
			user.setPassword(hashedPassword);
			user.setUsername(username);
			user.setRole(role);
			user.setStatus(status);

			// Lưu vào DB
			userDao.Insert(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean checkExistEmail(String email) {
		return userDao.findByEmail(email) != null;
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public boolean checkExistUsername(String username) {
		return userDao.findByUsername(username) != null;
	}

	@Override
	public void updatePassword(String email, String newPassword) {
		// Hash trước khi update
		String hashedPassword = hashPassword(newPassword);
		userDao.updatePassword(email, hashedPassword);
	}
}
