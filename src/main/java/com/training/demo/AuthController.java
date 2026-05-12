package com.training.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;

    private static final List<Course> COURSE_CATALOG = List.of(
        new Course(1, "SQL Mastery", "Learn SQL query writing, joins, indexing, and database design fundamentals.", "fas fa-database", 89.0),
        new Course(2, "Git Essentials", "Master version control workflows, branching, and collaboration with Git.", "fab fa-git-alt", 79.0),
        new Course(3, "DSA Fundamentals", "Build strong algorithm and data structure skills for coding and interview success.", "fas fa-brain", 109.0),
        new Course(4, "Java Essentials", "Start your programming journey with Java fundamentals and practical examples.", "fas fa-java", 99.0),
        new Course(5, "Spring Framework", "Learn Spring Boot, dependency injection, and building modern Java applications.", "fas fa-seedling", 129.0),
        new Course(6, "Mock Project", "Build a complete mock project to showcase real-world application skills.", "fas fa-robot", 119.0)
    );

    @GetMapping("/login")
    public String login(@RequestParam(required = false, defaultValue = "login") String mode,
                        @RequestParam(required = false) String signupSuccess,
                        RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("authMode", mode);
        if (signupSuccess != null) {
            redirectAttributes.addAttribute("signupSuccess", signupSuccess);
        }
        return "redirect:/";
    }
    
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        var userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            populateHomeModel(session, model);
            model.addAttribute("authMode", "login");
            model.addAttribute("loginError", "Invalid email or password. Please try again.");
            return "home";
        }

        session.setAttribute("loggedInUser", userOpt.get());
        return "redirect:/home";
    }

    @GetMapping("/signup")
    public String signup() {
        return "redirect:/?authMode=signup";
    }
    
    @PostMapping("/signup")
    public String signupUser(@RequestParam String firstName, @RequestParam String lastName,
                            @RequestParam String email, @RequestParam String password,
                            @RequestParam String confirmPassword, Model model, HttpSession session,
                            RedirectAttributes redirectAttributes) {
        try {
            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                populateHomeModel(session, model);
                model.addAttribute("authMode", "signup");
                model.addAttribute("signupError", "Passwords do not match!");
                model.addAttribute("signupFirstName", firstName);
                model.addAttribute("signupLastName", lastName);
                model.addAttribute("signupEmail", email);
                return "home";
            }
            
            // Register user in database
            userService.registerUser(firstName, lastName, email, password);
            redirectAttributes.addAttribute("authMode", "login");
            redirectAttributes.addAttribute("signupSuccess", "Account created successfully. Please sign in.");
            return "redirect:/";
        } catch (Exception e) {
            populateHomeModel(session, model);
            model.addAttribute("authMode", "signup");
            model.addAttribute("signupError", e.getMessage());
            model.addAttribute("signupFirstName", firstName);
            model.addAttribute("signupLastName", lastName);
            model.addAttribute("signupEmail", email);
            return "home";
        }
    }

    @GetMapping("/")
    public String landing(HttpSession session, Model model,
                          @RequestParam(required = false) String authMode,
                          @RequestParam(required = false) String signupSuccess) {
        var user = session.getAttribute("loggedInUser");
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("cartCount", getCart(session).size());
        }
        if (authMode != null) {
            model.addAttribute("authMode", authMode);
        }
        if (signupSuccess != null) {
            model.addAttribute("signupSuccess", signupSuccess);
        }
        return "home";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        model.addAttribute("user", user);
        model.addAttribute("cartCount", getCart(session).size());
        return "home";
    }

    @GetMapping("/courses")
    public String courses(HttpSession session, Model model, @RequestParam(required = false) String message) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        model.addAttribute("user", user);
        model.addAttribute("courses", COURSE_CATALOG);
        model.addAttribute("cartCount", getCart(session).size());
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "courses";
    }

    @GetMapping("/tech")
    public String tech(HttpSession session, Model model) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        model.addAttribute("user", user);
        model.addAttribute("cartCount", getCart(session).size());
        return "tech";
    }

    @PostMapping("/cart/add")
    public String addToCart(HttpSession session, @RequestParam int courseId) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }

        Course course = findCourseById(courseId);
        if (course != null) {
            List<Course> cart = getCart(session);
            if (cart.stream().noneMatch(item -> item.getId() == courseId)) {
                cart.add(course);
            }
        }
        return "redirect:/courses?message=Course+added+to+cart.";
    }

    @GetMapping("/cart")
    public String cart(HttpSession session, Model model, @RequestParam(required = false) String message) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        List<Course> cart = getCart(session);
        model.addAttribute("user", user);
        model.addAttribute("cartItems", cart);
        model.addAttribute("cartCount", cart.size());
        model.addAttribute("cartTotal", cart.stream().mapToDouble(Course::getPrice).sum());
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(HttpSession session, @RequestParam int courseId) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        List<Course> cart = getCart(session);
        cart.removeIf(item -> item.getId() == courseId);
        return "redirect:/cart?message=Course+removed+from+cart.";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/?authMode=login";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        User currentUser = (User) user;
        model.addAttribute("user", currentUser);
        model.addAttribute("cartCount", getCart(session).size());
        
        // Add image data if it exists
        if (currentUser.getProfileImage() != null && currentUser.getProfileImage().length > 0) {
            String imageBase64 = Base64.getEncoder().encodeToString(currentUser.getProfileImage());
            model.addAttribute("hasProfileImage", true);
            model.addAttribute("profileImageBase64", imageBase64);
        }
        
        return "profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(HttpSession session, @RequestParam String firstName, 
                               @RequestParam String lastName, @RequestParam String email,
                               Model model) {
        var user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        try {
            User updatedUser = userService.updateUser(user.getId(), firstName, lastName, email);
            session.setAttribute("loggedInUser", updatedUser);
            model.addAttribute("user", updatedUser);
            model.addAttribute("message", "Profile updated successfully!");
            model.addAttribute("cartCount", getCart(session).size());
            
            // Add image data if it exists
            if (updatedUser.getProfileImage() != null && updatedUser.getProfileImage().length > 0) {
                String imageBase64 = Base64.getEncoder().encodeToString(updatedUser.getProfileImage());
                model.addAttribute("hasProfileImage", true);
                model.addAttribute("profileImageBase64", imageBase64);
            }
            
            return "profile";
        } catch (Exception e) {
            model.addAttribute("user", user);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cartCount", getCart(session).size());
            return "profile";
        }
    }

    @PostMapping("/uploadProfileImage")
    public String uploadProfileImage(HttpSession session, @RequestParam("profileImageFile") MultipartFile file, Model model) {
        var user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        try {
            if (!file.isEmpty()) {
                byte[] imageData = file.getBytes();
                User updatedUser = userService.updateProfileImage(user.getId(), imageData);
                session.setAttribute("loggedInUser", updatedUser);
                model.addAttribute("user", updatedUser);
                model.addAttribute("message", "Profile image uploaded successfully!");
                
                // Add image data to display
                String imageBase64 = Base64.getEncoder().encodeToString(updatedUser.getProfileImage());
                model.addAttribute("hasProfileImage", true);
                model.addAttribute("profileImageBase64", imageBase64);
            } else {
                model.addAttribute("error", "Please select an image file.");
                model.addAttribute("user", user);
            }
            model.addAttribute("cartCount", getCart(session).size());
            return "profile";
        } catch (IOException e) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Error uploading image: " + e.getMessage());
            model.addAttribute("cartCount", getCart(session).size());
            return "profile";
        } catch (Exception e) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Error: " + e.getMessage());
            model.addAttribute("cartCount", getCart(session).size());
            return "profile";
        }
    }

    @SuppressWarnings("unchecked")
    private List<Course> getCart(HttpSession session) {
        List<Course> cart = (List<Course>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private Course findCourseById(int courseId) {
        return COURSE_CATALOG.stream().filter(course -> course.getId() == courseId).findFirst().orElse(null);
    }

    private void populateHomeModel(HttpSession session, Model model) {
        var user = session.getAttribute("loggedInUser");
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("cartCount", getCart(session).size());
        }
    }
}