package ute.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    // Tên danh mục: NVARCHAR(100) để lưu tiếng Việt có dấu
    @Column(name = "name", nullable = false, length = 100, columnDefinition = "NVARCHAR(100)")
    private String name;

    // Category.java
    @Column(name = "image", length = 255, columnDefinition = "NVARCHAR(255)")
    private String image;

    // Danh sách sản phẩm thuộc danh mục
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();
}
