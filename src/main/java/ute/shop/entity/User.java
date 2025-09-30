package ute.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    // Tên đăng nhập: NVARCHAR(50)
    @Column(name = "username", nullable = false, unique = true, length = 50, columnDefinition = "NVARCHAR(50)")
    private String username;

    // Mật khẩu (hash): NVARCHAR(255)
    @Column(name = "password", nullable = false, length = 255, columnDefinition = "NVARCHAR(255)")
    private String password;

    // Email: NVARCHAR(100)
    @Column(name = "email", nullable = false, unique = true, length = 100, columnDefinition = "NVARCHAR(100)")
    private String email;

    // Vai trò: Guest, User, Vendor, Admin, Shipper
    @Column(name = "role", length = 20, columnDefinition = "NVARCHAR(20) DEFAULT N'User'")
    private String role;

    // Trạng thái: active, inactive, banned
    @Column(name = "status", length = 20, columnDefinition = "NVARCHAR(20) DEFAULT N'active'")
    private String status;
    
    // Đường dẫn ảnh đại diện
    @Column(name = "avatar", length = 500, columnDefinition = "NVARCHAR(500)")
    private String avatar;
    
    // Tên hiển thị / tên thật
    @Column(name = "name", length = 100, columnDefinition = "NVARCHAR(100)")
    private String name;

    // Số điện thoại
    @Column(name = "phone", length = 20)
    private String phone;

    // Địa chỉ
    @Column(name = "address", length = 255, columnDefinition = "NVARCHAR(255)")
    private String address;

    // ========== Quan hệ ==========
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shop> shops = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();
}
