package com.training.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute
    public void addSessionUser(HttpSession session, Model model) {
        Object attr = session.getAttribute("loggedInUser");
        if (attr instanceof User user) {
            if (!model.containsAttribute("user")) {
                model.addAttribute("user", user);
            }
            model.addAttribute("isAdminUser", user.isAdmin());
        } else {
            model.addAttribute("isAdminUser", false);
        }
    }
}
