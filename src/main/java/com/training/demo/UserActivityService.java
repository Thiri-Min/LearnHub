package com.training.demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserActivityService {

    private static final Map<String, String> COURSE_TITLE_BY_ID = Map.ofEntries(
            Map.entry("1", "SQL Mastery"),
            Map.entry("2", "Git Essentials"),
            Map.entry("3", "DSA Fundamentals"),
            Map.entry("4", "Java Essentials"),
            Map.entry("5", "Spring Framework"),
            Map.entry("6", "Mock Project"),
            Map.entry("java-basics", "Java Basics"),
            Map.entry("python-basics", "Python Basics"),
            Map.entry("dotnet-basics", ".NET Fundamentals"),
            Map.entry("cloud-fundamentals", "Cloud Fundamentals"),
            Map.entry("data-analytics", "Data Analytics Basics")
    );

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginEventRepository loginEventRepository;

    @Autowired
    private RichContentViewRepository richContentViewRepository;

    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    public User recordLogin(User user, HttpServletRequest request) {
        user.setLoginCount(user.getLoginCount() + 1);
        User saved = userRepository.save(user);

        LoginEvent event = new LoginEvent();
        event.setUserId(saved.getId());
        event.setLoginAt(LocalDateTime.now());
        event.setIpAddress(resolveClientIp(request));
        event.setLocation("Location pending (browser will update if allowed)");
        loginEventRepository.save(event);
        return saved;
    }

    public void updateLatestLoginLocation(Long userId, String location) {
        if (location == null || location.isBlank()) {
            return;
        }
        loginEventRepository.findFirstByUserIdOrderByLoginAtDesc(userId).ifPresent(event -> {
            event.setLocation(location.trim());
            loginEventRepository.save(event);
        });
    }

    public void recordRichContentView(Long userId, String topicId, String topicTitle) {
        Optional<RichContentView> existing = richContentViewRepository.findByUserIdAndTopicId(userId, topicId);
        if (existing.isPresent()) {
            RichContentView view = existing.get();
            view.setViewCount(view.getViewCount() + 1);
            view.setLastViewedAt(LocalDateTime.now());
            view.setTopicTitle(topicTitle);
            richContentViewRepository.save(view);
        } else {
            RichContentView view = new RichContentView();
            view.setUserId(userId);
            view.setTopicId(topicId);
            view.setTopicTitle(topicTitle);
            view.setLastViewedAt(LocalDateTime.now());
            view.setViewCount(1);
            richContentViewRepository.save(view);
        }
    }

    public void toggleFavorite(Long userId, String courseId, String courseTitle) {
        String normalizedId = courseId.trim();
        String title = courseTitle != null && !courseTitle.isBlank()
                ? courseTitle.trim()
                : resolveCourseTitle(normalizedId);

        Optional<UserFavorite> existing = userFavoriteRepository.findByUserIdAndCourseId(userId, normalizedId);
        if (existing.isPresent()) {
            userFavoriteRepository.delete(existing.get());
        } else {
            UserFavorite favorite = new UserFavorite();
            favorite.setUserId(userId);
            favorite.setCourseId(normalizedId);
            favorite.setCourseTitle(title);
            favorite.setAddedAt(LocalDateTime.now());
            userFavoriteRepository.save(favorite);
        }
    }

    public void syncFavorites(Long userId, List<String> courseIds) {
        if (courseIds == null) {
            return;
        }
        for (String courseId : courseIds) {
            if (courseId == null || courseId.isBlank()) {
                continue;
            }
            String normalizedId = courseId.trim();
            if (userFavoriteRepository.findByUserIdAndCourseId(userId, normalizedId).isEmpty()) {
                UserFavorite favorite = new UserFavorite();
                favorite.setUserId(userId);
                favorite.setCourseId(normalizedId);
                favorite.setCourseTitle(resolveCourseTitle(normalizedId));
                favorite.setAddedAt(LocalDateTime.now());
                userFavoriteRepository.save(favorite);
            }
        }
    }

    public Map<String, Object> getProgressSummary(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Map<String, Object> summary = new HashMap<>();
        summary.put("loginCount", user.getLoginCount());
        summary.put("loginEvents", loginEventRepository.findByUserIdOrderByLoginAtDesc(userId));
        summary.put("richContentViews", richContentViewRepository.findByUserIdOrderByLastViewedAtDesc(userId));
        summary.put("quizAttempts", quizAttemptRepository.findByUserIdOrderByCompletedAtDesc(userId));
        summary.put("favorites", userFavoriteRepository.findByUserIdOrderByAddedAtDesc(userId));
        return summary;
    }

    public String resolveCourseTitle(String courseId) {
        return COURSE_TITLE_BY_ID.getOrDefault(courseId, "Course " + courseId);
    }

    private String resolveClientIp(HttpServletRequest request) {
        if (request == null) {
            return "Unknown";
        }
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isBlank()) {
            return realIp.trim();
        }
        return request.getRemoteAddr();
    }
}
