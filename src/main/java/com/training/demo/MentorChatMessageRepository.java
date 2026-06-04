package com.training.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MentorChatMessageRepository extends JpaRepository<MentorChatMessage, Long> {
    List<MentorChatMessage> findByUserIdAndMentorIdOrderBySentAtAsc(Long userId, Long mentorId);

    List<MentorChatMessage> findByMentorIdOrderBySentAtAsc(Long mentorId);

    List<MentorChatMessage> findByMentorIdOrderBySentAtDesc(Long mentorId);

    List<MentorChatMessage> findByUserIdOrderBySentAtDesc(Long userId);

    List<MentorChatMessage> findByMentorIdAndSenderOrderBySentAtDesc(Long mentorId, String sender);

    Optional<MentorChatMessage> findFirstByMentorIdOrderBySentAtDesc(Long mentorId);

    void deleteByMentorId(Long mentorId);
}
