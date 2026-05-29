package com.training.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PaymentService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    public String generateOrderCode() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        int suffix = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "VNP" + timestamp + suffix;
    }

    public String generateOtp() {
        int value = SECURE_RANDOM.nextInt(1_000_000);
        return String.format("%06d", value);
    }

    public PaymentOrder createVnpayOrder(User user, double amount, String billingName,
                                         String billingAddress, List<Course> courses) {
        PaymentOrder order = new PaymentOrder();
        order.setUserId(user.getId());
        order.setOrderCode(generateOrderCode());
        order.setOtp(generateOtp());
        order.setAmount(amount);
        order.setPaymentMethod("VNPay QR");
        order.setStatus("PAID");
        order.setBillingName(billingName);
        order.setBillingAddress(billingAddress);
        order.setCourseSummary(courses.stream()
                .map(c -> c.getTitle() + " ($" + c.getPrice() + ")")
                .reduce((a, b) -> a + "; " + b)
                .orElse(""));
        order.setCreatedAt(LocalDateTime.now());
        return paymentOrderRepository.save(order);
    }

    public List<PaymentOrder> getOrdersForUser(Long userId) {
        return paymentOrderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public VnPaySessionConfirmResult confirmVnpayBilling(String billingName,
                                                         String billingEmail,
                                                         double cartTotal) {
        if (billingName == null || billingName.isBlank()) {
            throw new IllegalArgumentException("Please enter your billing name.");
        }
        if (billingEmail == null || billingEmail.isBlank()) {
            throw new IllegalArgumentException("Please enter your email.");
        }
        String trimmedEmail = billingEmail.trim();
        if (!trimmedEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }

        return new VnPaySessionConfirmResult(
                generateOrderCode(),
                cartTotal,
                billingName.trim(),
                trimmedEmail
        );
    }

    public VnPaySessionConfirmResult confirmVnpayForSession(List<Course> cart,
                                                            String billingName,
                                                            String billingEmail) {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalStateException("Your cart is empty.");
        }
        if (billingName == null || billingName.isBlank()) {
            throw new IllegalArgumentException("Please enter your billing name.");
        }
        if (billingEmail == null || billingEmail.isBlank()) {
            throw new IllegalArgumentException("Please enter your email.");
        }
        String trimmedEmail = billingEmail.trim();
        if (!trimmedEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }

        double total = cart.stream().mapToDouble(Course::getPrice).sum();
        return new VnPaySessionConfirmResult(
                generateOrderCode(),
                total,
                billingName.trim(),
                trimmedEmail
        );
    }
}
