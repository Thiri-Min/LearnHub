package com.training.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    public AdminController(AdminService adminService,
                           UserRepository userRepository,
                           LoginEventRepository loginEventRepository,
                           QuizAttemptRepository quizAttemptRepository,
                           RichContentViewRepository richContentViewRepository,
                           UserFavoriteRepository userFavoriteRepository,
                           FeedbackRepository feedbackRepository) {
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.loginEventRepository = loginEventRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.richContentViewRepository = richContentViewRepository;
        this.userFavoriteRepository = userFavoriteRepository;
        this.feedbackRepository = feedbackRepository;
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

    private User requireAdmin(HttpSession session) {
        Object attr = session.getAttribute("loggedInUser");
        if (!(attr instanceof User user) || !user.isAdmin()) {
            return null;
        }
        return user;
    }
}
