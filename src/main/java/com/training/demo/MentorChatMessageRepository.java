package com.training.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorChatMessageRepository extends JpaRepository<MentorChatMessage, Long> {
    List<MentorChatMessage> findByUserIdAndMentorIdOrderBySentAtAsc(Long userId, Long mentorId);
}
