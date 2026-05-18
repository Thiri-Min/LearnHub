package com.training.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminDataInitializer implements ApplicationRunner {

    private static final String ADMIN_EMAIL = "thirithiriminmin@gmail.com";
    private static final String DEFAULT_ADMIN_PASSWORD = "Admin@123456";

    private final UserRepository userRepository;

    public AdminDataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        userRepository.findByEmail(ADMIN_EMAIL).ifPresentOrElse(user -> {
            if (!user.isAdmin()) {
                user.setRole("ADMIN");
                userRepository.save(user);
            }
        }, () -> {
            User admin = new User("Thiri", "Min", ADMIN_EMAIL, DEFAULT_ADMIN_PASSWORD);
            admin.setRole("ADMIN");
            userRepository.save(admin);
        });
    }
}
