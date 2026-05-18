package com.training.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

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
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model,
                            HttpSession session, HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {
        var userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            populateHomeModel(session, model);
            model.addAttribute("authMode", "login");
            model.addAttribute("loginError", "Invalid email or password. Please try again.");
            return "home";
        }

        User loggedIn = userActivityService.recordLogin(userOpt.get(), request);
        loggedIn = userService.findById(loggedIn.getId()).orElse(loggedIn);
        session.setAttribute("loggedInUser", loggedIn);
        redirectAttributes.addFlashAttribute("trackLocation", true);
        if (loggedIn.isAdmin()) {
            return "redirect:/admin";
        }
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

    @GetMapping("/rich-content")
    public String richContent(HttpSession session, Model model) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        model.addAttribute("user", user);
        model.addAttribute("cartCount", getCart(session).size());
        model.addAttribute("topics", TechContentCatalog.getAllTopics());
        return "rich-content";
    }

    @GetMapping("/rich-content/detail")
    public String richContentDetail(@RequestParam String topic, HttpSession session, Model model) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        var techTopic = TechContentCatalog.findById(topic);
        if (techTopic.isEmpty()) {
            return "redirect:/rich-content";
        }
        User currentUser = (User) user;
        TechTopic tech = techTopic.get();
        userActivityService.recordRichContentView(currentUser.getId(), tech.getId(), tech.getTitle());

        model.addAttribute("user", currentUser);
        model.addAttribute("cartCount", getCart(session).size());
        model.addAttribute("topic", tech);
        return "rich-content-detail";
    }

    @GetMapping("/my-progress")
    public String myProgress(HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/?authMode=login";
        }
        return "redirect:/profile?tab=progress";
    }

    @PostMapping("/api/track/location")
    @ResponseBody
    public Map<String, String> trackLoginLocation(@RequestParam String location, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("status", "unauthorized");
            return response;
        }
        userActivityService.updateLatestLoginLocation(((User) user).getId(), location);
        response.put("status", "ok");
        return response;
    }

    @PostMapping("/api/favorites/sync")
    @ResponseBody
    public Map<String, String> syncFavorites(@RequestParam String courseIds, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("status", "unauthorized");
            return response;
        }
        List<String> ids = parseCourseIdList(courseIds);
        userActivityService.syncFavorites(((User) user).getId(), ids);
        response.put("status", "ok");
        return response;
    }

    @PostMapping("/api/favorites/toggle")
    @ResponseBody
    public Map<String, Object> toggleFavoriteApi(@RequestParam String courseId,
                                                 @RequestParam(required = false) String courseTitle,
                                                 HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("status", "unauthorized");
            return response;
        }
        Long userId = ((User) user).getId();
        userActivityService.toggleFavorite(userId, courseId, courseTitle);
        boolean favorited = userFavoriteRepository.findByUserIdAndCourseId(userId, courseId.trim()).isPresent();
        response.put("status", "ok");
        response.put("favorited", favorited);
        return response;
    }

    @GetMapping("/course-detail")
    public String courseDetail(@RequestParam String courseId, HttpSession session, Model model) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        model.addAttribute("user", user);
        model.addAttribute("cartCount", getCart(session).size());
        model.addAttribute("courseId", courseId);
        return "course-detail";
    }

    @GetMapping("/quiz")
    public String quiz(@RequestParam String subject, @RequestParam String level, HttpSession session, Model model) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        model.addAttribute("user", user);
        model.addAttribute("cartCount", getCart(session).size());
        model.addAttribute("subject", subject);
        model.addAttribute("level", level);
        // For demo, hardcoded questions
        List<Map<String, Object>> questions = getQuestions(subject, level);
        model.addAttribute("questions", questions);
        return "quiz";
    }

    @PostMapping("/quiz/submit")
    @ResponseBody
    public Map<String, Object> submitQuizResult(@RequestParam String subject,
                                                @RequestParam String level,
                                                @RequestParam int score,
                                                @RequestParam int total,
                                                @RequestParam int percentage,
                                                @RequestParam String grade,
                                                @RequestParam(defaultValue = "false") boolean timeUp,
                                                HttpSession session) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return Map.of("ok", false, "error", "Not logged in");
        }
        User current = (User) user;
        adminService.saveQuizAttempt(current.getId(), subject, level, score, total, percentage, grade, timeUp);
        return Map.of("ok", true);
    }

    private List<Map<String, Object>> getQuestions(String subject, String level) {
        return TechQuizCatalog.getQuestions(subject, level);
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

    @PostMapping("/checkout")
    public String checkout(HttpSession session,
                           @RequestParam String paymentMethod,
                           @RequestParam String billingName,
                           @RequestParam String billingAddress,
                           @RequestParam(required = false) String cardNumber,
                           @RequestParam(required = false) String cardExpiry,
                           @RequestParam(required = false) String cardCvv,
                           Model model) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        User currentUser = (User) user;
        List<Course> cart = getCart(session);
        if (cart.isEmpty()) {
            return "redirect:/cart?message=Your+cart+is+empty.";
        }

        double total = cart.stream().mapToDouble(Course::getPrice).sum();
        String paymentSummary;
        if ("card".equals(paymentMethod)) {
            paymentSummary = "Paid by Card ending " + (cardNumber != null && cardNumber.length() >= 4 ? cardNumber.substring(cardNumber.length() - 4) : "xxxx") + ".";
        } else {
            paymentSummary = "Cash on Delivery (COD) selected.";
        }

        String successMessage = "Order successfully placed for " + currentUser.getFirstName() + " (" + currentUser.getEmail() + ") using " + (paymentMethod.equals("cod") ? "Cash on Delivery" : "Card") + ".";

        model.addAttribute("user", currentUser);
        model.addAttribute("cartCount", 0);
        model.addAttribute("orderItems", new ArrayList<>(cart));
        model.addAttribute("orderTotal", total);
        model.addAttribute("paymentMethod", paymentMethod.equals("cod") ? "Cash on Delivery" : "Card");
        model.addAttribute("paymentSummary", paymentSummary);
        model.addAttribute("billingName", billingName);
        model.addAttribute("billingAddress", billingAddress);
        model.addAttribute("userEmail", currentUser.getEmail());
        model.addAttribute("orderMessage", successMessage);
        session.setAttribute("lastOrderMessage", successMessage);

        cart.clear();
        return "order-confirmation";
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
    public String profile(HttpSession session, Model model,
                          @RequestParam(required = false, defaultValue = "favorites") String tab) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        User currentUser = userService.findById(((User) user).getId()).orElse((User) user);
        session.setAttribute("loggedInUser", currentUser);
        populateProfileModel(session, model, currentUser);
        model.addAttribute("activeTab", "progress".equals(tab) ? "progress" : "favorites");
        model.addAttribute("lastOrderMessage", session.getAttribute("lastOrderMessage"));
        return "profile";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(HttpSession session,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes redirectAttributes) {
        var user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        try {
            if (!newPassword.equals(confirmPassword)) {
                throw new Exception("New passwords do not match.");
            }
            User updatedUser = userService.updatePassword(user.getId(), currentPassword, newPassword);
            session.setAttribute("loggedInUser", updatedUser);
            redirectAttributes.addFlashAttribute("message", "Password updated successfully!");
            return "redirect:/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/profile";
        }
    }

    @PostMapping("/updateProfile")
    public String updateProfile(HttpSession session, @RequestParam String firstName,
                               @RequestParam String lastName, @RequestParam String email,
                               RedirectAttributes redirectAttributes) {
        var user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        try {
            User updatedUser = userService.updateUser(user.getId(), firstName, lastName, email);
            session.setAttribute("loggedInUser", updatedUser);
            redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
            return "redirect:/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/profile";
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
                populateProfileModel(session, model, updatedUser);
                model.addAttribute("activeTab", "favorites");
                model.addAttribute("message", "Profile image uploaded successfully!");
            } else {
                populateProfileModel(session, model, user);
                model.addAttribute("activeTab", "favorites");
                model.addAttribute("error", "Please select an image file.");
            }
            return "profile";
        } catch (IOException e) {
            populateProfileModel(session, model, user);
            model.addAttribute("activeTab", "favorites");
            model.addAttribute("error", "Error uploading image: " + e.getMessage());
            return "profile";
        } catch (Exception e) {
            populateProfileModel(session, model, user);
            model.addAttribute("activeTab", "favorites");
            model.addAttribute("error", "Error: " + e.getMessage());
            return "profile";
        }
    }

    private void populateProfileModel(HttpSession session, Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("cartCount", getCart(session).size());
        Map<String, Object> progress = userActivityService.getProgressSummary(user.getId());
        model.addAttribute("loginCount", progress.get("loginCount"));
        model.addAttribute("loginEvents", progress.get("loginEvents"));
        model.addAttribute("richContentViews", progress.get("richContentViews"));
        model.addAttribute("quizAttempts", progress.get("quizAttempts"));
        model.addAttribute("favorites", progress.get("favorites"));
        if (user.getProfileImage() != null && user.getProfileImage().length > 0) {
            String imageBase64 = Base64.getEncoder().encodeToString(user.getProfileImage());
            model.addAttribute("hasProfileImage", true);
            model.addAttribute("profileImageBase64", imageBase64);
        } else {
            model.addAttribute("hasProfileImage", false);
            model.addAttribute("profileImageBase64", "");
        }
    }

    private List<String> parseCourseIdList(String courseIds) {
        if (courseIds == null || courseIds.isBlank()) {
            return List.of();
        }
        String cleaned = courseIds.trim();
        if (cleaned.startsWith("[")) {
            cleaned = cleaned.substring(1, cleaned.length() - 1);
        }
        if (cleaned.isBlank()) {
            return List.of();
        }
        return Arrays.stream(cleaned.split(","))
                .map(id -> id.trim().replace("\"", ""))
                .filter(id -> !id.isEmpty())
                .collect(Collectors.toList());
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