package com.training.demo;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final LoginEventRepository loginEventRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final RichContentViewRepository richContentViewRepository;
    private final UserFavoriteRepository userFavoriteRepository;

    public AdminService(UserRepository userRepository,
                        LoginEventRepository loginEventRepository,
                        QuizAttemptRepository quizAttemptRepository,
                        RichContentViewRepository richContentViewRepository,
                        UserFavoriteRepository userFavoriteRepository) {
        this.userRepository = userRepository;
        this.loginEventRepository = loginEventRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.richContentViewRepository = richContentViewRepository;
        this.userFavoriteRepository = userFavoriteRepository;
    }

    public Map<String, Object> getDashboardData() {
        List<User> users = userRepository.findAll().stream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());

        Map<Long, User> userById = users.stream()
                .collect(Collectors.toMap(User::getId, u -> u));

        Map<String, Object> data = new HashMap<>();
        data.put("users", users);
        data.put("userById", userById);
        data.put("loginEvents", loginEventRepository.findAllByOrderByLoginAtDesc());
        data.put("quizAttempts", quizAttemptRepository.findAllByOrderByCompletedAtDesc());
        data.put("richContentViews", richContentViewRepository.findAll().stream()
                .sorted(Comparator.comparing(RichContentView::getLastViewedAt).reversed())
                .collect(Collectors.toList()));
        data.put("favorites", userFavoriteRepository.findAll().stream()
                .sorted(Comparator.comparing(UserFavorite::getAddedAt).reversed())
                .collect(Collectors.toList()));
        return data;
    }

    public QuizAttempt saveQuizAttempt(Long userId, String subject, String level,
                                       int score, int total, int percentage,
                                       String grade, boolean timeUp) {
        QuizAttempt attempt = new QuizAttempt();
        attempt.setUserId(userId);
        attempt.setSubject(subject);
        attempt.setLevel(level);
        attempt.setScore(score);
        attempt.setTotalQuestions(total);
        attempt.setPercentage(percentage);
        attempt.setGrade(grade);
        attempt.setTimeUp(timeUp);
        attempt.setCompletedAt(java.time.LocalDateTime.now());
        return quizAttemptRepository.save(attempt);
    }
}
