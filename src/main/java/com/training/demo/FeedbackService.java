package com.training.demo;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback save(String name, String email, String message, Long userId) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(email) || !StringUtils.hasText(message)) {
            throw new IllegalArgumentException("Name, email, and message are required.");
        }
        Feedback feedback = new Feedback();
        feedback.setName(name.trim());
        feedback.setEmail(email.trim());
        feedback.setMessage(message.trim());
        feedback.setUserId(userId);
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> findAllNewestFirst() {
        return feedbackRepository.findAllByOrderBySubmittedAtDesc();
    }
}
