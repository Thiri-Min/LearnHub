package com.training.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class CourseDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(CourseDataInitializer.class);

    private final CourseRepository courseRepository;

    public CourseDataInitializer(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (courseRepository.count() > 0) {
            normalizeExistingIcons();
            ensureDefaultFeaturedIfNone();
            return;
        }
        List<Course> seeded = List.of(
                new Course("SQL Mastery", "Learn SQL query writing, joins, indexing, and database design fundamentals.", "fas fa-database", 89.0),
                new Course("Git Essentials", "Master version control workflows, branching, and collaboration with Git.", "fab fa-git-alt", 79.0),
                new Course("DSA Fundamentals", "Build strong algorithm and data structure skills for coding and interview success.", "fas fa-brain", 109.0),
                new Course("Java Essentials", "Start your programming journey with Java fundamentals and practical examples.", "fab fa-java", 99.0),
                new Course("Spring Framework", "Learn Spring Boot, dependency injection, and building modern Java applications.", "fas fa-seedling", 129.0),
                new Course("Mock Project", "Build a complete mock project to showcase real-world application skills.", "fas fa-robot", 119.0)
        );
        courseRepository.saveAll(seeded);
        seeded.stream().limit(3).forEach(course -> course.setFeatured(true));
        courseRepository.saveAll(seeded);
        log.info("Seeded default course catalog with 6 courses (3 featured by default)");
    }

    private void ensureDefaultFeaturedIfNone() {
        if (!courseRepository.findAllByFeaturedTrueOrderByIdAsc().isEmpty()) {
            return;
        }
        List<Course> defaults = courseRepository.findAllByOrderByIdAsc().stream().limit(3).toList();
        defaults.forEach(course -> course.setFeatured(true));
        courseRepository.saveAll(defaults);
        log.info("Marked {} courses as default featured", defaults.size());
    }

    private void normalizeExistingIcons() {
        for (Course course : courseRepository.findAll()) {
            String normalized = CourseService.normalizeIcon(course.getIcon());
            if (!normalized.equals(course.getIcon())) {
                log.info("Fixing icon for course '{}': '{}' -> '{}'", course.getTitle(), course.getIcon(), normalized);
                course.setIcon(normalized);
                courseRepository.save(course);
            }
        }
    }
}
