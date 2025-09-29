package ute.shop.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    // Mỗi sản phẩm thuộc 1 shop
    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    // Mỗi sản phẩm thuộc 1 category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Tên sản phẩm: NVARCHAR(100) để lưu tiếng Việt
    @Column(name = "name", nullable = false, length = 100, columnDefinition = "NVARCHAR(100)")
    private String name;

    // Giá: DECIMAL(18,2)
    @Column(name = "price", precision = 18, scale = 2, nullable = false)
    private BigDecimal price;

    // Số lượng tồn kho
    @Column(name = "stock", nullable = false)
    private Integer stock;

    // Mô tả: NVARCHAR(MAX)
    @Lob
    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    // Ảnh: NVARCHAR(255)
    @Column(name = "image_url", length = 255, columnDefinition = "NVARCHAR(255)")
    private String imageUrl;

    // Liên kết với chi tiết đơn hàng
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    // Liên kết với đánh giá
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
}
