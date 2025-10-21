package ute.shop.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shipping_addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddress {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    // Mỗi địa chỉ thuộc về 1 user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "recipient_name", nullable = false, length = 100, columnDefinition = "NVARCHAR(100)")
    private String recipientName;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "address_line", nullable = false, length = 255, columnDefinition = "NVARCHAR(255)")
    private String addressLine;

    @Column(name = "ward", length = 100, columnDefinition = "NVARCHAR(100)")
    private String ward;

    @Column(name = "district", length = 100, columnDefinition = "NVARCHAR(100)")
    private String district;

    @Column(name = "city", length = 100, columnDefinition = "NVARCHAR(100)")
    private String city;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = true)
    private Date createdAt = new Date();
}
