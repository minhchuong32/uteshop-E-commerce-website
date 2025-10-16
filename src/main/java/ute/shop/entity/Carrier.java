package ute.shop.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carriers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrier_id")
    private Integer carrierId;

    @Column(name = "carrier_name", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String carrierName;

    @Column(name = "carrier_fee", precision = 10, scale = 2)
    private BigDecimal carrierFee;

    @Column(name = "carrier_description", columnDefinition = "NVARCHAR(255)")
    private String carrierDescription;
}
