package ute.shop.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_variants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 100)
    private String optionName;

    @Column(length = 100, nullable = true)
    private String optionValue;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @Column(precision = 18, scale = 2) // có thể null
    private BigDecimal oldPrice;

    @Column(length = 255)
    private String imageUrl;
}
