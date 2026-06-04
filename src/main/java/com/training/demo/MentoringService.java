package com.training.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MentoringService {

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private MentorSlotRepository mentorSlotRepository;

    @Autowired
    private MentorBookingRepository mentorBookingRepository;

    @Autowired
    private MentorChatMessageRepository mentorChatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatReadStatusService chatReadStatusService;

    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll().stream()
                .sorted(Comparator.comparingInt(this::mentorDisplayOrder))
                .toList();
    }

    private int mentorDisplayOrder(Mentor mentor) {
        if (mentor.getName() == null) {
            return 999;
        }
        String normalized = mentor.getName().replace(".", "").trim().toLowerCase();
        if (normalized.contains("thiri")) {
            return 0;
        }
        if (normalized.contains("tu") && normalized.startsWith("mr")) {
            return 1;
        }
        if (normalized.contains("hai")) {
            return 2;
        }
        if (normalized.contains("mai")) {
            return 3;
        }
        if (normalized.contains("hieu")) {
            return 4;
        }
        if (normalized.contains("loan")) {
            return 5;
        }
        return 100 + mentor.getId().intValue();
    }

    public Optional<Mentor> getMentor(Long mentorId) {
        return mentorRepository.findById(mentorId);
    }

    public long countAvailableSlots(Long mentorId) {
        return mentorSlotRepository.countByMentorIdAndStatus(mentorId, "AVAILABLE");
    }

    public List<MentorSlot> getNextAvailableSlots(Long mentorId, int limit) {
        return mentorSlotRepository.findByMentorIdAndStatusOrderByStartTimeAsc(mentorId, "AVAILABLE").stream()
                .filter(slot -> slot.getStartTime().isAfter(LocalDateTime.now()))
                .limit(limit)
                .toList();
    }

    public List<MentorSlot> getUpcomingSlots(Long mentorId) {
        return mentorSlotRepository.findByMentorIdAndStartTimeAfterOrderByStartTimeAsc(
                mentorId, LocalDateTime.now().minusHours(1));
    }

    public List<MentorBooking> getUserBookingsForMentor(Long userId, Long mentorId) {
        return mentorBookingRepository.findByUserIdAndMentorIdOrderByRequestedAtDesc(userId, mentorId);
    }

    public List<MentorChatMessage> getChatMessages(Long userId, Long mentorId) {
        return mentorChatMessageRepository.findByUserIdAndMentorIdOrderBySentAtAsc(userId, mentorId);
    }

    public List<MentorChatMessage> getMentorChatMessages(Long mentorId) {
        return mentorChatMessageRepository.findByMentorIdOrderBySentAtAsc(mentorId);
    }

    public Optional<Long> getLatestChatUserId(Long mentorId) {
        return mentorChatMessageRepository.findFirstByMentorIdOrderBySentAtDesc(mentorId)
                .map(MentorChatMessage::getUserId);
    }

    public ChatNotificationState getChatNotificationState(User user) {
        if (user == null) {
            return new ChatNotificationState(0, "/mentoring");
        }
        List<MentorChatMessage> messages = user.isAdmin()
                ? mentorChatMessageRepository.findAll().stream()
                        .sorted(Comparator.comparing(MentorChatMessage::getSentAt).reversed())
                        .toList()
                : mentorChatMessageRepository.findByUserIdOrderBySentAtDesc(user.getId());
        long unreadCount = chatReadStatusService.countUnread(user, messages);
        MentorChatMessage targetMessage = chatReadStatusService.latestUnread(user, messages)
                .orElse(messages.isEmpty() ? null : messages.get(0));
        return new ChatNotificationState(unreadCount, buildChatHref(user, targetMessage));
    }

    private String buildChatHref(User user, MentorChatMessage message) {
        if (message == null) {
            return "/mentoring";
        }
        String href = "/mentoring/mentor?mentorId=" + message.getMentorId();
        if (user != null && user.isAdmin()) {
            href += "&menteeId=" + message.getUserId();
        }
        return href + "#chat";
    }

    public List<ChatConversation> getChatConversations(Long mentorId) {
        return getChatConversations(mentorId, null);
    }

    public List<ChatConversation> getChatConversations(Long mentorId, User viewer) {
        List<MentorChatMessage> messages = mentorChatMessageRepository.findByMentorIdOrderBySentAtDesc(mentorId);
        Map<Long, MentorChatMessage> latestByUser = new LinkedHashMap<>();
        Map<Long, Integer> pendingCounts = new LinkedHashMap<>();
        Map<Long, Boolean> mentorReplySeen = new LinkedHashMap<>();
        Map<Long, List<MentorChatMessage>> messagesByUser = new LinkedHashMap<>();
        for (MentorChatMessage message : messages) {
            Long userId = message.getUserId();
            messagesByUser.computeIfAbsent(userId, key -> new ArrayList<>()).add(message);
            if ("USER".equals(message.getSender())) {
                latestByUser.putIfAbsent(userId, message);
                if (!mentorReplySeen.getOrDefault(userId, false)) {
                    pendingCounts.merge(userId, 1, Integer::sum);
                }
            } else if ("MENTOR".equals(message.getSender())) {
                mentorReplySeen.putIfAbsent(userId, true);
            }
        }
        Map<Long, User> users = userRepository.findAllById(latestByUser.keySet()).stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        List<ChatConversation> conversations = new ArrayList<>();
        for (Map.Entry<Long, MentorChatMessage> entry : latestByUser.entrySet()) {
            User user = users.get(entry.getKey());
            MentorChatMessage latest = entry.getValue();
            conversations.add(new ChatConversation(
                    entry.getKey(),
                    user != null ? buildDisplayName(user) : "Mentee #" + entry.getKey(),
                    user != null && user.getEmail() != null ? user.getEmail() : "",
                    latest.getContent(),
                    latest.getSentAt(),
                    viewer != null
                            ? (int) chatReadStatusService.countUnread(viewer, messagesByUser.get(entry.getKey()))
                            : pendingCounts.getOrDefault(entry.getKey(), 0)
            ));
        }
        return conversations;
    }

    @Transactional
    public MentorBooking requestBooking(Long userId, Long slotId, String studentName,
                                        String studentEmail, String studentNote) throws Exception {
        if (studentName == null || studentName.isBlank()) {
            throw new Exception("Student name is required.");
        }
        if (studentEmail == null || studentEmail.isBlank()) {
            throw new Exception("Email is required.");
        }
        if (!studentEmail.contains("@")) {
            throw new Exception("Please enter a valid email address.");
        }

        MentorSlot slot = mentorSlotRepository.findById(slotId)
                .orElseThrow(() -> new Exception("Time slot not found."));

        if (!"AVAILABLE".equals(slot.getStatus())) {
            throw new Exception("This time slot is no longer available.");
        }

        if (slot.getStartTime().isBefore(LocalDateTime.now())) {
            throw new Exception("Cannot book a past time slot.");
        }

        Optional<MentorBooking> existing = mentorBookingRepository.findBySlotIdAndUserId(slotId, userId);
        if (existing.isPresent()) {
            throw new Exception("You already requested this time slot.");
        }

        slot.setStatus("REQUESTED");
        mentorSlotRepository.save(slot);

        MentorBooking booking = new MentorBooking();
        booking.setUserId(userId);
        booking.setMentorId(slot.getMentorId());
        booking.setSlotId(slotId);
        booking.setStatus("REQUESTED");
        booking.setStudentName(studentName.trim());
        booking.setStudentEmail(studentEmail.trim());
        booking.setStudentNote(studentNote != null ? studentNote.trim() : "");
        booking.setRequestedAt(LocalDateTime.now());
        return mentorBookingRepository.save(booking);
    }

    @Transactional
    public void sendUserMessage(Long userId, Long mentorId, String content) throws Exception {
        sendChatMessage(userId, mentorId, content, "USER");
    }

    @Transactional
    public void sendMentorMessage(Long targetUserId, Long mentorId, String content) throws Exception {
        sendChatMessage(targetUserId, mentorId, content, "MENTOR");
    }

    private void sendChatMessage(Long userId, Long mentorId, String content, String sender) throws Exception {
        if (content == null || content.isBlank()) {
            throw new Exception("Message cannot be empty.");
        }
        mentorRepository.findById(mentorId)
                .orElseThrow(() -> new Exception("Mentor not found."));

        MentorChatMessage message = new MentorChatMessage();
        message.setUserId(userId);
        message.setMentorId(mentorId);
        message.setSender(sender);
        message.setContent(content.trim());
        message.setSentAt(LocalDateTime.now());
        mentorChatMessageRepository.save(message);
    }

    private String buildDisplayName(User user) {
        String first = user.getFirstName() != null ? user.getFirstName().trim() : "";
        String last = user.getLastName() != null ? user.getLastName().trim() : "";
        String full = (first + " " + last).trim();
        if (!full.isEmpty()) {
            return full;
        }
        return user.getUsername() != null ? user.getUsername() : "Mentee";
    }

    public static class ChatConversation {
        private final Long userId;
        private final String displayName;
        private final String email;
        private final String lastMessage;
        private final LocalDateTime lastSentAt;
        private final int notificationCount;

        public ChatConversation(Long userId, String displayName, String email, String lastMessage, LocalDateTime lastSentAt, int notificationCount) {
            this.userId = userId;
            this.displayName = displayName;
            this.email = email;
            this.lastMessage = lastMessage;
            this.lastSentAt = lastSentAt;
            this.notificationCount = notificationCount;
        }

        public Long getUserId() {
            return userId;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getEmail() {
            return email;
        }

        public String getLastMessage() {
            return lastMessage;
        }

        public LocalDateTime getLastSentAt() {
            return lastSentAt;
        }

        public int getNotificationCount() {
            return notificationCount;
        }
    }

    public static class ChatNotificationState {
        private final long unreadCount;
        private final String href;

        public ChatNotificationState(long unreadCount, String href) {
            this.unreadCount = unreadCount;
            this.href = href;
        }

        public long getUnreadCount() {
            return unreadCount;
        }

        public String getHref() {
            return href;
        }
    }
}
