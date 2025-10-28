package ute.shop.dto;

import java.io.Serializable;

// Lớp này là Data Transfer Object (DTO), dùng để vận chuyển dữ liệu tin nhắn
// giữa backend (Controller, WebSocket) và frontend (JSP, JavaScript).
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1L; // Cần thiết cho Serializable

    private Long senderId;
    private String senderUsername;
    private String senderAvatar;
    private String type; // "TEXT" hoặc "FILE"
    private String content;
    private String originalFilename;
    private String createdAt; // Luôn là chuỗi đã được định dạng

    // Constructor rỗng cần thiết cho các thư viện như Gson/Jackson
    public MessageDTO() {
    }

    // Constructor đầy đủ để dễ dàng tạo đối tượng
    public MessageDTO(Long senderId, String senderUsername, String senderAvatar, String type, String content, String originalFilename, String createdAt) {
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.senderAvatar = senderAvatar;
        this.type = type;
        this.content = content;
        this.originalFilename = originalFilename;
        this.createdAt = createdAt;
    }

    // Generate Getters and Setters cho tất cả các thuộc tính
    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}