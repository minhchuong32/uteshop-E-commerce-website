package ute.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "complaint_messages") // Tên bảng này khớp với code bạn cung cấp
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
    private Complaint complaint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(name = "content", columnDefinition = "NVARCHAR(MAX)")
    private String content;
    /**
     * Lưu tên file gốc để hiển thị cho người dùng.
     */
    @Column(name = "original_filename")
    private String originalFilename;
    /**
     * - @Column: Định nghĩa cột trong database.
     * - @Builder.Default: Đảm bảo rằng nếu không chỉ định, loại tin nhắn sẽ mặc định là "TEXT".
     */
    @Column(name = "message_type", nullable = false, length = 20)
    @Builder.Default
    private String messageType = "TEXT";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private Date createdAt = new Date();
}