package com.training.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private ChatPresenceService chatPresenceService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private VnPayTokenService vnPayTokenService;

    @Autowired
    private AppUrlResolver appUrlResolver;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private CourseService courseService;

    @Value("${app.public-url:}")
    private String configuredPublicUrl;

    @GetMapping("/login")
    public String login(@RequestParam(required = false, defaultValue = "login") String mode,
                        @RequestParam(required = false) String signupSuccess,
                        RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("authMode", mode);
        if (signupSuccess != null) {
            redirectAttributes.addAttribute("signupSuccess", signupSuccess);
        }
        return "redirect:/";
    }
    
    @PostMapping("/login")
    public String loginUser(@RequestParam String emailOrUsername, @RequestParam String password, Model model,
                            HttpSession session, HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {
        String loginId = emailOrUsername == null ? "" : emailOrUsername.trim();
        String loginPassword = password == null ? "" : password.trim();
        var userOpt = userService.findByEmailOrUsername(loginId);
        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(loginPassword)) {
            populateHomeModel(session, model);
            model.addAttribute("authMode", "login");
            model.addAttribute("loginError", "Invalid email/username or password. Please try again.");
            model.addAttribute("loginEmailOrUsername", loginId);
            return "home";
        }

        User loggedIn = userActivityService.recordLogin(userOpt.get(), request);
        loggedIn = userService.findById(loggedIn.getId()).orElse(loggedIn);
        session.setAttribute("loggedInUser", loggedIn);
        chatPresenceService.markActive(loggedIn);
        redirectAttributes.addFlashAttribute("trackLocation", true);
        redirectAttributes.addFlashAttribute("gaTrackLoginSuccess", true);
        session.setAttribute("pendingLocationTrack", Boolean.TRUE);
        session.setAttribute("pendingGaLoginTrack", Boolean.TRUE);
        return "redirect:/home";
    }

    @GetMapping("/signup")
    public String signup() {
        return "redirect:/?authMode=signup";
    }
    
    @PostMapping("/signup")
    public String signupUser(@RequestParam String firstName, @RequestParam String lastName,
                            @RequestParam String username, @RequestParam String email,
                            @RequestParam String password, @RequestParam String confirmPassword,
                            Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            if (!password.equals(confirmPassword)) {
                populateHomeModel(session, model);
                model.addAttribute("authMode", "signup");
                model.addAttribute("signupError", "Passwords do not match!");
                preserveSignupForm(model, firstName, lastName, username, email);
                return "home";
            }

            userService.registerUser(firstName, lastName, username, email, password);
            redirectAttributes.addAttribute("authMode", "login");
            redirectAttributes.addAttribute("signupSuccess", "Account created successfully. Sign in with your email or username.");
            return "redirect:/";
        } catch (Exception e) {
            populateHomeModel(session, model);
            model.addAttribute("authMode", "signup");
            model.addAttribute("signupError", e.getMessage());
            preserveSignupForm(model, firstName, lastName, username, email);
            return "home";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "redirect:/?authMode=login";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String emailOrUsername,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes redirectAttributes) {
        try {
            userService.resetPasswordByIdentifier(emailOrUsername, newPassword, confirmPassword);
            redirectAttributes.addAttribute("authMode", "login");
            redirectAttributes.addAttribute("signupSuccess", "Password reset successful. Please sign in with your new password.");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("showResetModal", true);
            redirectAttributes.addFlashAttribute("resetError", e.getMessage());
            redirectAttributes.addFlashAttribute("resetEmailOrUsername", emailOrUsername);
            redirectAttributes.addAttribute("authMode", "login");
            return "redirect:/";
        }
    }

    private void preserveSignupForm(Model model, String firstName, String lastName, String username, String email) {
        model.addAttribute("signupFirstName", firstName);
        model.addAttribute("signupLastName", lastName);
        model.addAttribute("signupUsername", username);
        model.addAttribute("signupEmail", email);
    }

    @GetMapping("/")
    public String landing(HttpSession session, Model model,
                          @RequestParam(required = false) String authMode,
                          @RequestParam(required = false) String signupSuccess) {
        populateHomeModel(session, model);
        if (authMode != null) {
            model.addAttribute("authMode", authMode);
        }
        if (signupSuccess != null) {
            model.addAttribute("signupSuccess", signupSuccess);
        }
        return "home";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/?authMode=login";
        }
        populateHomeModel(session, model);
        return "home";
    }

    @GetMapping("/courses")
    public String courses(HttpSession session, Model model, @RequestParam(required = false) String message) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        model.addAttribute("user", user);
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("cartCount", getCart(session).size());
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "courses";
    }

    @GetMapping("/tech")
    public String tech(HttpSession session, Model model,
                      @RequestParam(required = false) String message) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        if (message != null && !message.isBlank()) {
            model.addAttribute("message", message);
        }
        model.addAttribute("user", user);
        model.addAttribute("cartCount", getCart(session).size());
        User currentUser = (User) user;
        model.addAttribute("dsaMaxAttempts", TechQuizCatalog.DSA_MAX_ATTEMPTS);
        model.addAttribute("dsaAttemptCounts", buildDsaAttemptCounts(currentUser.getId()));
        model.addAttribute("frontEndMaxAttempts", TechQuizCatalog.FRONTEND_MAX_ATTEMPTS);
        model.addAttribute("frontEndAttemptCounts", buildSubjectAttemptCounts(currentUser.getId(), "FrontEnd"));
        model.addAttribute("baseFrameworkMaxAttempts", TechQuizCatalog.BASE_FRAMEWORK_MAX_ATTEMPTS);
        model.addAttribute("baseFrameworkAttemptCounts", buildSubjectAttemptCounts(currentUser.getId(), "BaseFramework"));
        model.addAttribute("aiMaxAttempts", TechQuizCatalog.AI_MAX_ATTEMPTS);
        model.addAttribute("aiAttemptCounts", buildSubjectAttemptCounts(currentUser.getId(), "AI"));
        model.addAttribute("fullstackMaxAttempts", TechQuizCatalog.FULLSTACK_MAX_ATTEMPTS);
        model.addAttribute("fullstackAttemptCounts", buildSubjectAttemptCounts(currentUser.getId(), "Fullstack"));
        return "tech";
    }

    @GetMapping("/flashcard")
    public String flashcard(HttpSession session, Model model) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        User currentUser = (User) user;
        applyOptionalSession(session, model);
        model.addAttribute("skills", buildMkTestSkills(currentUser.getId()));
        return "mk-test";
    }

    @GetMapping("/fill-blank-practice")
    public String fillBlankPractice(HttpSession session) {
        return "redirect:/flashcard";
    }

    private List<Map<String, Object>> buildMkTestSkills(Long userId) {
        List<Map<String, Object>> skills = new ArrayList<>();
        skills.add(buildMkSkill("Java", "fab fa-java", "Practice Java basics, OOP, collections, and core interview concepts.", userId, "Java"));
        skills.add(buildMkSkill("DSA", "fas fa-brain", "Challenge yourself with arrays, trees, graphs, and searching patterns.", userId, "DSA"));
        skills.add(buildMkSkill("SQL", "fas fa-database", "Strengthen your query writing, joins, and database design knowledge.", userId, "SQL"));
        skills.add(buildMkSkill("Frontend", "fas fa-code", "Review HTML, CSS, JavaScript, and web UI fundamentals.", userId, "FrontEnd"));
        return skills;
    }

    private Map<String, Object> buildMkSkill(String title, String icon, String description, Long userId, String subject) {
        Map<String, Object> skill = new LinkedHashMap<>();
        skill.put("title", title);
        skill.put("icon", icon);
        skill.put("description", description);
        skill.put("levels", buildMkLevels(subject, userId));
        return skill;
    }

    private List<Map<String, Object>> buildMkLevels(String subject, Long userId) {
        List<Map<String, Object>> levels = new ArrayList<>();
        for (String level : List.of("Pre-Intermediate", "Intermediate", "Advanced")) {
            boolean disabled = isMkLevelDisabled(subject, userId, level);
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("label", level);
            item.put("disabled", disabled);
            item.put("subject", subject);
            item.put("level", level);
            item.put("attemptCount", getMkAttemptCount(userId, subject, level));
            item.put("maxAttempts", getMkMaxAttempts(subject));
            levels.add(item);
        }
        return levels;
    }

    private boolean isMkLevelDisabled(String subject, Long userId, String level) {
        int maxAttempts = getMkMaxAttempts(subject);
        return getMkAttemptCount(userId, subject, level) >= maxAttempts;
    }

    private int getMkAttemptCount(Long userId, String subject, String level) {
        return Math.toIntExact(quizAttemptRepository.countByUserIdAndSubjectAndLevel(userId, normalizeMkSubject(subject), level));
    }

    private int getMkMaxAttempts(String subject) {
        return switch (subject) {
            case "DSA" -> TechQuizCatalog.DSA_MAX_ATTEMPTS;
            case "FrontEnd" -> TechQuizCatalog.FRONTEND_MAX_ATTEMPTS;
            default -> 999;
        };
    }

    private String normalizeMkSubject(String subject) {
        return subject == null ? "" : subject.trim();
    }

    private Map<String, Long> buildDsaAttemptCounts(Long userId) {
        return buildSubjectAttemptCounts(userId, "DSA");
    }

    private Map<String, Long> buildSubjectAttemptCounts(Long userId, String subject) {
        Map<String, Long> counts = new LinkedHashMap<>();
        for (String level : List.of("Pre-Intermediate", "Intermediate", "Advanced")) {
            counts.put(level, quizAttemptRepository.countByUserIdAndSubjectAndLevel(userId, subject, level));
        }
        return counts;
    }

    @GetMapping("/rich-content")
    public String richContent(HttpSession session, Model model) {
        applyOptionalSession(session, model);
        model.addAttribute("topics", TechContentCatalog.getAllTopics());
        return "rich-content";
    }

    @GetMapping("/rich-content/detail")
    public String richContentDetail(@RequestParam String topic, HttpSession session, Model model) {
        var techTopic = TechContentCatalog.findById(topic);
        if (techTopic.isEmpty()) {
            return "redirect:/rich-content";
        }
        TechTopic tech = techTopic.get();
        var user = session.getAttribute("loggedInUser");
        if (user instanceof User currentUser) {
            userActivityService.recordRichContentView(currentUser.getId(), tech.getId(), tech.getTitle());
        }
        applyOptionalSession(session, model);
        model.addAttribute("topic", tech);
        return "rich-content-detail";
    }

    @GetMapping("/my-progress")
    public String myProgress(HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/?authMode=login";
        }
        return "redirect:/profile?tab=progress";
    }

    @PostMapping("/api/track/location")
    @ResponseBody
    public Map<String, String> trackLoginLocation(@RequestParam(required = false) String location,
                                                  HttpSession session, HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("status", "unauthorized");
            return response;
        }
        String resolved = location;
        if (resolved == null || resolved.isBlank() || resolved.startsWith("Unknown")) {
            resolved = userActivityService.resolveCountryFromRequest(request);
        }
        userActivityService.updateLatestLoginLocation(((User) user).getId(), resolved);
        session.removeAttribute("pendingLocationTrack");
        response.put("status", "ok");
        response.put("location", resolved);
        return response;
    }

    @PostMapping("/api/favorites/sync")
    @ResponseBody
    public Map<String, String> syncFavorites(@RequestParam String courseIds, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("status", "unauthorized");
            return response;
        }
        List<String> ids = parseCourseIdList(courseIds);
        userActivityService.syncFavorites(((User) user).getId(), ids);
        response.put("status", "ok");
        return response;
    }

    @PostMapping("/api/favorites/toggle")
    @ResponseBody
    public Map<String, Object> toggleFavoriteApi(@RequestParam String courseId,
                                                 @RequestParam(required = false) String courseTitle,
                                                 HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("status", "unauthorized");
            return response;
        }
        Long userId = ((User) user).getId();
        userActivityService.toggleFavorite(userId, courseId, courseTitle);
        boolean favorited = userFavoriteRepository.findByUserIdAndCourseId(userId, courseId.trim()).isPresent();
        response.put("status", "ok");
        response.put("favorited", favorited);
        return response;
    }

    @GetMapping("/course-detail")
    public String courseDetail(@RequestParam String courseId, HttpSession session, Model model) {
        applyOptionalSession(session, model);
        model.addAttribute("courseId", courseId);
        return "course-detail";
    }

    @GetMapping("/quiz")
    public String quiz(@RequestParam String subject,
                       @RequestParam String level,
                       @RequestParam(defaultValue = "false") boolean fresh,
                       HttpSession session,
                       Model model) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        User currentUser = (User) user;
        if (hasAttemptLimit(subject)) {
            long attemptsUsed = quizAttemptRepository.countByUserIdAndSubjectAndLevel(
                    currentUser.getId(), normalizeLimitedSubject(subject), level);
            if (fresh && attemptsUsed >= maxAttemptsForSubject(subject)) {
                return "redirect:/tech";
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("cartCount", getCart(session).size());
        model.addAttribute("subject", subject);
        model.addAttribute("level", level);
        String quizSessionKey = quizSessionKey(session, subject, level);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> questions = (List<Map<String, Object>>) session.getAttribute(quizSessionKey);
        if (fresh || questions == null) {
            questions = TechQuizCatalog.getRandomizedQuestions(subject, level, TechQuizCatalog.QUIZ_QUESTION_COUNT);
            session.setAttribute(quizSessionKey, questions);
        }
        model.addAttribute("questions", questions);
        return "quiz";
    }

    @PostMapping("/quiz/submit")
    @ResponseBody
    public Map<String, Object> submitQuizResult(@RequestParam String subject,
                                                @RequestParam String level,
                                                @RequestParam int score,
                                                @RequestParam int total,
                                                @RequestParam int percentage,
                                                @RequestParam String grade,
                                                @RequestParam(defaultValue = "false") boolean timeUp,
                                                HttpSession session) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return Map.of("ok", false, "error", "Not logged in");
        }
        User current = (User) user;
        if (hasAttemptLimit(subject)) {
            long attemptsUsed = quizAttemptRepository.countByUserIdAndSubjectAndLevel(
                    current.getId(), normalizeLimitedSubject(subject), level);
            if (attemptsUsed >= maxAttemptsForSubject(subject)) {
                return Map.of("ok", false, "error", "This test is not available right now.");
            }
        }
        adminService.saveQuizAttempt(current.getId(), subject, level, score, total, percentage, grade, timeUp);
        session.removeAttribute(quizSessionKey(session, subject, level));
        return Map.of("ok", true);
    }

    private static boolean isDsaQuiz(String subject) {
        return subject != null && "DSA".equalsIgnoreCase(subject.trim());
    }

    private static boolean isFrontEndQuiz(String subject) {
        return subject != null && "FrontEnd".equalsIgnoreCase(subject.trim());
    }

    private static boolean isBaseFrameworkQuiz(String subject) {
        return subject != null && "BaseFramework".equalsIgnoreCase(subject.trim());
    }

    private static boolean isAiQuiz(String subject) {
        return subject != null && "AI".equalsIgnoreCase(subject.trim());
    }

    private static boolean isFullstackQuiz(String subject) {
        return subject != null && "Fullstack".equalsIgnoreCase(subject.trim());
    }

    private static boolean hasAttemptLimit(String subject) {
        return isDsaQuiz(subject) || isFrontEndQuiz(subject) || isBaseFrameworkQuiz(subject)
                || isAiQuiz(subject) || isFullstackQuiz(subject);
    }

    private static int maxAttemptsForSubject(String subject) {
        if (isFrontEndQuiz(subject)) {
            return TechQuizCatalog.FRONTEND_MAX_ATTEMPTS;
        }
        if (isBaseFrameworkQuiz(subject)) {
            return TechQuizCatalog.BASE_FRAMEWORK_MAX_ATTEMPTS;
        }
        if (isAiQuiz(subject)) {
            return TechQuizCatalog.AI_MAX_ATTEMPTS;
        }
        if (isFullstackQuiz(subject)) {
            return TechQuizCatalog.FULLSTACK_MAX_ATTEMPTS;
        }
        return TechQuizCatalog.DSA_MAX_ATTEMPTS;
    }

    private static String normalizeLimitedSubject(String subject) {
        if (isFrontEndQuiz(subject)) {
            return "FrontEnd";
        }
        if (isBaseFrameworkQuiz(subject)) {
            return "BaseFramework";
        }
        if (isAiQuiz(subject)) {
            return "AI";
        }
        if (isFullstackQuiz(subject)) {
            return "Fullstack";
        }
        return "DSA";
    }

    private static String quizSessionKey(HttpSession session, String subject, String level) {
        Object user = session.getAttribute("loggedInUser");
        long userId = user instanceof User u ? u.getId() : 0L;
        return "quizQuestions:" + userId + ":" + subject + ":" + level;
    }

    private static String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    @PostMapping("/cart/add")
    public String addToCart(HttpSession session, @RequestParam long courseId) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }

        Course course = findCourseById(courseId);
        if (course != null) {
            List<Course> cart = getCart(session);
            if (cart.stream().noneMatch(item -> item.getId() == courseId)) {
                cart.add(course);
            }
        }
        return "redirect:/courses?message=Course+added+successfully.+Proceed+to+checkout+when+you+are+ready.";
    }

    @GetMapping("/cart")
    public String cart(HttpSession session, Model model, HttpServletRequest request,
                       @RequestParam(required = false) String message) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        List<Course> cart = getCart(session);
        User currentUser = (User) user;
        double cartTotal = cart.stream().mapToDouble(Course::getPrice).sum();

        String formToken = (String) session.getAttribute(PaymentController.SESSION_VNPAY_TOKEN);
        Optional<VnPayTokenService.VnPayTokenData> existingToken =
                formToken != null ? vnPayTokenService.find(formToken) : Optional.empty();

        String orderRef;
        if (existingToken.isPresent() && existingToken.get().isConfirmed()) {
            orderRef = existingToken.get().getVerifiedOrderRef();
        } else if (existingToken.isPresent()) {
            orderRef = existingToken.get().getOrderRef();
        } else {
            orderRef = paymentService.generateOrderCode();
            formToken = vnPayTokenService.createToken(currentUser.getId(), cartTotal, orderRef);
            session.setAttribute(PaymentController.SESSION_VNPAY_TOKEN, formToken);
        }

        String baseUrl = appUrlResolver.resolvePublicBaseUrl(request, configuredPublicUrl);
        String vnpayFormUrl = baseUrl + "/payment/vnpay/scan?t=" + formToken;
        model.addAttribute("user", user);
        model.addAttribute("cartItems", cart);
        model.addAttribute("cartCount", cart.size());
        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("vnpayOrderRef", orderRef);
        model.addAttribute("vnpayFormUrl", vnpayFormUrl);
        model.addAttribute("vnpayFormToken", formToken);
        model.addAttribute("vnpayQrUrl", "https://api.qrserver.com/v1/create-qr-code/?size=180x180&data="
                + URLEncoder.encode(vnpayFormUrl, StandardCharsets.UTF_8));

        Object verifiedRef = session.getAttribute(PaymentController.SESSION_VNPAY_VERIFIED);
        model.addAttribute("vnpayConfirmed", verifiedRef != null);
        if (verifiedRef != null) {
            model.addAttribute("vnpayBillingName", session.getAttribute(PaymentController.SESSION_VNPAY_BILLING_NAME));
            model.addAttribute("vnpayBillingEmail", session.getAttribute(PaymentController.SESSION_VNPAY_BILLING_EMAIL));
        }
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "cart";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session,
                           @RequestParam String paymentMethod,
                           @RequestParam String billingName,
                           @RequestParam String billingAddress,
                           @RequestParam(required = false) String cardNumber,
                           @RequestParam(required = false) String cardExpiry,
                           @RequestParam(required = false) String cardCvv,
                           Model model) {
        Map<String, Object> result = processCheckout(session, paymentMethod, billingName, billingAddress,
                cardNumber, cardExpiry, cardCvv);
        if (!(Boolean) result.get("success")) {
            return "redirect:/cart?message=" + encodeRedirectMessage((String) result.get("message"));
        }

        User currentUser = (User) session.getAttribute("loggedInUser");
        @SuppressWarnings("unchecked")
        List<Course> orderItems = (List<Course>) result.get("orderItems");
        model.addAttribute("user", currentUser);
        model.addAttribute("cartCount", 0);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("orderTotal", result.get("orderTotal"));
        model.addAttribute("paymentMethod", result.get("paymentLabel"));
        model.addAttribute("paymentSummary", result.get("paymentSummary"));
        model.addAttribute("billingName", billingName);
        model.addAttribute("billingAddress", billingAddress);
        model.addAttribute("userEmail", currentUser.getEmail());
        model.addAttribute("orderMessage", result.get("orderMessage"));
        model.addAttribute("showOtpOnDashboard", result.get("showOtpOnDashboard"));
        return "order-confirmation";
    }

    @PostMapping("/api/checkout")
    @ResponseBody
    public Map<String, Object> apiCheckout(HttpSession session,
                                           @RequestParam String paymentMethod,
                                           @RequestParam String billingName,
                                           @RequestParam(required = false) String billingAddress,
                                           @RequestParam(required = false) String billingEmail,
                                           @RequestParam(required = false) String cardNumber,
                                           @RequestParam(required = false) String cardExpiry,
                                           @RequestParam(required = false) String cardCvv) {
        String billingDetail = "qrpay".equals(paymentMethod)
                ? (billingEmail != null ? billingEmail : "")
                : (billingAddress != null ? billingAddress : "");
        Map<String, Object> result = processCheckout(session, paymentMethod, billingName, billingDetail,
                cardNumber, cardExpiry, cardCvv);
        if ((Boolean) result.get("success")) {
            result.put("message", "Thank you! Your courses are now in your account. Open Courses anytime to start learning.");
        }
        return result;
    }

    private Map<String, Object> processCheckout(HttpSession session,
                                                String paymentMethod,
                                                String billingName,
                                                String billingDetail,
                                                String cardNumber,
                                                String cardExpiry,
                                                String cardCvv) {
        Map<String, Object> result = new HashMap<>();
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            result.put("success", false);
            result.put("message", "Please log in to continue.");
            return result;
        }

        User currentUser = (User) user;
        List<Course> cart = getCart(session);
        if (cart.isEmpty()) {
            result.put("success", false);
            result.put("message", "Your cart is empty.");
            return result;
        }

        double total = cart.stream().mapToDouble(Course::getPrice).sum();
        String paymentSummary;
        String paymentLabel;
        boolean isVnpay = "qrpay".equals(paymentMethod);
        if ("card".equals(paymentMethod)) {
            paymentSummary = "Paid by Card ending " + (cardNumber != null && cardNumber.length() >= 4 ? cardNumber.substring(cardNumber.length() - 4) : "xxxx") + ".";
            paymentLabel = "Card";
        } else if (isVnpay) {
            Object verifiedRef = session.getAttribute(PaymentController.SESSION_VNPAY_VERIFIED);
            Object verifiedAmount = session.getAttribute(PaymentController.SESSION_VNPAY_AMOUNT);
            if (verifiedRef == null || verifiedAmount == null || Math.abs(((Number) verifiedAmount).doubleValue() - total) > 0.01) {
                result.put("success", false);
                result.put("message", "Please scan the VNPay QR and confirm payment before placing your order.");
                return result;
            }
            paymentSummary = "VNPay QR payment confirmed. Reference: " + verifiedRef + ".";
            paymentLabel = "VNPay QR";
        } else {
            result.put("success", false);
            result.put("message", "Please select a valid payment method.");
            return result;
        }

        String successMessage = "Order successfully placed for " + currentUser.getFirstName() + " (" + currentUser.getEmail() + ") using " + paymentLabel + ".";
        if (isVnpay) {
            paymentService.createVnpayOrder(currentUser, total, billingName, billingDetail, cart);
            successMessage += " Your payment OTP is available on your Profile dashboard.";
            session.removeAttribute(PaymentController.SESSION_VNPAY_VERIFIED);
            session.removeAttribute(PaymentController.SESSION_VNPAY_AMOUNT);
            session.removeAttribute(PaymentController.SESSION_VNPAY_BILLING_NAME);
            session.removeAttribute(PaymentController.SESSION_VNPAY_BILLING_EMAIL);
            String formToken = (String) session.getAttribute(PaymentController.SESSION_VNPAY_TOKEN);
            vnPayTokenService.remove(formToken);
            session.removeAttribute(PaymentController.SESSION_VNPAY_TOKEN);
        }

        List<Course> orderItems = new ArrayList<>(cart);
        cart.clear();
        session.setAttribute("lastOrderMessage", successMessage);

        result.put("success", true);
        result.put("orderItems", orderItems);
        result.put("orderTotal", total);
        result.put("paymentSummary", paymentSummary);
        result.put("paymentLabel", paymentLabel);
        result.put("orderMessage", successMessage);
        result.put("showOtpOnDashboard", isVnpay);
        return result;
    }

    private static String encodeRedirectMessage(String message) {
        return URLEncoder.encode(message, StandardCharsets.UTF_8).replace("+", "%20");
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(HttpSession session, @RequestParam long courseId) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        List<Course> cart = getCart(session);
        cart.removeIf(item -> item.getId() == courseId);
        return "redirect:/cart?message=Course+removed+from+cart.";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        Object user = session.getAttribute("loggedInUser");
        if (user instanceof User currentUser) {
            chatPresenceService.markOffline(currentUser);
        }
        session.invalidate();
        return "redirect:/?authMode=login";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model,
                          @RequestParam(required = false, defaultValue = "favorites") String tab) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        User currentUser = userService.findById(((User) user).getId()).orElse((User) user);
        session.setAttribute("loggedInUser", currentUser);
        populateProfileModel(session, model, currentUser);
        String activeTab = "dashboard".equals(tab) ? "dashboard"
                : "progress".equals(tab) ? "progress" : "favorites";
        model.addAttribute("activeTab", activeTab);
        model.addAttribute("lastOrderMessage", session.getAttribute("lastOrderMessage"));
        model.addAttribute("paymentOrders", paymentService.getOrdersForUser(currentUser.getId()));
        return "profile";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(HttpSession session,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes redirectAttributes) {
        var user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        try {
            if (!newPassword.equals(confirmPassword)) {
                throw new Exception("New passwords do not match.");
            }
            User updatedUser = userService.updatePassword(user.getId(), currentPassword, newPassword);
            session.setAttribute("loggedInUser", updatedUser);
            redirectAttributes.addFlashAttribute("message", "Password updated successfully!");
            return "redirect:/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/profile";
        }
    }

    @PostMapping("/updateProfile")
    public String updateProfile(HttpSession session, @RequestParam String firstName,
                               @RequestParam String lastName, @RequestParam String username,
                               @RequestParam String email, RedirectAttributes redirectAttributes) {
        var user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        try {
            User updatedUser = userService.updateUser(user.getId(), firstName, lastName, username, email);
            session.setAttribute("loggedInUser", updatedUser);
            redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
            return "redirect:/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/profile";
        }
    }

    @PostMapping("/uploadProfileImage")
    public String uploadProfileImage(HttpSession session, @RequestParam("profileImageFile") MultipartFile file, Model model) {
        var user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/?authMode=login";
        }
        try {
            if (!file.isEmpty()) {
                byte[] imageData = file.getBytes();
                User updatedUser = userService.updateProfileImage(user.getId(), imageData);
                session.setAttribute("loggedInUser", updatedUser);
                populateProfileModel(session, model, updatedUser);
                model.addAttribute("activeTab", "favorites");
                model.addAttribute("message", "Profile image uploaded successfully!");
            } else {
                populateProfileModel(session, model, user);
                model.addAttribute("activeTab", "favorites");
                model.addAttribute("error", "Please select an image file.");
            }
            return "profile";
        } catch (IOException e) {
            populateProfileModel(session, model, user);
            model.addAttribute("activeTab", "favorites");
            model.addAttribute("error", "Error uploading image: " + e.getMessage());
            return "profile";
        } catch (Exception e) {
            populateProfileModel(session, model, user);
            model.addAttribute("activeTab", "favorites");
            model.addAttribute("error", "Error: " + e.getMessage());
            return "profile";
        }
    }

    private void populateProfileModel(HttpSession session, Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("cartCount", getCart(session).size());
        Map<String, Object> progress = userActivityService.getProgressSummary(user.getId());
        model.addAttribute("loginCount", progress.get("loginCount"));
        model.addAttribute("loginEvents", progress.get("loginEvents"));
        model.addAttribute("richContentViews", progress.get("richContentViews"));
        model.addAttribute("quizAttempts", progress.get("quizAttempts"));
        model.addAttribute("favorites", progress.get("favorites"));
        model.addAttribute("paymentOrders", paymentService.getOrdersForUser(user.getId()));
        if (user.getProfileImage() != null && user.getProfileImage().length > 0) {
            String imageBase64 = Base64.getEncoder().encodeToString(user.getProfileImage());
            model.addAttribute("hasProfileImage", true);
            model.addAttribute("profileImageBase64", imageBase64);
        } else {
            model.addAttribute("hasProfileImage", false);
            model.addAttribute("profileImageBase64", "");
        }
    }

    private List<String> parseCourseIdList(String courseIds) {
        if (courseIds == null || courseIds.isBlank()) {
            return List.of();
        }
        String cleaned = courseIds.trim();
        if (cleaned.startsWith("[")) {
            cleaned = cleaned.substring(1, cleaned.length() - 1);
        }
        if (cleaned.isBlank()) {
            return List.of();
        }
        return Arrays.stream(cleaned.split(","))
                .map(id -> id.trim().replace("\"", ""))
                .filter(id -> !id.isEmpty())
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private List<Course> getCart(HttpSession session) {
        List<Course> cart = (List<Course>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private Course findCourseById(long courseId) {
        return courseService.findById(courseId);
    }

    private void populateHomeModel(HttpSession session, Model model) {
        var user = session.getAttribute("loggedInUser");
        boolean loggedIn = user != null;
        applyOptionalSession(session, model);
        model.addAttribute("featuredCourses", courseService.findFeaturedForHome(loggedIn));
    }

    private void applyOptionalSession(HttpSession session, Model model) {
        var user = session.getAttribute("loggedInUser");
        if (user != null) {
            if (!model.containsAttribute("user")) {
                model.addAttribute("user", user);
            }
            model.addAttribute("cartCount", getCart(session).size());
        }
    }
}
