package com.training.demo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Shared helper for quiz bank classes. */
final class QuizQuestion {

    private QuizQuestion() {
    }

    static Map<String, Object> of(String question, List<String> options, int answer) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("question", question);
        item.put("options", options);
        item.put("answer", answer);
        if (answer >= 0 && answer < options.size()) {
            item.put("correctOption", options.get(answer));
        }
        return item;
    }
}
