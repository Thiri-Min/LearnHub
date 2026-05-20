package com.training.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AdminDataInitializer implements ApplicationRunner {

    private static final String ADMIN_EMAIL = "thirithiriminmin@gmail.com";
    private static final String ADMIN_USERNAME = "thirimin";
    private static final String DEFAULT_ADMIN_PASSWORD = "Admin@123456";

    private final UserRepository userRepository;
    private final UserService userService;

    public AdminDataInitializer(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) {
        backfillMissingUsernames();

        userRepository.findByEmailIgnoreCase(ADMIN_EMAIL).ifPresentOrElse(user -> {
            if (user.getUsername() == null || user.getUsername().isBlank()) {
                user.setUsername(ADMIN_USERNAME);
            }
            if (!user.isAdmin()) {
                user.setRole("ADMIN");
            }
            userRepository.save(user);
        }, () -> {
            User admin = new User("Thiri", "Min", ADMIN_USERNAME, ADMIN_EMAIL, DEFAULT_ADMIN_PASSWORD);
            admin.setRole("ADMIN");
            userRepository.saveAndFlush(admin);
        });
    }

    private void backfillMissingUsernames() {
        for (User user : userRepository.findAll()) {
            if (user.getUsername() == null || user.getUsername().isBlank()) {
                user.setUsername(generateUniqueUsernameFromEmail(user.getEmail()));
                userRepository.saveAndFlush(user);
            }
        }
    }

    private String generateUniqueUsernameFromEmail(String email) {
        String base = "user";
        if (email != null && email.contains("@")) {
            base = userService.normalizeUsername(email.substring(0, email.indexOf('@')).replaceAll("[^a-zA-Z0-9_]", ""));
        }
        if (base.length() < 3) {
            base = "user";
        }
        if (base.length() > 24) {
            base = base.substring(0, 24);
        }
        String candidate = base;
        int suffix = 1;
        while (userRepository.existsByUsernameIgnoreCase(candidate)) {
            candidate = base + suffix;
            suffix++;
        }
        return candidate;
    }
}
