package ute.shop.entity;

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

    // Mỗi đơn giao hàng gắn với 1 shipper
    @ManyToOne
    @JoinColumn(name = "shipper_id", nullable = false)
    private Shipper shipper;

    // Mỗi đơn giao hàng gắn với 1 order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Trạng thái giao hàng: NVARCHAR(20) (assigned, delivering, delivered)
    @Column(name = "status", length = 20, columnDefinition = "NVARCHAR(20)")
    private String status;
}
