package ute.shop.service.impl;

import ute.shop.dao.IUserDao;
import ute.shop.dao.impl.UserDaoImpl;
import ute.shop.entity.User;
import ute.shop.service.IUserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements IUserService {

    private final IUserDao userDao = new UserDaoImpl();

    @Override
    public User login(String email, String password) {
        Optional<User> userOpt = this.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String hashedInput = hashPassword(password);

            if (hashedInput.equals(user.getPassword())) {
                return user; // trả trực tiếp User
            }
        }
        return null; // nếu không login được
    }

    @Override
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi hash mật khẩu", e);
        }
    }

    @Override
    public void insert(User user) {
        user.setPassword(hashPassword(user.getPassword()));
        userDao.insert(user);
    }

    @Override
    public boolean register(String username, String password, String email, String role, String status) {
        if (checkExistEmail(email) || checkExistUsername(username)) {
            return false;
        }
        try {
            String hashedPassword = hashPassword(password);

            User user = new User();
            user.setEmail(email);
            user.setPassword(hashedPassword);
            user.setUsername(username);
            user.setRole(role);
            user.setStatus(status);

            userDao.insert(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.findByEmail(email).isPresent();
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.findByUsername(username).isPresent();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public boolean update(User user) {
        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            user.setPassword(hashPassword(user.getPassword()));
        }
        return userDao.update(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        return userDao.updatePassword(email, hashPassword(newPassword));
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void updateStatus(int id, String status) {
        userDao.updateStatus(id, status);
    }

    @Override
    public boolean updatePwd(User user, boolean changePwd) {
        if (changePwd) {
            user.setPassword(hashPassword(user.getPassword()));
        }
        return userDao.updatePwd(user, changePwd);
    }

	@Override
	public List<User> getUsersByRole(String role) {
		return userDao.getUsersByRole(role);
	}

	@Override
	public long countAllUsers() {
		return userDao.countAllUsers();
	}

	@Override
	public Optional<User> findByGoogleId(String googleId) {
		return userDao.findByGoogleId(googleId);
	}
}
