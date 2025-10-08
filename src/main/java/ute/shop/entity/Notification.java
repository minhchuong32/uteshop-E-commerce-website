package ute.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "relatedComplaint"})
@EqualsAndHashCode(exclude = {"user", "relatedComplaint"})
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // Người nhận thông báo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_complaint_id", nullable = true)
    private Complaint relatedComplaint; // ✅ Liên kết đến khiếu nại

    @Column(name = "title", columnDefinition = "NVARCHAR(255)")
    private String title;

    @Column(name = "message", columnDefinition = "NVARCHAR(255)")
    private String message;

    @Builder.Default
    @Column(name = "is_read")
    private boolean read = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    @Column(name = "created_at")
    private Date createdAt = new Date();
}
