package com.training.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TutorialController {

    @GetMapping("/tutorials")
    public String tutorialsPage(HttpSession session, Model model) {
        model.addAttribute("cartCount", getCartCount(session));
        return "tutorials";
    }

    private int getCartCount(HttpSession session) {
        List<?> cart = (List<?>) session.getAttribute("cart");
        return cart != null ? cart.size() : 0;
    }
}
