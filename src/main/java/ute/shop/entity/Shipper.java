package ute.shop.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shippers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipper_id")
    private Integer shipperId;

    // Mỗi shipper gắn với 1 user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Tên shipper: NVARCHAR(100)
    @Column(name = "name", length = 100, columnDefinition = "NVARCHAR(100)", nullable = false)
    private String name;

    // Số điện thoại: VARCHAR(20)
    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    // Danh sách các đơn giao hàng của shipper này
    @OneToMany(mappedBy = "shipper", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Delivery> deliveries = new ArrayList<>();
}
