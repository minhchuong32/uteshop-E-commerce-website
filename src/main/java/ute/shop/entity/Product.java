package ute.shop.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"shop", "category", "variants", "orderDetails", "reviews", "images"})
@Entity
@Table(name = "products")
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

    // Tên sản phẩm
    @Column(name = "name", nullable = false, length = 100, columnDefinition = "NVARCHAR(100)")
    private String name;

    // Mô tả chung
    @Lob
    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    // Ảnh đại diện
    @Column(name = "image_url", length = 255, columnDefinition = "NVARCHAR(255)")
    private String imageUrl;

    // Liên kết với các variant
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductVariant> variants = new ArrayList<>();

    @Transient
    private BigDecimal price;


//    // Liên kết với chi tiết đơn hàng (nếu chưa tách theo variant)
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderDetail> orderDetails = new ArrayList<>();

    // Liên kết với đánh giá
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    // Danh sách ảnh liên quan
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    // ====================== Trường tạm tính toán ======================
    @Transient
    private int reviewsCount;

    @Transient
    private double averageRating;

    @PostLoad
    public void calculateReviews() {
        if (reviews == null || reviews.isEmpty()) {
            this.reviewsCount = 0;
            this.averageRating = 0.0;
        } else {
            this.reviewsCount = reviews.size();
            this.averageRating = reviews.stream()
                    .filter(r -> r.getRating() != null)
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
        }
    }
}
