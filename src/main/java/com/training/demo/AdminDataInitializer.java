package com.training.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AdminDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminDataInitializer.class);

    private final UserRepository userRepository;
    private final UserService userService;

    @Value("${app.admin.email:thirithiriminmin@gmail.com}")
    private String adminEmail;

    @Value("${app.admin.username:thiri}")
    private String adminUsername;

    @Value("${app.admin.password:111}")
    private String adminPassword;

    @Value("${app.admin.first-name:Thiri}")
    private String adminFirstName;

    @Value("${app.admin.last-name:Min}")
    private String adminLastName;

    @Value("${app.admin.sync-password-on-startup:false}")
    private boolean syncPasswordOnStartup;

    public AdminDataInitializer(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) {
        backfillMissingUsernames();
        ensureAdminAccount();
    }

    private void ensureAdminAccount() {
        String email = adminEmail.trim();
        String username = userService.normalizeUsername(adminUsername.trim());
        String password = adminPassword.trim();

        userRepository.findByEmailIgnoreCase(email).ifPresentOrElse(user -> {
            user.setUsername(username);
            user.setRole("ADMIN");
            if (syncPasswordOnStartup) {
                user.setPassword(password);
            }
            userRepository.saveAndFlush(user);
            log.info("Admin account verified for email {} (username: {})", email, username);
        }, () -> {
            User admin = new User(adminFirstName, adminLastName, username, email, password);
            admin.setRole("ADMIN");
            userRepository.saveAndFlush(admin);
            log.info("Admin account created for email {} (username: {})", email, username);
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
