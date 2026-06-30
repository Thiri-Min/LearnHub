package com.training.demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public String resolveCountryFromRequest(HttpServletRequest request) {
        return resolveCountryFromIp(resolveClientIp(request));
    }

    public User recordLogin(User user, HttpServletRequest request) {
        user.setLoginCount(user.getLoginCount() + 1);
        User saved = userRepository.save(user);

        LoginEvent event = new LoginEvent();
        event.setUserId(saved.getId());
        event.setLoginAt(LocalDateTime.now());
        String ipAddress = resolveClientIp(request);
        event.setIpAddress(ipAddress);
        event.setLocation(resolveCountryFromIp(ipAddress));
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
        String[] headerNames = {
                "X-Forwarded-For",
                "X-Real-IP",
                "CF-Connecting-IP",
                "True-Client-IP"
        };
        for (String headerName : headerNames) {
            String value = request.getHeader(headerName);
            if (value != null && !value.isBlank() && !"unknown".equalsIgnoreCase(value.trim())) {
                return value.split(",")[0].trim();
            }
        }
        String remoteAddr = request.getRemoteAddr();
        return remoteAddr != null && !remoteAddr.isBlank() ? remoteAddr.trim() : "Unknown";
    }

    private String resolveCountryFromIp(String ip) {
        if (ip == null || ip.isBlank() || "Unknown".equalsIgnoreCase(ip.trim())) {
            return "Unknown";
        }
        String normalized = ip.trim();
        if (isLocalOrPrivateAddress(normalized)) {
            return "Local development";
        }

        String fromIpApiCo = lookupCountryFromIpApiCo(normalized);
        if (isResolvableCountry(fromIpApiCo)) {
            return fromIpApiCo;
        }

        String fromIpWho = lookupCountryFromIpWho(normalized);
        if (isResolvableCountry(fromIpWho)) {
            return fromIpWho;
        }

        String fromIpApi = lookupCountryFromIpApi(normalized);
        if (isResolvableCountry(fromIpApi)) {
            return fromIpApi;
        }

        return "Unknown";
    }

    private boolean isResolvableCountry(String country) {
        return country != null && !country.isBlank() && !"Unknown".equalsIgnoreCase(country.trim());
    }

    private String lookupCountryFromIpApiCo(String ip) {
        try {
            URI uri = URI.create("https://ipapi.co/" + ip + "/country_name/");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setRequestProperty("User-Agent", "LearnHub-LMS/1.0");
            if (connection.getResponseCode() != 200) {
                return "Unknown";
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String country = reader.readLine();
                return country != null ? country.trim() : "Unknown";
            }
        } catch (Exception ignored) {
            return "Unknown";
        }
    }

    private String lookupCountryFromIpWho(String ip) {
        try {
            URI uri = URI.create("https://ipwho.is/" + ip);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setRequestProperty("User-Agent", "LearnHub-LMS/1.0");
            if (connection.getResponseCode() != 200) {
                return "Unknown";
            }
            StringBuilder body = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }
            }
            String json = body.toString();
            if (!json.contains("\"success\":true")) {
                return "Unknown";
            }
            Matcher matcher = Pattern.compile("\"country\"\\s*:\\s*\"([^\"]+)\"").matcher(json);
            if (matcher.find()) {
                return matcher.group(1);
            }
        } catch (Exception ignored) {
            return "Unknown";
        }
        return "Unknown";
    }

    private String lookupCountryFromIpApi(String ip) {
        try {
            URI uri = URI.create("http://ip-api.com/json/" + ip + "?fields=status,country");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            if (connection.getResponseCode() != 200) {
                return "Unknown";
            }
            StringBuilder body = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }
            }
            if (!body.toString().contains("\"status\":\"success\"")) {
                return "Unknown";
            }
            Matcher matcher = Pattern.compile("\"country\"\\s*:\\s*\"([^\"]+)\"").matcher(body.toString());
            if (matcher.find()) {
                return matcher.group(1);
            }
        } catch (Exception ignored) {
            return "Unknown";
        }
        return "Unknown";
    }

    private boolean isLocalOrPrivateAddress(String ip) {
        if ("127.0.0.1".equals(ip) || "::1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            return true;
        }
        if (ip.startsWith("192.168.") || ip.startsWith("10.")) {
            return true;
        }
        if (ip.startsWith("172.")) {
            String[] parts = ip.split("\\.");
            if (parts.length >= 2) {
                try {
                    int secondOctet = Integer.parseInt(parts[1]);
                    return secondOctet >= 16 && secondOctet <= 31;
                } catch (NumberFormatException ignored) {
                    return false;
                }
            }
        }
        return false;
    }
}
