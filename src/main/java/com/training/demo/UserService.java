package com.training.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,30}$");

    @Autowired
    private UserRepository userRepository;

    @Value("${app.admin.email:thirithiriminmin@gmail.com}")
    private String reservedAdminEmail;

    public User registerUser(String firstName, String lastName, String username, String email, String password) throws Exception {
        String trimmedFirst = trim(firstName);
        String trimmedLast = trim(lastName);
        String normalizedUsername = normalizeUsername(username);
        String trimmedEmail = trim(email);

        if (trimmedFirst.isEmpty() || trimmedLast.isEmpty() || normalizedUsername.isEmpty() || trimmedEmail.isEmpty()) {
            throw new Exception("First name, last name, username, and email are required.");
        }
        validateUsername(normalizedUsername);

        if (trimmedEmail.equalsIgnoreCase(reservedAdminEmail.trim())) {
            throw new Exception("This email is reserved for the system admin account. Please sign in instead of registering.");
        }

        if (userRepository.existsByUsernameIgnoreCase(normalizedUsername)) {
            throw new Exception("Username is already taken!");
        }
        if (userRepository.existsByEmailIgnoreCase(trimmedEmail)) {
            throw new Exception("Email already registered!");
        }

        User user = new User(trimmedFirst, trimmedLast, normalizedUsername, trimmedEmail, password);
        return userRepository.saveAndFlush(user);
    }

    public Optional<User> findByEmail(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        return userRepository.findByEmailIgnoreCase(email.trim());
    }

    public Optional<User> findByUsername(String username) {
        if (username == null || username.isBlank()) {
            return Optional.empty();
        }
        return userRepository.findByUsernameIgnoreCase(normalizeUsername(username));
    }

    /** Login with either email or username (case-insensitive). */
    public Optional<User> findByEmailOrUsername(String emailOrUsername) {
        if (emailOrUsername == null || emailOrUsername.isBlank()) {
            return Optional.empty();
        }
        String identifier = emailOrUsername.trim();
        if (identifier.contains("@")) {
            return findByEmail(identifier);
        }
        return findByUsername(identifier);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, String firstName, String lastName, String username, String email) throws Exception {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("User not found!"));

        String trimmedFirst = trim(firstName);
        String trimmedLast = trim(lastName);
        String normalizedUsername = normalizeUsername(username);
        String trimmedEmail = trim(email);

        if (trimmedFirst.isEmpty() || trimmedLast.isEmpty() || normalizedUsername.isEmpty() || trimmedEmail.isEmpty()) {
            throw new Exception("First name, last name, username, and email are required.");
        }
        validateUsername(normalizedUsername);

        if (!user.getUsername().equalsIgnoreCase(normalizedUsername)
                && userRepository.existsByUsernameIgnoreCase(normalizedUsername)) {
            throw new Exception("Username is already taken by another account.");
        }

        if (!user.getEmail().equalsIgnoreCase(trimmedEmail)
                && userRepository.existsByEmailIgnoreCase(trimmedEmail)) {
            throw new Exception("Email is already registered to another account.");
        }

        user.setFirstName(trimmedFirst);
        user.setLastName(trimmedLast);
        user.setUsername(normalizedUsername);
        user.setEmail(trimmedEmail);

        userRepository.saveAndFlush(user);
        return userRepository.findById(id).orElseThrow(() -> new Exception("User not found after save."));
    }

    public User updateProfileImage(Long id, byte[] imageData) throws Exception {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("User not found!"));

        user.setProfileImage(imageData);
        return userRepository.save(user);
    }

    public User updatePassword(Long id, String currentPassword, String newPassword) throws Exception {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("User not found!"));

        if (!user.getPassword().equals(currentPassword)) {
            throw new Exception("Current password is incorrect.");
        }
        if (newPassword == null || newPassword.length() < 4) {
            throw new Exception("New password must be at least 4 characters.");
        }
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public String normalizeUsername(String username) {
        if (username == null) {
            return "";
        }
        return username.trim().toLowerCase();
    }

    private void validateUsername(String username) throws Exception {
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new Exception("Username must be 3–30 characters and use only letters, numbers, and underscores.");
        }
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
