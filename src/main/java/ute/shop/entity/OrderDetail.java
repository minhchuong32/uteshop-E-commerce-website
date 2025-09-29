package ute.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Integer orderDetailId;

    // Mỗi dòng chi tiết thuộc về 1 order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Mỗi dòng chi tiết gắn với 1 sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Số lượng
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // Giá tại thời điểm đặt: DECIMAL(18,2)
    @Column(name = "price", precision = 18, scale = 2, nullable = false)
    private BigDecimal price;
}
