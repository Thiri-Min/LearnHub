package com.training.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoginEventRepository extends JpaRepository<LoginEvent, Long> {
    List<LoginEvent> findByUserIdOrderByLoginAtDesc(Long userId);

    List<LoginEvent> findAllByOrderByLoginAtDesc();

    Optional<LoginEvent> findFirstByUserIdOrderByLoginAtDesc(Long userId);
}
