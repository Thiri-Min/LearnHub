package com.training.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User registerUser(String firstName, String lastName, String email, String password) throws Exception {
        String trimmedFirst = firstName == null ? "" : firstName.trim();
        String trimmedLast = lastName == null ? "" : lastName.trim();
        String trimmedEmail = email == null ? "" : email.trim();
        if (trimmedFirst.isEmpty() || trimmedLast.isEmpty() || trimmedEmail.isEmpty()) {
            throw new Exception("First name, last name, and email are required.");
        }
        Optional<User> existingUser = userRepository.findByEmail(trimmedEmail);
        if (existingUser.isPresent()) {
            throw new Exception("Email already registered!");
        }
        
        User user = new User(trimmedFirst, trimmedLast, trimmedEmail, password);
        
        // Save to database
        return userRepository.save(user);
    }
    
    public Optional<User> findByEmail(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        return userRepository.findByEmail(email.trim());
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public User updateUser(Long id, String firstName, String lastName, String email) throws Exception {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("User not found!"));

        String trimmedFirst = firstName == null ? "" : firstName.trim();
        String trimmedLast = lastName == null ? "" : lastName.trim();
        String trimmedEmail = email == null ? "" : email.trim();

        if (trimmedFirst.isEmpty() || trimmedLast.isEmpty() || trimmedEmail.isEmpty()) {
            throw new Exception("First name, last name, and email are required.");
        }

        if (!user.getEmail().equalsIgnoreCase(trimmedEmail)) {
            Optional<User> existing = userRepository.findByEmail(trimmedEmail);
            if (existing.isPresent() && !existing.get().getId().equals(id)) {
                throw new Exception("Email is already registered to another account.");
            }
        }

        user.setFirstName(trimmedFirst);
        user.setLastName(trimmedLast);
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
}
