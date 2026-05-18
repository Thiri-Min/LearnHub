package com.training.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {
    List<UserFavorite> findByUserIdOrderByAddedAtDesc(Long userId);

    Optional<UserFavorite> findByUserIdAndCourseId(Long userId, String courseId);
}
