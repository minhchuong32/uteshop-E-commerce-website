package ute.shop.dao;

import ute.shop.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserDao {

    // Lấy tất cả User
    List<User> findAll();

    // Thêm mới User
    void insert(User user);

    // Cập nhật User (return true nếu thành công)
    boolean update(User user);

    // Xóa User theo ID
    void delete(int id);

    // Tìm User theo email
    Optional<User> findByEmail(String email);

    // Tìm User theo username
    Optional<User> findByUsername(String username);

    // Lấy User theo ID
    Optional<User> getUserById(int id);

    // Cập nhật password theo email
    boolean updatePassword(String email, String newPassword);

    // Cập nhật status theo ID
    void updateStatus(int id, String status);

    // Cập nhật User, tùy chọn có đổi password hay không
    boolean updatePwd(User user, boolean changePwd);
}
