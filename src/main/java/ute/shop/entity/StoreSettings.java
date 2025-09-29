package ute.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "store_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // Tên cửa hàng: NVARCHAR(255)
    @Column(name = "store_name", nullable = false, length = 255, columnDefinition = "NVARCHAR(255)")
    private String storeName;

    // Email liên hệ: NVARCHAR(255)
    @Column(name = "email", length = 255, columnDefinition = "NVARCHAR(255)")
    private String email;

    // Hotline: NVARCHAR(50)
    @Column(name = "hotline", length = 50, columnDefinition = "NVARCHAR(50)")
    private String hotline;

    // Địa chỉ: NVARCHAR(500)
    @Column(name = "address", length = 500, columnDefinition = "NVARCHAR(500)")
    private String address;

    // Logo (đường dẫn): NVARCHAR(500)
    @Column(name = "logo", length = 500, columnDefinition = "NVARCHAR(500)")
    private String logo;

    // Theme (giao diện): NVARCHAR(50)
    @Column(name = "theme", length = 50, columnDefinition = "NVARCHAR(50) DEFAULT N'default'")
    private String theme;

    // Cho phép thanh toán
    @Column(name = "cod_enabled")
    private Boolean codEnabled;

    @Column(name = "momo_enabled")
    private Boolean momoEnabled;

    @Column(name = "vnpay_enabled")
    private Boolean vnpayEnabled;

    // Ngày tạo
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt = new Date();

    // Ngày cập nhật
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt = new Date();
}
