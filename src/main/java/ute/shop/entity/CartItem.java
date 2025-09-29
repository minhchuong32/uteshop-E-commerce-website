package ute.shop.entity;

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

    // Mỗi cart item thuộc về 1 sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Số lượng
    @Column(nullable = false)
    private int quantity;
}
