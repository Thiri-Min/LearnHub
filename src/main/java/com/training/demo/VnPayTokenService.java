package com.training.demo;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VnPayTokenService {

    private static final long TOKEN_TTL_SECONDS = 3600;

    private final Map<String, VnPayTokenData> tokens = new ConcurrentHashMap<>();

    public String createToken(Long userId, double cartTotal, String orderRef) {
        purgeExpired();
        String token = UUID.randomUUID().toString().replace("-", "");
        VnPayTokenData data = new VnPayTokenData();
        data.userId = userId;
        data.cartTotal = cartTotal;
        data.orderRef = orderRef;
        data.createdAt = Instant.now();
        tokens.put(token, data);
        return token;
    }

    public Optional<VnPayTokenData> find(String token) {
        if (token == null || token.isBlank()) {
            return Optional.empty();
        }
        purgeExpired();
        VnPayTokenData data = tokens.get(token);
        if (data == null || data.isExpired()) {
            tokens.remove(token);
            return Optional.empty();
        }
        return Optional.of(data);
    }

    public void confirm(String token, String billingName, String billingEmail, String verifiedOrderRef, double amount) {
        VnPayTokenData data = tokens.get(token);
        if (data == null) {
            throw new IllegalArgumentException("This payment link is invalid or has expired.");
        }
        data.confirmed = true;
        data.billingName = billingName;
        data.billingEmail = billingEmail;
        data.verifiedOrderRef = verifiedOrderRef;
        data.confirmedAmount = amount;
    }

    public void remove(String token) {
        if (token != null) {
            tokens.remove(token);
        }
    }

    private void purgeExpired() {
        tokens.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

    public static class VnPayTokenData {
        private Long userId;
        private double cartTotal;
        private String orderRef;
        private Instant createdAt;
        private boolean confirmed;
        private String billingName;
        private String billingEmail;
        private String verifiedOrderRef;
        private double confirmedAmount;

        public boolean isExpired() {
            return createdAt == null || createdAt.plusSeconds(TOKEN_TTL_SECONDS).isBefore(Instant.now());
        }

        public Long getUserId() {
            return userId;
        }

        public double getCartTotal() {
            return cartTotal;
        }

        public String getOrderRef() {
            return orderRef;
        }

        public boolean isConfirmed() {
            return confirmed;
        }

        public String getBillingName() {
            return billingName;
        }

        public String getBillingEmail() {
            return billingEmail;
        }

        public String getVerifiedOrderRef() {
            return verifiedOrderRef;
        }

        public double getConfirmedAmount() {
            return confirmedAmount;
        }
    }
}
