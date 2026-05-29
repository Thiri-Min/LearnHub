package com.training.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    public static final String SESSION_VNPAY_TOKEN = "vnpayFormToken";
    public static final String SESSION_VNPAY_VERIFIED = "vnpayPaymentVerified";
    public static final String SESSION_VNPAY_AMOUNT = "vnpayPaymentAmount";
    public static final String SESSION_VNPAY_BILLING_NAME = "vnpayBillingName";
    public static final String SESSION_VNPAY_BILLING_EMAIL = "vnpayBillingEmail";

    @Autowired
    private VnPayTokenService vnPayTokenService;

    @GetMapping("/vnpay/status")
    public Map<String, Object> vnpayStatus(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        String token = (String) session.getAttribute(SESSION_VNPAY_TOKEN);
        if (token != null) {
            vnPayTokenService.find(token).ifPresent(data -> {
                if (data.isConfirmed()) {
                    session.setAttribute(SESSION_VNPAY_VERIFIED, data.getVerifiedOrderRef());
                    session.setAttribute(SESSION_VNPAY_AMOUNT, data.getConfirmedAmount());
                    session.setAttribute(SESSION_VNPAY_BILLING_NAME, data.getBillingName());
                    session.setAttribute(SESSION_VNPAY_BILLING_EMAIL, data.getBillingEmail());
                }
            });
        }

        Object verifiedRef = session.getAttribute(SESSION_VNPAY_VERIFIED);
        boolean confirmed = verifiedRef != null;
        response.put("confirmed", confirmed);
        if (confirmed) {
            response.put("orderRef", verifiedRef);
            response.put("amount", session.getAttribute(SESSION_VNPAY_AMOUNT));
            response.put("billingName", session.getAttribute(SESSION_VNPAY_BILLING_NAME));
            response.put("billingEmail", session.getAttribute(SESSION_VNPAY_BILLING_EMAIL));
        }
        return response;
    }
}
