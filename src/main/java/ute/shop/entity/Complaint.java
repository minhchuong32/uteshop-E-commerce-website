package ute.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    // 📎 Tên file đính kèm (ảnh, video, tài liệu)
    @Column(name = "attachment", columnDefinition = "NVARCHAR(255)")
    private String attachment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    @Builder.Default
    private Date createdAt = new Date();

    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt ASC")
    @Builder.Default
    private List<ComplaintMessage> messages = new ArrayList<>();
    
    @OneToMany(mappedBy = "relatedComplaint", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();

}
