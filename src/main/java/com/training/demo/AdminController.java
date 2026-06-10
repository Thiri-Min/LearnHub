package com.training.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserRepository userRepository;
    private final LoginEventRepository loginEventRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final RichContentViewRepository richContentViewRepository;
    private final UserFavoriteRepository userFavoriteRepository;
    private final FeedbackRepository feedbackRepository;
    private final CourseService courseService;

    public AdminController(AdminService adminService,
                           UserRepository userRepository,
                           LoginEventRepository loginEventRepository,
                           QuizAttemptRepository quizAttemptRepository,
                           RichContentViewRepository richContentViewRepository,
                           UserFavoriteRepository userFavoriteRepository,
                           FeedbackRepository feedbackRepository,
                           CourseService courseService) {
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.loginEventRepository = loginEventRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.richContentViewRepository = richContentViewRepository;
        this.userFavoriteRepository = userFavoriteRepository;
        this.feedbackRepository = feedbackRepository;
        this.courseService = courseService;
    }

    @GetMapping
    public String dashboard(HttpSession session,
                            Model model,
                            @RequestParam(value = "tab", required = false) String tab) {
        User user = requireAdmin(session);
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        if (user.getId() != null) {
            user = userRepository.findById(user.getId()).orElse(user);
            session.setAttribute("loggedInUser", user);
        }

        Map<String, Object> data = adminService.getDashboardData();
        model.addAttribute("user", user);
        model.addAttribute("isAdminUser", user.isAdmin());
        model.addAttribute("users", data.get("users"));
        model.addAttribute("userById", data.get("userById"));
        model.addAttribute("loginEvents", data.get("loginEvents"));
        model.addAttribute("quizAttempts", data.get("quizAttempts"));
        model.addAttribute("richContentViews", data.get("richContentViews"));
        model.addAttribute("favorites", data.get("favorites"));
        model.addAttribute("feedbacks", data.get("feedbacks"));
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("activeTab", tab == null ? "users" : tab);
        model.addAttribute("cartCount", 0);
        return "admin/dashboard";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id,
                             @RequestParam(value = "tab", defaultValue = "users") String tab,
                             HttpSession session) {
        User admin = requireAdmin(session);
        if (admin == null) return "redirect:/?authMode=login";

        if (id == null) {
            return "redirect:/admin?tab=" + tab;
        }

        if (admin.getId() != null && admin.getId().equals(id)) {
            return "redirect:/admin?tab=" + tab;
        }

        userRepository.findById(id).ifPresent(u -> {
            if (!u.isAdmin()) {
                Long userId = u.getId();
                if (userId != null) {
                    userRepository.deleteById(userId);
                }
            }
        });
        return "redirect:/admin?tab=" + tab;
    }

    @PostMapping("/users/{id}/role")
    public String changeUserRole(@PathVariable("id") Long id,
                                 @RequestParam String role,
                                 @RequestParam(value = "tab", defaultValue = "users") String tab,
                                 HttpSession session) {
        User admin = requireAdmin(session);
        if (admin == null) return "redirect:/?authMode=login";
        if (id == null || (admin.getId() != null && admin.getId().equals(id))) {
            return "redirect:/admin?tab=" + tab;
        }
        if (!ASSIGNABLE_ROLES.contains(role)) {
            return "redirect:/admin?tab=" + tab;
        }
        userRepository.findById(id).ifPresent(u -> {
            if (!u.isAdmin()) {
                u.setRole(role);
                userRepository.save(u);
            }
        });
        return "redirect:/admin?tab=" + tab;
    }

    private static final java.util.Set<String> ASSIGNABLE_ROLES = java.util.Set.of("USER", "TRAINER", "RICH_CONTENT");

    @PostMapping("/logins/{id}/delete")
    public String deleteLoginEvent(@PathVariable("id") Long id,
                                   @RequestParam(value = "tab", defaultValue = "logins") String tab,
                                   HttpSession session) {
        if (requireAdmin(session) == null) return "redirect:/?authMode=login";
        if (id == null) return "redirect:/admin?tab=" + tab;
        loginEventRepository.deleteById(id);
        return "redirect:/admin?tab=" + tab;
    }

    @PostMapping("/tests/{id}/delete")
    public String deleteQuizAttempt(@PathVariable("id") Long id,
                                    @RequestParam(value = "tab", defaultValue = "tests") String tab,
                                    HttpSession session) {
        if (requireAdmin(session) == null) return "redirect:/?authMode=login";
        if (id == null) return "redirect:/admin?tab=" + tab;
        quizAttemptRepository.deleteById(id);
        return "redirect:/admin?tab=" + tab;
    }

    @PostMapping("/progress/views/{id}/delete")
    public String deleteRichContentView(@PathVariable("id") Long id,
                                        @RequestParam(value = "tab", defaultValue = "progress") String tab,
                                        HttpSession session) {
        if (requireAdmin(session) == null) return "redirect:/?authMode=login";
        if (id == null) return "redirect:/admin?tab=" + tab;
        richContentViewRepository.deleteById(id);
        return "redirect:/admin?tab=" + tab;
    }

    @PostMapping("/progress/favorites/{id}/delete")
    public String deleteFavorite(@PathVariable("id") Long id,
                                 @RequestParam(value = "tab", defaultValue = "progress") String tab,
                                 HttpSession session) {
        if (requireAdmin(session) == null) return "redirect:/?authMode=login";
        if (id == null) return "redirect:/admin?tab=" + tab;
        userFavoriteRepository.deleteById(id);
        return "redirect:/admin?tab=" + tab;
    }

    @PostMapping("/feedback/{id}/delete")
    public String deleteFeedback(@PathVariable("id") Long id,
                                 @RequestParam(value = "tab", defaultValue = "feedback") String tab,
                                 HttpSession session) {
        if (requireAdmin(session) == null) return "redirect:/?authMode=login";
        if (id == null) return "redirect:/admin?tab=" + tab;
        feedbackRepository.deleteById(id);
        return "redirect:/admin?tab=" + tab;
    }

    @PostMapping("/courses")
    public String addCourse(@RequestParam String title,
                            @RequestParam String description,
                            @RequestParam(required = false) String icon,
                            @RequestParam double price,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        if (requireAdmin(session) == null) return "redirect:/?authMode=login";
        try {
            Course course = courseService.addCourse(title, description, icon, price);
            redirectAttributes.addFlashAttribute("courseSuccess", "Course \"" + course.getTitle() + "\" added successfully.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("courseError", ex.getMessage());
            redirectAttributes.addFlashAttribute("formCourseTitle", title);
            redirectAttributes.addFlashAttribute("formCourseDescription", description);
            redirectAttributes.addFlashAttribute("formCourseIcon", icon);
            redirectAttributes.addFlashAttribute("formCoursePrice", price);
        }
        return "redirect:/admin?tab=courses";
    }

    @PostMapping("/courses/{id}/update")
    public String updateCourse(@PathVariable("id") Long id,
                               @RequestParam String title,
                               @RequestParam String description,
                               @RequestParam(required = false) String icon,
                               @RequestParam double price,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (requireAdmin(session) == null) return "redirect:/?authMode=login";
        if (id == null) return "redirect:/admin?tab=courses";
        try {
            Course course = courseService.updateCourse(id, title, description, icon, price);
            redirectAttributes.addFlashAttribute("courseSuccess", "Course \"" + course.getTitle() + "\" updated successfully.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("courseError", ex.getMessage());
        }
        return "redirect:/admin?tab=courses";
    }

    @PostMapping("/courses/{id}/feature")
    public String toggleCourseFeatured(@PathVariable("id") Long id,
                                       @RequestParam(value = "tab", defaultValue = "courses") String tab,
                                       HttpSession session) {
        if (requireAdmin(session) == null) return "redirect:/?authMode=login";
        if (id == null) return "redirect:/admin?tab=" + tab;
        try {
            courseService.toggleFeatured(id);
        } catch (IllegalArgumentException ignored) {
            // Course was deleted in the meantime; nothing to toggle.
        }
        return "redirect:/admin?tab=" + tab;
    }

    @PostMapping("/courses/{id}/delete")
    public String deleteCourse(@PathVariable("id") Long id,
                               @RequestParam(value = "tab", defaultValue = "courses") String tab,
                               HttpSession session) {
        if (requireAdmin(session) == null) return "redirect:/?authMode=login";
        if (id == null) return "redirect:/admin?tab=" + tab;
        courseService.deleteCourse(id);
        return "redirect:/admin?tab=" + tab;
    }

    private User requireAdmin(HttpSession session) {
        Object attr = session.getAttribute("loggedInUser");
        if (!(attr instanceof User user) || !user.isAdmin()) {
            return null;
        }
        return user;
    }
}
