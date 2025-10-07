package ute.shop.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CartItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Integer cartItemId;

    // Mỗi cart item thuộc về 1 user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Mỗi cart item gắn với 1 variant của sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    // Số lượng
    @Column(nullable = false)
    private int quantity;

    // Giá tại thời điểm thêm vào giỏ (để tránh thay đổi giá sau này)
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;
}
