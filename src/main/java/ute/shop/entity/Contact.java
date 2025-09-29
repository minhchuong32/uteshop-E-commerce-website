package ute.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Table(name = "contact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contactId;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(nullable = false, length = 100, columnDefinition = "NVARCHAR(100)")
    private String fullName;

    @Column(nullable = false, length = 100, columnDefinition = "NVARCHAR(100)")
    private String email;

    @Lob
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
}
