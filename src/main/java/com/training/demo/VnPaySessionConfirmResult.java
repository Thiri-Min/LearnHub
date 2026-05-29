package com.training.demo;

public class VnPaySessionConfirmResult {

    private final String orderRef;
    private final double amount;
    private final String billingName;
    private final String billingEmail;

    public VnPaySessionConfirmResult(String orderRef, double amount, String billingName, String billingEmail) {
        this.orderRef = orderRef;
        this.amount = amount;
        this.billingName = billingName;
        this.billingEmail = billingEmail;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public double getAmount() {
        return amount;
    }

    public String getBillingName() {
        return billingName;
    }

    public String getBillingEmail() {
        return billingEmail;
    }
}
