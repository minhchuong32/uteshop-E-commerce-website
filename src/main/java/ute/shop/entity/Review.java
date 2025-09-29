package ute.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    // Mỗi review gắn với một sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Mỗi review do một user viết
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Điểm đánh giá 1–5
    @Column(name = "rating", nullable = false)
    private Integer rating;

    // Nội dung đánh giá: NVARCHAR(MAX)
    @Lob
    @Column(name = "comment", columnDefinition = "NVARCHAR(MAX)")
    private String comment;

    // Link hình ảnh/video minh họa: NVARCHAR(255)
    @Column(name = "media_url", length = 255, columnDefinition = "NVARCHAR(255)")
    private String mediaUrl;
}
