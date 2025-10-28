package ute.shop.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lưu trữ ngữ cảnh của một phiên chat
 * Giúp bot "nhớ" các tin nhắn trước đó
 */
public class ChatContext {
    private String sessionId;
    private long startTime;
    private List<String> userMessages = new ArrayList<>();
    private List<String> botMessages = new ArrayList<>();
    private String currentTopic; // Chủ đề đang được thảo luận
    private Map<String, Object> userData = new HashMap<>(); // Lưu thông tin user
    
    public void addUserMessage(String message) {
        userMessages.add(message);
    }
    
    public void addBotMessage(String message) {
        botMessages.add(message);
    }
    
    public String getLastUserMessage() {
        return userMessages.isEmpty() ? null : userMessages.get(userMessages.size() - 1);
    }
    
    public List<String> getRecentMessages(int count) {
        int start = Math.max(0, userMessages.size() - count);
        return userMessages.subList(start, userMessages.size());
    }
    
    // Getters & Setters...
}