package com.training.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/payment")
public class VnPayFormController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private VnPayTokenService vnPayTokenService;

    @GetMapping("/vnpay/scan")
    public String scanRedirect(@RequestParam("t") String token) {
        return "redirect:/payment/vnpay-form?t=" + token;
    }

    @GetMapping("/vnpay-form")
    public String vnpayForm(@RequestParam(value = "t", required = false) String token,
                            @RequestParam(required = false, defaultValue = "false") boolean submitted,
                            Model model) {
        if (token == null || token.isBlank()) {
            return "redirect:/cart?message=Please+scan+the+VNPay+QR+code+to+open+the+billing+form.";
        }

        var tokenDataOpt = vnPayTokenService.find(token);
        if (tokenDataOpt.isEmpty()) {
            return "redirect:/cart?message=Payment+link+expired.+Please+refresh+your+cart+and+scan+again.";
        }
        var tokenData = tokenDataOpt.get();

        model.addAttribute("cartTotal", tokenData.getCartTotal());
        model.addAttribute("vnpayOrderRef", tokenData.getOrderRef());
        model.addAttribute("formToken", token);
        model.addAttribute("submitted", submitted);
        return "vnpay-form";
    }

    @PostMapping("/vnpay-form")
    public String submitVnpayForm(@RequestParam("t") String token,
                                  @RequestParam String billingName,
                                  @RequestParam String billingEmail,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        var tokenDataOpt = vnPayTokenService.find(token);
        if (tokenDataOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "This payment link is invalid or has expired.");
            return "redirect:/cart";
        }
        var tokenData = tokenDataOpt.get();

        try {
            VnPaySessionConfirmResult result = paymentService.confirmVnpayBilling(
                    billingName, billingEmail, tokenData.getCartTotal());
            vnPayTokenService.confirm(token, result.getBillingName(), result.getBillingEmail(),
                    result.getOrderRef(), tokenData.getCartTotal());
            applyTokenToSession(session, token);
            return "redirect:/payment/vnpay-form?t=" + token + "&submitted=1";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/payment/vnpay-form?t=" + token;
        }
    }

    private void applyTokenToSession(HttpSession session, String token) {
        vnPayTokenService.find(token).ifPresent(data -> {
            if (!data.isConfirmed()) {
                return;
            }
            session.setAttribute(PaymentController.SESSION_VNPAY_TOKEN, token);
            session.setAttribute(PaymentController.SESSION_VNPAY_VERIFIED, data.getVerifiedOrderRef());
            session.setAttribute(PaymentController.SESSION_VNPAY_AMOUNT, data.getConfirmedAmount());
            session.setAttribute(PaymentController.SESSION_VNPAY_BILLING_NAME, data.getBillingName());
            session.setAttribute(PaymentController.SESSION_VNPAY_BILLING_EMAIL, data.getBillingEmail());
        });
    }
}
