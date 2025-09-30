package ute.shop.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deliveries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Integer deliveryId;

    // Mỗi đơn giao hàng gắn với 1 user có role = 'Shipper'
    @ManyToOne
    @JoinColumn(name = "shipper_id", nullable = false)
    private User shipper;

    // Mỗi đơn giao hàng gắn với 1 order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Trạng thái giao hàng: NVARCHAR(20) (assigned, delivering, delivered, canceled, returned)
    @Column(name = "status", length = 20, columnDefinition = "NVARCHAR(20) DEFAULT N'Đã gán'")
    private String status;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt = new Date();  // thêm field này
}
