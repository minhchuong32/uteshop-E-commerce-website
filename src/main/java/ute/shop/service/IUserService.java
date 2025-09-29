package ute.shop.service;

import ute.shop.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    // Đăng nhập bằng email + mật khẩu
    User login(String email, String password);

    // Hash mật khẩu
    String hashPassword(String password);

    // Thêm user mới
    void insert(User user);

    // Đăng ký
    boolean register(String username, String password, String email, String role, String status);

    // Kiểm tra email tồn tại
    boolean checkExistEmail(String email);

    // Kiểm tra username tồn tại
    boolean checkExistUsername(String username);

    // Tìm user theo email
    Optional<User> findByEmail(String email);

    // Tìm user theo id
    Optional<User> getUserById(int id);

    // Update user
    boolean update(User user);

    // Xóa user
    void delete(int id);

    // Update password
    boolean updatePassword(String email, String newPassword);

    // Lấy tất cả users
    List<User> getAllUsers();

    // Cập nhật status
    void updateStatus(int id, String status);

    // Update user có thể đổi mật khẩu hoặc không
    boolean updatePwd(User user, boolean changePwd);
}
