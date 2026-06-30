package com.training.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @Value("${app.analytics.ga4-measurement-id:}")
    private String ga4MeasurementId;

    @Autowired
    private ChatBotService chatBotService;

    @Autowired
    private MentoringService mentoringService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addSessionUser(HttpSession session, Model model) {
        userService.syncSessionUser(session);
        Object attr = session.getAttribute("loggedInUser");
        if (attr instanceof User user) {
            if (!model.containsAttribute("user")) {
                model.addAttribute("user", user);
            }
            model.addAttribute("isAdminUser", user.isAdmin());
            MentoringService.ChatNotificationState chatState = mentoringService.getChatNotificationState(user);
            model.addAttribute("chatNotificationCount", chatState.getUnreadCount());
            model.addAttribute("chatNotificationHref", chatState.getHref());
        } else {
            model.addAttribute("isAdminUser", false);
            model.addAttribute("chatNotificationCount", 0);
            model.addAttribute("chatNotificationHref", "/mentoring");
        }
        model.addAttribute("ga4MeasurementId", ga4MeasurementId);
        model.addAttribute("aiChatEnabled", chatBotService.isEnabled());
        model.addAttribute("aiLiveEnabled", chatBotService.isLiveAiConfigured());

        if (!model.containsAttribute("trackLocation")
                && Boolean.TRUE.equals(session.getAttribute("pendingLocationTrack"))) {
            model.addAttribute("trackLocation", true);
        }
        if (!model.containsAttribute("gaTrackLoginSuccess")
                && Boolean.TRUE.equals(session.getAttribute("pendingGaLoginTrack"))) {
            model.addAttribute("gaTrackLoginSuccess", true);
            session.removeAttribute("pendingGaLoginTrack");
        }
    }
}
