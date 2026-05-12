package com.training.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User registerUser(String firstName, String lastName, String email, String password) throws Exception {
        // Check if user already exists
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new Exception("Email already registered!");
        }
        
        // Create new user
        User user = new User(firstName, lastName, email, password);
        
        // Save to database
        return userRepository.save(user);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User updateUser(Long id, String firstName, String lastName, String email) throws Exception {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("User not found!"));
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        
        return userRepository.save(user);
    }
    
    public User updateProfileImage(Long id, byte[] imageData) throws Exception {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("User not found!"));
        
        user.setProfileImage(imageData);
        return userRepository.save(user);
    }
}
