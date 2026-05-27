package com.training.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/feedback")
    public String feedbackPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        model.addAttribute("user", user);
        model.addAttribute("defaultName", user.getFirstName() + " " + user.getLastName());
        model.addAttribute("defaultEmail", user.getEmail());
        model.addAttribute("cartCount", getCartCount(session));
        return "feedback";
    }

    @PostMapping("/feedback")
    public String submitFeedback(@RequestParam String name,
                                 @RequestParam String email,
                                 @RequestParam String message,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        User user = getLoggedInUser(session);
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        try {
            Long userId = user.getId();
            feedbackService.save(name, email, message, userId);
            redirectAttributes.addFlashAttribute("feedbackSuccess", true);
            return "redirect:/feedback";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("feedbackError", ex.getMessage());
            redirectAttributes.addFlashAttribute("formName", name);
            redirectAttributes.addFlashAttribute("formEmail", email);
            redirectAttributes.addFlashAttribute("formMessage", message);
            return "redirect:/feedback";
        }
    }

    private User getLoggedInUser(HttpSession session) {
        Object attr = session.getAttribute("loggedInUser");
        if (attr instanceof User user) {
            return user;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private int getCartCount(HttpSession session) {
        List<?> cart = (List<?>) session.getAttribute("cart");
        return cart != null ? cart.size() : 0;
    }
}
