package ute.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "complaints")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"order", "user"})
@EqualsAndHashCode(exclude = {"order", "user"})
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private int complaintId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // Đơn hàng bị khiếu nại

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // Người gửi khiếu nại

    @Column(name = "title", columnDefinition = "NVARCHAR(255)")
    private String title; // Tiêu đề khiếu nại

    @Column(name = "content", columnDefinition = "NVARCHAR(MAX)")
    private String content; // Nội dung chi tiết

    @Column(name = "status", columnDefinition = "NVARCHAR(50)")
    private String status; // "Chờ xử lý" / "Đang xử lý" / "Đã giải quyết"

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt = new Date();
    
 
}
