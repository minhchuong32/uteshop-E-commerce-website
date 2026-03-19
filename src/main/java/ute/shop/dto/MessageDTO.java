//package ute.shop.dto;
//
//import java.io.Serializable;
//
//// Lớp này là Data Transfer Object (DTO), dùng để vận chuyển dữ liệu tin nhắn
//// giữa backend (Controller, WebSocket) và frontend (JSP, JavaScript).
//public class MessageDTO implements Serializable {
//
//    private static final long serialVersionUID = 1L; // Cần thiết cho Serializable
//
//    private Long senderId;
//    private String senderUsername;
//    private String senderAvatar;
//    private String type; // "TEXT" hoặc "FILE"
//    private String content;
//    private String originalFilename;
//    private String createdAt; // Luôn là chuỗi đã được định dạng
//
//    // Constructor rỗng cần thiết cho các thư viện như Gson/Jackson
//    public MessageDTO() {
//    }
//
//    // Constructor đầy đủ để dễ dàng tạo đối tượng
//    public MessageDTO(Long senderId, String senderUsername, String senderAvatar, String type, String content, String originalFilename, String createdAt) {
//        this.senderId = senderId;
//        this.senderUsername = senderUsername;
//        this.senderAvatar = senderAvatar;
//        this.type = type;
//        this.content = content;
//        this.originalFilename = originalFilename;
//        this.createdAt = createdAt;
//    }
//
//    // Generate Getters and Setters cho tất cả các thuộc tính
//    public Long getSenderId() {
//        return senderId;
//    }
//
//    public void setSenderId(Long senderId) {
//        this.senderId = senderId;
//    }
//
//    public String getSenderUsername() {
//        return senderUsername;
//    }
//
//    public void setSenderUsername(String senderUsername) {
//        this.senderUsername = senderUsername;
//    }
//
//    public String getSenderAvatar() {
//        return senderAvatar;
//    }
//
//    public void setSenderAvatar(String senderAvatar) {
//        this.senderAvatar = senderAvatar;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getOriginalFilename() {
//        return originalFilename;
//    }
//
//    public void setOriginalFilename(String originalFilename) {
//        this.originalFilename = originalFilename;
//    }
//
//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
//}


package ute.shop.dto;

import java.io.Serializable;

public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long senderId;
    private String senderUsername;
    private String senderAvatar;
    private String type;
    private String content;
    private String originalFilename;
    private String createdAt;

    // constructor private để chỉ Builder tạo object
    private MessageDTO(Builder builder) {
        this.senderId = builder.senderId;
        this.senderUsername = builder.senderUsername;
        this.senderAvatar = builder.senderAvatar;
        this.type = builder.type;
        this.content = builder.content;
        this.originalFilename = builder.originalFilename;
        this.createdAt = builder.createdAt;
    }

    // ===== Builder =====
    public static class Builder {

        private Long senderId;
        private String senderUsername;
        private String senderAvatar;
        private String type;
        private String content;
        private String originalFilename;
        private String createdAt;

        public Builder senderId(Long senderId) {
            this.senderId = senderId;
            return this;
        }

        public Builder senderUsername(String senderUsername) {
            this.senderUsername = senderUsername;
            return this;
        }

        public Builder senderAvatar(String senderAvatar) {
            this.senderAvatar = senderAvatar;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder originalFilename(String originalFilename) {
            this.originalFilename = originalFilename;
            return this;
        }

        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public MessageDTO build() {
            return new MessageDTO(this);
        }
    }

    // ===== Getters =====

    public Long getSenderId() {
        return senderId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}