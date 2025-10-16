package ute.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    // Mỗi đơn hàng thuộc về 1 user (khách hàng)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Tổng tiền
    @Column(name = "total_amount", precision = 18, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    // Trạng thái đơn hàng
    @Column(name = "status", length = 20, columnDefinition = "NVARCHAR(20) DEFAULT N'new'")
    private String status;

    // Phương thức thanh toán
    @Column(name = "payment_method", length = 20, columnDefinition = "NVARCHAR(20) DEFAULT N'COD'")
    private String paymentMethod;

    // Ngày tạo
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt = new Date();

    // Địa chỉ giao hàng
    @Column(name = "address", length = 255, columnDefinition = "NVARCHAR(255)")
    private String address;

    // Chi tiết đơn hàng
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails = new ArrayList<>();


    // Giao hàng (lịch sử)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Delivery> deliveries = new ArrayList<>();
    
    // Mỗi đơn hàng thuộc về 1 shop
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;


}
