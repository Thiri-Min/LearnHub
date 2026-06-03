package com.training.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        if (content == null || content.isBlank()) {
            throw new Exception("Message cannot be empty.");
        }
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new Exception("Mentor not found."));

        MentorChatMessage userMsg = new MentorChatMessage();
        userMsg.setUserId(userId);
        userMsg.setMentorId(mentorId);
        userMsg.setSender("USER");
        userMsg.setContent(content.trim());
        userMsg.setSentAt(LocalDateTime.now());
        mentorChatMessageRepository.save(userMsg);

        MentorChatMessage mentorReply = new MentorChatMessage();
        mentorReply.setUserId(userId);
        mentorReply.setMentorId(mentorId);
        mentorReply.setSender("MENTOR");
        mentorReply.setContent("Hello! This is " + mentor.getName()
                + ". Thank you for your message. I will review it and respond during our session. "
                + "Feel free to share your questions about " + mentor.getExpertise() + ".");
        mentorReply.setSentAt(LocalDateTime.now().plusSeconds(1));
        mentorChatMessageRepository.save(mentorReply);
    }
}
