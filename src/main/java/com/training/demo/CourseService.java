package com.training.demo;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAllByOrderByIdAsc();
    }

    public Course findById(long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course addCourse(String title, String description, String icon, double price) {
        if (!StringUtils.hasText(title) || !StringUtils.hasText(description)) {
            throw new IllegalArgumentException("Title and description are required.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (courseRepository.existsByTitleIgnoreCase(title.trim())) {
            throw new IllegalArgumentException("A course with this title already exists.");
        }
        return courseRepository.save(new Course(title.trim(), description.trim(), normalizeIcon(icon), price));
    }

    public Course updateCourse(long id, String title, String description, String icon, double price) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found."));
        if (!StringUtils.hasText(title) || !StringUtils.hasText(description)) {
            throw new IllegalArgumentException("Title and description are required.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (courseRepository.existsByTitleIgnoreCaseAndIdNot(title.trim(), id)) {
            throw new IllegalArgumentException("Another course with this title already exists.");
        }
        course.setTitle(title.trim());
        course.setDescription(description.trim());
        course.setIcon(normalizeIcon(icon));
        course.setPrice(price);
        return courseRepository.save(course);
    }

    public void deleteCourse(long id) {
        courseRepository.deleteById(id);
    }

    public List<Course> findFeatured() {
        return courseRepository.findAllByFeaturedTrueOrderByIdAsc();
    }

    public List<Course> findDefaultFeatured(int limit) {
        return courseRepository.findAllByOrderByIdAsc().stream()
                .limit(limit)
                .toList();
    }

    /** Guests always see defaults; logged-in users see admin-starred courses, falling back to defaults. */
    public List<Course> findFeaturedForHome(boolean loggedIn) {
        if (loggedIn) {
            List<Course> starred = findFeatured();
            if (!starred.isEmpty()) {
                return starred;
            }
        }
        return findDefaultFeatured(3);
    }

    public Course toggleFeatured(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found."));
        course.setFeatured(!course.isFeatured());
        return courseRepository.save(course);
    }

    private static final Set<String> BRAND_ICON_NAMES = Set.of(
            "java", "python", "git-alt", "git", "github", "gitlab", "js", "node-js", "node",
            "react", "angular", "vuejs", "docker", "aws", "microsoft", "windows", "apple",
            "android", "linux", "php", "html5", "css3", "css3-alt", "bootstrap", "sass",
            "npm", "yarn", "figma", "youtube", "tiktok", "linkedin", "facebook", "google");

    /** Friendly keywords admins may type that aren't real Font Awesome icon names. */
    private static final Map<String, String> ICON_ALIASES = Map.ofEntries(
            Map.entry("ai", "robot"),
            Map.entry("artificial-intelligence", "robot"),
            Map.entry("machine-learning", "brain"),
            Map.entry("ml", "brain"),
            Map.entry("data", "chart-line"),
            Map.entry("analytics", "chart-line"),
            Map.entry("security", "shield-halved"),
            Map.entry("web", "globe"),
            Map.entry("mobile", "mobile-screen"),
            Map.entry("sql", "database"),
            Map.entry("db", "database"),
            Map.entry("devops", "gears"),
            Map.entry("automation", "gears"),
            Map.entry("testing", "vial"),
            Map.entry("spring", "seedling"),
            Map.entry("dotnet", "code"),
            Map.entry("csharp", "code"));

    /**
     * Make admin-entered icon classes render reliably: accept bare names ("cloud"),
     * missing style prefixes ("fa-cloud"), and wrong families ("fas fa-java" for a
     * brands-only icon), producing a valid Font Awesome class pair.
     */
    static String normalizeIcon(String icon) {
        if (!StringUtils.hasText(icon)) {
            return "fas fa-book";
        }
        String cleaned = icon.trim().replaceAll("\\s+", " ");
        String[] tokens = cleaned.split(" ");

        String stylePrefix = null;
        String iconName = null;
        for (String token : tokens) {
            switch (token) {
                case "fas", "fa-solid" -> stylePrefix = "fas";
                case "fab", "fa-brands" -> stylePrefix = "fab";
                case "far", "fa-regular" -> stylePrefix = "far";
                case "fa" -> { /* generic prefix, ignore */ }
                default -> {
                    if (iconName == null) {
                        iconName = token.startsWith("fa-") ? token.substring(3) : token;
                    }
                }
            }
        }
        if (iconName == null || iconName.isBlank()) {
            return "fas fa-book";
        }
        iconName = iconName.toLowerCase();
        iconName = ICON_ALIASES.getOrDefault(iconName, iconName);
        if (BRAND_ICON_NAMES.contains(iconName)) {
            stylePrefix = "fab";
        } else if (stylePrefix == null || "fab".equals(stylePrefix)) {
            stylePrefix = "fas";
        }
        return stylePrefix + " fa-" + iconName;
    }
}
