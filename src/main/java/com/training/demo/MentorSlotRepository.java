package com.training.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MentorSlotRepository extends JpaRepository<MentorSlot, Long> {
    List<MentorSlot> findByMentorIdAndStartTimeAfterOrderByStartTimeAsc(Long mentorId, LocalDateTime after);

    List<MentorSlot> findByMentorIdAndStatusOrderByStartTimeAsc(Long mentorId, String status);

    long countByMentorIdAndStatus(Long mentorId, String status);
}
