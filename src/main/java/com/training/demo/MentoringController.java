package com.training.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MentoringController {

    private static final DateTimeFormatter CHAT_TIME_FORMAT = DateTimeFormatter.ofPattern("dd MMM HH:mm");

    @Autowired
    private MentoringService mentoringService;

    @Autowired
    private ChatPresenceService chatPresenceService;

    @Autowired
    private ChatReadStatusService chatReadStatusService;

    @Autowired
    private UserService userService;

    @GetMapping("/mentoring")
    public String mentoringHub(HttpSession session, Model model) {
        User user = requireUser(session);
        List<Mentor> mentors = mentoringService.getAllMentors();
        Map<Long, Long> availableCounts = new HashMap<>();
        Map<Long, List<MentorSlot>> upcomingSlots = new HashMap<>();
        int mi = 0;
        for (Mentor mentor : mentors) {
            availableCounts.put(mentor.getId(), mentoringService.countAvailableSlots(mentor.getId()));
            List<MentorSlot> slots = mentoringService.getNextAvailableSlots(mentor.getId(), 2);
            if (slots == null || slots.isEmpty()) {
                // create a lightweight placeholder slot for display (not persisted)
                slots = new ArrayList<>();
                LocalDateTime base = LocalDateTime.now().plusDays(1 + (mi % 5)).withHour(9 + (mi % 3)).withMinute(0).withSecond(0).withNano(0);
                MentorSlot placeholder = new MentorSlot();
                placeholder.setMentorId(mentor.getId());
                placeholder.setStartTime(base);
                placeholder.setEndTime(base.plusHours(1));
                placeholder.setStatus("AVAILABLE");
                slots.add(placeholder);
            }
            upcomingSlots.put(mentor.getId(), slots);
            mi++;
        }
        model.addAttribute("mentors", mentors);
        model.addAttribute("availableCounts", availableCounts);
        model.addAttribute("upcomingSlots", upcomingSlots);
        if (user != null) {
            model.addAttribute("cartCount", getCartCount(session));
        }
        return "mentoring";
    }

    @GetMapping("/mentoring/mentor")
    public String mentorDetail(@RequestParam Long mentorId,
                               @RequestParam(required = false) Long slotId,
                               @RequestParam(required = false) Long menteeId,
                               HttpSession session, Model model) {
        User user = requireUser(session);
        var mentorOpt = mentoringService.getMentor(mentorId);
        if (mentorOpt.isEmpty()) {
            return "redirect:/mentoring";
        }
        Mentor mentor = mentorOpt.get();
        model.addAttribute("mentor", mentor);
        model.addAttribute("slots", mentoringService.getUpcomingSlots(mentorId));
        model.addAttribute("preselectedSlotId", slotId);

        if (user == null) {
            model.addAttribute("bookings", List.of());
            model.addAttribute("trainerView", false);
            model.addAttribute("chatConversations", List.of());
            model.addAttribute("selectedMenteeId", null);
            model.addAttribute("chatTitle", "Chat with Mentor");
            model.addAttribute("chatPartnerOnline", false);
            model.addAttribute("chatTargetUserId", null);
            model.addAttribute("chatMessages", List.of());
            model.addAttribute("defaultStudentName", "");
            model.addAttribute("defaultStudentEmail", "");
            return "mentor-detail";
        }

        chatPresenceService.markActive(user);
        model.addAttribute("cartCount", getCartCount(session));
        model.addAttribute("bookings", mentoringService.getUserBookingsForMentor(user.getId(), mentorId));
        boolean trainerView = userService.canAccessTrainerView(user);
        List<MentoringService.ChatConversation> chatConversations = trainerView
                ? mentoringService.getChatConversations(mentorId, user)
                : List.of();
        Long selectedMenteeId = trainerView
                ? (menteeId != null ? menteeId : chatConversations.stream()
                        .findFirst()
                        .map(MentoringService.ChatConversation::getUserId)
                        .orElse(null))
                : user.getId();
        String selectedMenteeName = trainerView
                ? chatConversations.stream()
                        .filter(conversation -> conversation.getUserId().equals(selectedMenteeId))
                        .findFirst()
                        .map(MentoringService.ChatConversation::getDisplayName)
                        .orElse(null)
                : null;
        model.addAttribute("trainerView", trainerView);
        model.addAttribute("chatConversations", chatConversations);
        model.addAttribute("selectedMenteeId", selectedMenteeId);
        model.addAttribute("chatTitle", trainerView
                ? (selectedMenteeName != null ? "Chat with " + selectedMenteeName : "Chat with Mentee")
                : "Chat with Mentor");
        model.addAttribute("chatPartnerOnline", trainerView
                ? chatPresenceService.isUserOnline(selectedMenteeId)
                : chatPresenceService.isOtherSideOnline(user));
        model.addAttribute("chatTargetUserId", selectedMenteeId);
        chatReadStatusService.markRead(user, mentorId, selectedMenteeId);
        if (trainerView) {
            chatConversations = mentoringService.getChatConversations(mentorId, user);
            model.addAttribute("chatConversations", chatConversations);
        }
        MentoringService.ChatNotificationState chatState = mentoringService.getChatNotificationState(user);
        model.addAttribute("chatNotificationCount", chatState.getUnreadCount());
        model.addAttribute("chatNotificationHref", chatState.getHref());
        model.addAttribute("chatMessages", trainerView
                ? (selectedMenteeId != null ? mentoringService.getChatMessages(selectedMenteeId, mentorId) : List.of())
                : mentoringService.getChatMessages(user.getId(), mentorId));
        model.addAttribute("defaultStudentName", buildDisplayName(user));
        model.addAttribute("defaultStudentEmail", user.getEmail() != null ? user.getEmail() : "");
        return "mentor-detail";
    }

    @PostMapping("/mentoring/book")
    public String bookSlot(@RequestParam Long slotId,
                           @RequestParam Long mentorId,
                           @RequestParam String studentName,
                           @RequestParam String studentEmail,
                           @RequestParam(required = false) String studentNote,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        User user = requireUser(session);
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        try {
            mentoringService.requestBooking(user.getId(), slotId, studentName, studentEmail, studentNote);
            var mentorOpt = mentoringService.getMentor(mentorId);
            String mentorName = mentorOpt.map(Mentor::getName).orElse("your mentor");
            redirectAttributes.addFlashAttribute("registrationSuccess", true);
            redirectAttributes.addFlashAttribute("registeredMentorName", mentorName);
            return "redirect:/mentoring/mentor?mentorId=" + mentorId + "&openChat=true#chat";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/mentoring/mentor?mentorId=" + mentorId;
        }
    }

    @GetMapping(value = "/mentoring/mentor/{mentorId}/slots", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<MentorSlotDto> mentorSlots(@PathVariable Long mentorId, HttpSession session) {
        if (requireUser(session) == null) {
            return List.of();
        }
        return mentoringService.getUpcomingSlots(mentorId).stream()
                .filter(slot -> "AVAILABLE".equals(slot.getStatus()) && slot.getStartTime().isAfter(java.time.LocalDateTime.now()))
                .map(slot -> new MentorSlotDto(slot.getId(), slot.getStartTime().toString(), slot.getEndTime().toString(), slot.getStatus()))
                .toList();
    }

    @PostMapping(value = "/mentoring/book-modal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<BookingResponse> bookModal(@RequestBody BookingRequest request, HttpSession session) {
        User user = requireUser(session);
        if (user == null) {
            return ResponseEntity.ok(new BookingResponse(false, null, "Please log in to book a session."));
        }
        try {
            mentoringService.requestBooking(user.getId(), request.getSlotId(), request.getStudentName(), request.getStudentEmail(), request.getStudentNote());
            String redirectUrl = "/mentoring/mentor?mentorId=" + request.getMentorId() + "&openChat=true#chat";
            return ResponseEntity.ok(new BookingResponse(true, redirectUrl, null));
        } catch (Exception e) {
            return ResponseEntity.ok(new BookingResponse(false, null, e.getMessage()));
        }
    }

    public static class MentorSlotDto {
        private Long id;
        private String startTime;
        private String endTime;
        private String status;

        public MentorSlotDto(Long id, String startTime, String endTime, String status) {
            this.id = id;
            this.startTime = startTime;
            this.endTime = endTime;
            this.status = status;
        }

        public Long getId() {
            return id;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getStatus() {
            return status;
        }
    }

    public static class BookingRequest {
        private Long mentorId;
        private Long slotId;
        private String studentName;
        private String studentEmail;
        private String studentNote;

        public Long getMentorId() {
            return mentorId;
        }

        public void setMentorId(Long mentorId) {
            this.mentorId = mentorId;
        }

        public Long getSlotId() {
            return slotId;
        }

        public void setSlotId(Long slotId) {
            this.slotId = slotId;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getStudentEmail() {
            return studentEmail;
        }

        public void setStudentEmail(String studentEmail) {
            this.studentEmail = studentEmail;
        }

        public String getStudentNote() {
            return studentNote;
        }

        public void setStudentNote(String studentNote) {
            this.studentNote = studentNote;
        }
    }

    public static class BookingResponse {
        private boolean success;
        private String redirectUrl;
        private String error;

        public BookingResponse(boolean success, String redirectUrl, String error) {
            this.success = success;
            this.redirectUrl = redirectUrl;
            this.error = error;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public String getError() {
            return error;
        }
    }

    private String buildDisplayName(User user) {
        String first = user.getFirstName() != null ? user.getFirstName().trim() : "";
        String last = user.getLastName() != null ? user.getLastName().trim() : "";
        String full = (first + " " + last).trim();
        if (!full.isEmpty()) {
            return full;
        }
        return user.getUsername() != null ? user.getUsername() : "";
    }

    @PostMapping("/mentoring/chat")
    public String sendChat(@RequestParam Long mentorId,
                           @RequestParam(required = false) Long chatTargetUserId,
                           @RequestParam String content,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        User user = requireUser(session);
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        chatPresenceService.markActive(user);
        try {
            if (isTrainer(user)) {
                if (chatTargetUserId == null) {
                    throw new Exception("No mentee conversation found yet.");
                }
                mentoringService.sendMentorMessage(chatTargetUserId, mentorId, content);
                redirectAttributes.addFlashAttribute("message", "Message sent to your mentee.");
            } else {
                mentoringService.sendUserMessage(user.getId(), mentorId, content);
                redirectAttributes.addFlashAttribute("message", "Message sent to your mentor.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        String redirectUrl = "/mentoring/mentor?mentorId=" + mentorId;
        if (isTrainer(user) && chatTargetUserId != null) {
            redirectUrl += "&menteeId=" + chatTargetUserId;
        }
        return "redirect:" + redirectUrl + "#chat";
    }

    @GetMapping(value = "/mentoring/chat/presence", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> chatPresence(@RequestParam(required = false) Long menteeId,
                                            HttpSession session) {
        User user = requireUser(session);
        if (user == null) {
            return Map.of("online", false);
        }
        chatPresenceService.markActive(user);
        boolean online = isTrainer(user) && menteeId != null
                ? chatPresenceService.isUserOnline(menteeId)
                : chatPresenceService.isOtherSideOnline(user);
        return Map.of("online", online);
    }

    @GetMapping(value = "/mentoring/chat/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> chatMessages(@RequestParam Long mentorId,
                                            @RequestParam(required = false) Long menteeId,
                                            HttpSession session) {
        User user = requireUser(session);
        if (user == null) {
            return Map.of("messages", List.of());
        }
        chatPresenceService.markActive(user);
        Long targetUserId = isTrainer(user) ? menteeId : user.getId();
        if (targetUserId == null) {
            return Map.of("messages", List.of());
        }
        chatReadStatusService.markRead(user, mentorId, targetUserId);
        String mentorName = mentoringService.getMentor(mentorId).map(Mentor::getName).orElse("Mentor");
        List<Map<String, Object>> messages = mentoringService.getChatMessages(targetUserId, mentorId).stream()
                .map(message -> {
                    boolean mine = (isTrainer(user) && "MENTOR".equals(message.getSender()))
                            || (!isTrainer(user) && "USER".equals(message.getSender()));
                    String senderLabel = "USER".equals(message.getSender())
                            ? (isTrainer(user) ? "Mentee" : "You")
                            : (isTrainer(user) ? "You" : mentorName);
                    return Map.<String, Object>of(
                            "id", message.getId(),
                            "content", message.getContent(),
                            "mine", mine,
                            "senderLabel", senderLabel,
                            "sentAt", CHAT_TIME_FORMAT.format(message.getSentAt()),
                            "deliveryStatus", chatReadStatusService.getDeliveryStatus(message)
                    );
                })
                .toList();
        return Map.of("messages", messages);
    }

    @GetMapping(value = "/mentoring/chat/conversations", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> chatConversations(@RequestParam Long mentorId,
                                                 HttpSession session) {
        User user = requireUser(session);
        if (user == null || !isTrainer(user)) {
            return Map.of("conversations", List.of());
        }
        chatPresenceService.markActive(user);
        List<Map<String, Object>> conversations = mentoringService.getChatConversations(mentorId, user).stream()
                .map(conversation -> Map.<String, Object>of(
                        "userId", conversation.getUserId(),
                        "displayName", conversation.getDisplayName(),
                        "lastMessage", conversation.getLastMessage(),
                        "lastSentAt", CHAT_TIME_FORMAT.format(conversation.getLastSentAt()),
                        "notificationCount", conversation.getNotificationCount()
                ))
                .toList();
        return Map.of("conversations", conversations);
    }

    @GetMapping(value = "/mentoring/chat/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> chatNotifications(HttpSession session) {
        User user = requireUser(session);
        if (user == null) {
            return Map.of("unreadCount", 0, "href", "/mentoring");
        }
        MentoringService.ChatNotificationState state = mentoringService.getChatNotificationState(user);
        return Map.of("unreadCount", state.getUnreadCount(), "href", state.getHref());
    }

    private User requireUser(HttpSession session) {
        return userService.syncSessionUser(session).orElse(null);
    }

    private boolean isTrainer(User user) {
        return userService.canAccessTrainerView(user);
    }

    @SuppressWarnings("unchecked")
    private int getCartCount(HttpSession session) {
        List<Course> cart = (List<Course>) session.getAttribute("cart");
        return cart == null ? 0 : cart.size();
    }
}
