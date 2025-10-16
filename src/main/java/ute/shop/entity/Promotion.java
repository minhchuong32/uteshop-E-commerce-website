package ute.shop.entity;

import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "promotions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Integer promotionId;

    // Mỗi khuyến mãi thuộc về 1 shop
    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    // Mỗi khuyến mãi có thể áp dụng cho 1 sản phẩm (tùy chọn)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @Column(name = "discount_type", length = 20, columnDefinition = "NVARCHAR(20)", nullable = false)
    private String discountType;

    @Column(name = "value", precision = 18, scale = 2, nullable = false)
    private BigDecimal value;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;
}
