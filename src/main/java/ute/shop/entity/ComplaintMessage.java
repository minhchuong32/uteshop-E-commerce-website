package ute.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "complaint_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id")
    private Complaint complaint; // Thuộc về khiếu nại nào

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender; // Người gửi (Admin hoặc User)

    @Column(name = "content", columnDefinition = "NVARCHAR(MAX)")
    private String content; // Nội dung tin nhắn

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    @Builder.Default
    private Date createdAt = new Date(); // Giữ mặc định khi dùng builder
}
