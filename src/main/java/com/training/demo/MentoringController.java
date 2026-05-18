package com.training.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MentoringController {

    @Autowired
    private MentoringService mentoringService;

    @GetMapping("/mentoring")
    public String mentoringHub(HttpSession session, Model model) {
        User user = requireUser(session);
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        List<Mentor> mentors = mentoringService.getAllMentors();
        Map<Long, Long> availableCounts = new HashMap<>();
        for (Mentor mentor : mentors) {
            availableCounts.put(mentor.getId(), mentoringService.countAvailableSlots(mentor.getId()));
        }
        model.addAttribute("user", user);
        model.addAttribute("cartCount", getCartCount(session));
        model.addAttribute("mentors", mentors);
        model.addAttribute("availableCounts", availableCounts);
        return "mentoring";
    }

    @GetMapping("/mentoring/mentor")
    public String mentorDetail(@RequestParam Long mentorId, HttpSession session, Model model) {
        User user = requireUser(session);
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        var mentorOpt = mentoringService.getMentor(mentorId);
        if (mentorOpt.isEmpty()) {
            return "redirect:/mentoring";
        }
        model.addAttribute("user", user);
        model.addAttribute("cartCount", getCartCount(session));
        model.addAttribute("mentor", mentorOpt.get());
        model.addAttribute("slots", mentoringService.getUpcomingSlots(mentorId));
        model.addAttribute("bookings", mentoringService.getUserBookingsForMentor(user.getId(), mentorId));
        model.addAttribute("chatMessages", mentoringService.getChatMessages(user.getId(), mentorId));
        return "mentor-detail";
    }

    @PostMapping("/mentoring/book")
    public String bookSlot(@RequestParam Long slotId,
                           @RequestParam Long mentorId,
                           @RequestParam(required = false) String studentNote,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        User user = requireUser(session);
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        try {
            mentoringService.requestBooking(user.getId(), slotId, studentNote);
            redirectAttributes.addFlashAttribute("message",
                    "Booking request submitted! Your mentor will confirm the consultation slot.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/mentoring/mentor?mentorId=" + mentorId;
    }

    @PostMapping("/mentoring/chat")
    public String sendChat(@RequestParam Long mentorId,
                           @RequestParam String content,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        User user = requireUser(session);
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        try {
            mentoringService.sendUserMessage(user.getId(), mentorId, content);
            redirectAttributes.addFlashAttribute("message", "Message sent to your mentor.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/mentoring/mentor?mentorId=" + mentorId + "#chat";
    }

    private User requireUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    @SuppressWarnings("unchecked")
    private int getCartCount(HttpSession session) {
        List<Course> cart = (List<Course>) session.getAttribute("cart");
        return cart == null ? 0 : cart.size();
    }
}
