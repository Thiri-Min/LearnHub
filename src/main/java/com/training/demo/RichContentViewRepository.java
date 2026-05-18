package com.training.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RichContentViewRepository extends JpaRepository<RichContentView, Long> {
    List<RichContentView> findByUserIdOrderByLastViewedAtDesc(Long userId);

    Optional<RichContentView> findByUserIdAndTopicId(Long userId, String topicId);
}
