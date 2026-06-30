package com.training.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatReadStatusService {

    private final Map<String, LocalDateTime> readTimes = new ConcurrentHashMap<>();

    @Autowired
    private UserService userService;

    public void markRead(User viewer, Long mentorId, Long menteeId) {
        if (viewer == null || mentorId == null || menteeId == null) {
            return;
        }
        readTimes.put(readKey(userService.canAccessTrainerView(viewer), mentorId, menteeId), LocalDateTime.now());
    }

    public String getDeliveryStatus(MentorChatMessage message) {
        if (message == null || message.getSentAt() == null) {
            return "Delivered";
        }
        boolean recipientIsTrainer = "USER".equals(message.getSender());
        LocalDateTime readAt = readTimes.get(readKey(recipientIsTrainer, message.getMentorId(), message.getUserId()));
        return readAt != null && !readAt.isBefore(message.getSentAt()) ? "Read" : "Delivered";
    }

    public long countUnread(User viewer, List<MentorChatMessage> messages) {
        if (viewer == null || messages == null) {
            return 0;
        }
        return messages.stream()
                .filter(message -> isUnreadFor(viewer, message))
                .count();
    }

    public Optional<MentorChatMessage> latestUnread(User viewer, List<MentorChatMessage> messages) {
        if (viewer == null || messages == null) {
            return Optional.empty();
        }
        return messages.stream()
                .filter(message -> isUnreadFor(viewer, message))
                .findFirst();
    }

    private boolean isUnreadFor(User viewer, MentorChatMessage message) {
        if (viewer == null || message == null || message.getSentAt() == null) {
            return false;
        }
        boolean viewerIsTrainer = userService.canAccessTrainerView(viewer);
        boolean messageFromOtherSide = viewerIsTrainer
                ? "USER".equals(message.getSender())
                : "MENTOR".equals(message.getSender());
        if (!messageFromOtherSide) {
            return false;
        }
        LocalDateTime readAt = readTimes.get(readKey(viewerIsTrainer, message.getMentorId(), message.getUserId()));
        return readAt == null || message.getSentAt().isAfter(readAt);
    }

    private String readKey(boolean trainer, Long mentorId, Long menteeId) {
        return mentorId + ":" + menteeId + ":" + (trainer ? "trainer" : "mentee");
    }
}
