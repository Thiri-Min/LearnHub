package com.training.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class EnglishItController {

    @GetMapping("/english-it")
    public String englishItPage(HttpSession session, Model model) {
        model.addAttribute("units", EnglishItCatalog.getUnits());
        model.addAttribute("cefrLevels", EnglishItCatalog.getCefrLevels());
        model.addAttribute("categories", EnglishItCatalog.getCategories());
        model.addAttribute("summary", EnglishItCatalog.getSummary());
        model.addAttribute("cartCount", getCartCount(session));
        return "english-it";
    }

    @GetMapping("/english-it/unit/{id}")
    public String englishItUnitPage(@PathVariable String id, HttpSession session, Model model) {
        var unitOpt = EnglishItCatalog.findById(id);
        if (unitOpt.isEmpty()) {
            return "redirect:/english-it";
        }
        var lectureOpt = EnglishItLectureContent.getLecture(id);
        if (lectureOpt.isEmpty()) {
            return "redirect:/english-it";
        }
        model.addAttribute("unit", unitOpt.get());
        model.addAttribute("lecture", lectureOpt.get());
        model.addAttribute("cartCount", getCartCount(session));
        return "english-it-unit";
    }

    private int getCartCount(HttpSession session) {
        List<?> cart = (List<?>) session.getAttribute("cart");
        return cart != null ? cart.size() : 0;
    }
}
