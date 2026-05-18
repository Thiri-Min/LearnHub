package com.training.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MentorBookingRepository extends JpaRepository<MentorBooking, Long> {
    List<MentorBooking> findByUserIdOrderByRequestedAtDesc(Long userId);

    List<MentorBooking> findByUserIdAndMentorIdOrderByRequestedAtDesc(Long userId, Long mentorId);

    Optional<MentorBooking> findBySlotIdAndUserId(Long slotId, Long userId);
}
