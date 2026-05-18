package com.training.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String dashboard(HttpSession session, Model model) {
        User user = requireAdmin(session);
        if (user == null) {
            return "redirect:/?authMode=login";
        }

        Map<String, Object> data = adminService.getDashboardData();
        model.addAttribute("user", user);
        model.addAttribute("users", data.get("users"));
        model.addAttribute("userById", data.get("userById"));
        model.addAttribute("loginEvents", data.get("loginEvents"));
        model.addAttribute("quizAttempts", data.get("quizAttempts"));
        model.addAttribute("richContentViews", data.get("richContentViews"));
        model.addAttribute("favorites", data.get("favorites"));
        model.addAttribute("cartCount", 0);
        return "admin/dashboard";
    }

    private User requireAdmin(HttpSession session) {
        Object attr = session.getAttribute("loggedInUser");
        if (!(attr instanceof User user) || !user.isAdmin()) {
            return null;
        }
        return user;
    }
}
