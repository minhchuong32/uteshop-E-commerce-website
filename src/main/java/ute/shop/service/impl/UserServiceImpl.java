package ute.shop.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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

			// ⚡ DEBUG LOG (in ra console)
			System.out.println(">>> Email nhập: " + email);
			System.out.println(">>> Password nhập (plain): " + password);
			System.out.println(">>> Password nhập (hashed): " + hashedInput);
			System.out.println(">>> Password DB (hashed): " + user.getPassword());

			// So sánh với mật khẩu trong DB
			if (hashedInput.equals(user.getPassword())) {
				System.out.println(">>> ✅ Login thành công!");
				return user;
			} else {
				System.out.println(">>> ❌ Sai mật khẩu!");
			}
		} else {
			System.out.println(">>> ⚠️ Không tìm thấy user với email: " + email);
		}
		return null;
	}
	
	@Override
	// Hàm hash SHA-256 (nếu muốn bảo mật tốt hơn thì nên thay bằng BCrypt)
	public String hashPassword(String password) {
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
	public boolean updatePassword(String email, String newPassword) {
		// Hash trước khi update
		String hashedPassword = hashPassword(newPassword);
		return userDao.updatePassword(email, hashedPassword);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = userDao.findAll();
		return users;
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return userDao.getUserById(id);
	}

	@Override
	public boolean update(User user) {
		if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
			user.setPassword(hashPassword(user.getPassword()));
		}
		return userDao.Update(user);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		userDao.Delete(id);
	}

	@Override
	public void updateStatus(int id, String status) {
		// TODO Auto-generated method stub
		userDao.updateStatus(id, status);
	}


	@Override
	public boolean UpdatePwd(User user, boolean changePwd) {
		// Nếu có đổi mật khẩu thì hash
	    if (changePwd) {
	        user.setPassword(hashPassword(user.getPassword()));
	    }
	    return userDao.UpdatePwd(user, changePwd);
	}
}
