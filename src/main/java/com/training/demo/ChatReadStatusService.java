package com.training.demo;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatReadStatusService {

    private final Map<String, LocalDateTime> readTimes = new ConcurrentHashMap<>();

    public void markRead(User viewer, Long mentorId, Long menteeId) {
        if (viewer == null || mentorId == null || menteeId == null) {
            return;
        }
        readTimes.put(readKey(viewer.isAdmin(), mentorId, menteeId), LocalDateTime.now());
    }

    public String getDeliveryStatus(MentorChatMessage message) {
        if (message == null || message.getSentAt() == null) {
            return "Delivered";
        }
        boolean recipientIsTrainer = "USER".equals(message.getSender());
        LocalDateTime readAt = readTimes.get(readKey(recipientIsTrainer, message.getMentorId(), message.getUserId()));
        return readAt != null && !readAt.isBefore(message.getSentAt()) ? "Read" : "Delivered";
    }

    private String readKey(boolean trainer, Long mentorId, Long menteeId) {
        return mentorId + ":" + menteeId + ":" + (trainer ? "trainer" : "mentee");
    }
}
