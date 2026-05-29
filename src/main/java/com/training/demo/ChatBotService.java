package com.training.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatBotService {

    private static final Logger log = LoggerFactory.getLogger(ChatBotService.class);
    private static final String OPENAI_BASE = "https://api.openai.com";
    private static final String GEMINI_BASE = "https://generativelanguage.googleapis.com";

    private static final String SYSTEM_PROMPT = """
            You are the friendly AI assistant for "Learn Hub", a Learning Management System website.
            Answer questions about this website, its courses, features, and how to use it.
            Be concise, helpful, and accurate. If you don't know something specific, suggest where on the site the user might look.

            Website pages and features:
            - Home (/ or /home): landing page with login, signup, featured courses, forgot password
            - Courses (/courses): catalog with Add to Cart, Favorites, and Read for each course
            - Course detail (/course-detail?courseId=): Code Demo and Practice Lab (Mock Project has HTML/CSS/JS editors with live UI preview; other courses use Input Code and Output)
            - Cart (/cart): shopping cart for courses
            - Tech (/tech): Git MCQ quizzes at Pre-Intermediate, Intermediate, Advanced levels (10 random questions per attempt)
            - Quiz (/quiz): take quizzes from the tech page
            - Rich content library for learning materials
            - Mentoring (/mentoring): book mentors and chat
            - My Progress (/my-progress): track learning progress
            - Profile (/profile): user profile
            - Feedback (/feedback): submit feedback (hidden for admin users)
            - Admin dashboard (/admin): for administrators

            Available courses in the catalog:
            1. SQL Mastery - database and SQL fundamentals
            2. Git Essentials - version control with Git
            3. DSA Fundamentals - algorithms and data structures
            4. Java Essentials - Java programming basics
            5. Spring Framework - Spring Boot and Java applications
            6. Mock Project - build a mock project with web practice lab (HTML, CSS, JavaScript)

            Counts: 6 courses in the catalog, 6 mentors on the mentoring page, 9 tech skill areas on the Tech page (Java, SQL, Git, DSA, Spring, Mock Project, Python, C++, .NET).
            Git quizzes: 3 levels (Pre-Intermediate, Intermediate, Advanced), 10 random questions per attempt, start from /tech then /quiz.

            Users can sign up, log in, reset password via forgot password on the home page, add courses to cart, favorite courses, read course materials, take Git quizzes, and use mentoring.
            Do not invent features or prices not listed above. Stay focused on helping users navigate and use Learn Hub.
            """;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestClient openAiClient;
    private final RestClient geminiClient;

    @Value("${app.ai.openai-api-key:}")
    private String openAiApiKey;

    @Value("${app.ai.model:gpt-4o-mini}")
    private String openAiModel;

    @Value("${app.ai.gemini-api-key:}")
    private String geminiApiKey;

    @Value("${app.ai.gemini-model:gemini-2.0-flash}")
    private String geminiModel;

    @Value("${app.ai.fallback-enabled:true}")
    private boolean fallbackEnabled;

    public ChatBotService() {
        this.openAiClient = RestClient.builder().baseUrl(OPENAI_BASE).build();
        this.geminiClient = RestClient.builder().baseUrl(GEMINI_BASE).build();
    }

    public boolean isEnabled() {
        return hasOpenAiKey() || hasGeminiKey();
    }

    public String chat(String userMessage) {
        if (!isEnabled() && !fallbackEnabled) {
            throw new IllegalStateException("AI assistant is not configured. Set OpenAI or Gemini API key.");
        }
        if (userMessage == null || userMessage.isBlank()) {
            throw new IllegalArgumentException("Message cannot be empty.");
        }
        if (userMessage.length() > 2000) {
            throw new IllegalArgumentException("Message is too long. Please use 2000 characters or fewer.");
        }

        String trimmed = userMessage.trim();
        IllegalStateException lastError = null;

        if (hasOpenAiKey()) {
            try {
                return callOpenAi(trimmed);
            } catch (IllegalStateException e) {
                lastError = e;
                log.warn("OpenAI unavailable: {}", e.getMessage());
            }
        }

        if (hasGeminiKey()) {
            try {
                return callGemini(trimmed);
            } catch (IllegalStateException e) {
                lastError = e;
                log.warn("Gemini unavailable: {}", e.getMessage());
            }
        }

        if (fallbackEnabled) {
            log.info("Using built-in fallback answers");
            return localFallbackAnswer(trimmed);
        }

        throw lastError != null ? lastError : new IllegalStateException("No AI provider available.");
    }

    private boolean hasOpenAiKey() {
        return openAiApiKey != null && !openAiApiKey.trim().isBlank();
    }

    private boolean hasGeminiKey() {
        return geminiApiKey != null && !geminiApiKey.trim().isBlank();
    }

    private String callOpenAi(String userMessage) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", openAiModel);
        body.put("messages", List.of(
                Map.of("role", "system", "content", SYSTEM_PROMPT),
                Map.of("role", "user", "content", userMessage)
        ));
        body.put("max_tokens", 600);
        body.put("temperature", 0.7);

        try {
            String responseJson = openAiClient.post()
                    .uri("/v1/chat/completions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + openAiApiKey.trim())
                    .body(body)
                    .retrieve()
                    .body(String.class);

            JsonNode root = objectMapper.readTree(responseJson);
            JsonNode choices = root.path("choices");
            if (choices.isEmpty()) {
                throw new IllegalStateException("No response from OpenAI.");
            }
            String content = choices.get(0).path("message").path("content").asText("");
            if (content.isBlank()) {
                throw new IllegalStateException("Empty response from OpenAI.");
            }
            return content.trim();
        } catch (RestClientResponseException e) {
            String detail = parseOpenAiError(e.getResponseBodyAsString(), e.getStatusCode().value());
            log.error("OpenAI API error {}: {}", e.getStatusCode().value(), detail);
            throw new IllegalStateException(detail);
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            log.error("OpenAI request failed", e);
            throw new IllegalStateException("Could not reach OpenAI.");
        }
    }

    private String callGemini(String userMessage) {
        Map<String, Object> body = new HashMap<>();
        body.put("systemInstruction", Map.of("parts", List.of(Map.of("text", SYSTEM_PROMPT))));
        body.put("contents", List.of(Map.of("parts", List.of(Map.of("text", userMessage)))));
        body.put("generationConfig", Map.of(
                "maxOutputTokens", 600,
                "temperature", 0.7
        ));

        String uri = "/v1beta/models/" + geminiModel.trim() + ":generateContent?key=" + geminiApiKey.trim();

        try {
            String responseJson = geminiClient.post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(String.class);

            JsonNode root = objectMapper.readTree(responseJson);
            JsonNode candidates = root.path("candidates");
            if (candidates.isEmpty()) {
                String blockReason = root.path("promptFeedback").path("blockReason").asText("");
                if (!blockReason.isBlank()) {
                    throw new IllegalStateException("Gemini blocked the request: " + blockReason);
                }
                throw new IllegalStateException("No response from Gemini.");
            }
            JsonNode parts = candidates.get(0).path("content").path("parts");
            if (parts.isEmpty()) {
                throw new IllegalStateException("Empty response from Gemini.");
            }
            String content = parts.get(0).path("text").asText("");
            if (content.isBlank()) {
                throw new IllegalStateException("Empty response from Gemini.");
            }
            return content.trim();
        } catch (RestClientResponseException e) {
            String detail = parseGeminiError(e.getResponseBodyAsString(), e.getStatusCode().value());
            log.error("Gemini API error {}: {}", e.getStatusCode().value(), detail);
            throw new IllegalStateException(detail);
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            log.error("Gemini request failed", e);
            throw new IllegalStateException("Could not reach Gemini.");
        }
    }

    private String parseOpenAiError(String body, int status) {
        try {
            if (body != null && !body.isBlank()) {
                JsonNode err = objectMapper.readTree(body).path("error");
                String code = err.path("code").asText("");
                String message = err.path("message").asText("");
                if ("insufficient_quota".equals(code)) {
                    return "OpenAI quota exceeded — trying Gemini next.";
                }
                if ("invalid_api_key".equals(code) || status == 401) {
                    return "Invalid OpenAI API key.";
                }
                if ("rate_limit_exceeded".equals(code) || status == 429) {
                    return "OpenAI rate limit reached — trying Gemini next.";
                }
                if (!message.isBlank()) {
                    return message;
                }
            }
        } catch (Exception ignored) {
            // fall through
        }
        return "OpenAI error " + status + ".";
    }

    private String parseGeminiError(String body, int status) {
        try {
            if (body != null && !body.isBlank()) {
                JsonNode err = objectMapper.readTree(body).path("error");
                String message = err.path("message").asText("");
                if (status == 429) {
                    return "Gemini rate limit reached — using built-in answers.";
                }
                if (!message.isBlank()) {
                    return "Gemini: " + message;
                }
            }
        } catch (Exception ignored) {
            // fall through
        }
        return "Gemini error " + status + ".";
    }

    private String localFallbackAnswer(String message) {
        String q = message.toLowerCase();

        if ((q.contains("how do i take") || q.contains("how to take")) && q.contains("quiz")) {
            return """
                    How to take a quiz:
                    1. Log in and open the Tech page (/tech).
                    2. Find the Git quiz card and pick a level: Pre-Intermediate, Intermediate, or Advanced.
                    3. Click Start Quiz — you get 10 random multiple-choice questions.
                    4. Answer on /quiz and submit to see your score.""";
        }
        if (q.contains("how many") && q.contains("course")) {
            return """
                    There are 6 courses available on Learn Hub (/courses):
                    SQL Mastery, Git Essentials, DSA Fundamentals, Java Essentials, Spring Framework, and Mock Project.
                    Open any course and click Read for lessons and Practice Lab.""";
        }
        if (q.contains("how many") && q.contains("mentor")) {
            return """
                    There are 6 mentors on the Mentoring page (/mentoring).
                    You can view profiles, book consultation slots, and chat with a mentor after booking.""";
        }
        if (q.contains("how many") && (q.contains("tech") || q.contains("skill"))) {
            return """
                    The Tech page (/tech) includes 9 tech skill areas:
                    Java, SQL, Git, DSA, Spring, Mock Project, Python, C++, and .NET.
                    Each area has learning materials (PDFs and videos). Git also has MCQ quizzes at three levels.""";
        }
        if (q.contains("course") || q.contains("catalog") || q.contains("learn")) {
            return """
                    Learn Hub courses (go to /courses):
                    1. SQL Mastery
                    2. Git Essentials
                    3. DSA Fundamentals
                    4. Java Essentials
                    5. Spring Framework
                    6. Mock Project
                    Click Read on a course for Code Demo and Practice Lab, or Add to Cart to save it.""";
        }
        if (q.contains("mock") || q.contains("html") || q.contains("css") || q.contains("javascript")) {
            return """
                    Mock Project (course 6) has a web practice lab: edit HTML, CSS, and JavaScript on the left and see UI Output on the right.
                    Open Courses → Mock Project → Read, then scroll to Practice Lab. Click Run Code to preview your page.""";
        }
        if (q.contains("git") || q.contains("quiz")) {
            return """
                    Git quizzes are on the Tech page (/tech). Choose Pre-Intermediate, Intermediate, or Advanced — each attempt has 10 random MCQs.
                    After starting a quiz, submit your answers on /quiz.""";
        }
        if (q.contains("login") || q.contains("sign up") || q.contains("signup") || q.contains("register")) {
            return "Sign up or log in from the home page (/ or /home). Use your email or username and password. Forgot password is available on the login section.";
        }
        if (q.contains("password") || q.contains("forgot")) {
            return "Use Forgot password on the home page login area to reset your password with your email/username and a new password.";
        }
        if (q.contains("cart") || q.contains("buy") || q.contains("purchase")) {
            return "Browse /courses, click Add to Cart on any course, then open /cart to review your selections.";
        }
        if (q.contains("mentor")) {
            return "Visit /mentoring to browse mentors, book sessions, and chat with them on the mentor detail page.";
        }
        if (q.contains("progress")) {
            return "Your learning activity is shown on /my-progress after you log in.";
        }
        if (q.contains("practice") || q.contains("code") || q.contains("run")) {
            return """
                    Open any course → Read → Practice Lab. Most courses use Input Code and Output panels.
                    Mock Project uses HTML, CSS, JavaScript editors plus a live UI preview. Click Run Code to execute.""";
        }
        if (q.contains("hello") || q.contains("hi ") || q.equals("hi")) {
            return "Hello! I'm the Learn Hub assistant. Ask me about courses, Git quizzes, cart, mentoring, or practice labs.";
        }

        return """
                I'm answering with built-in help (AI providers may be rate-limited).
                Try asking about: courses, Git quizzes, login, cart, Mock Project practice, or mentoring.
                Browse /courses to get started!""";
    }
}
