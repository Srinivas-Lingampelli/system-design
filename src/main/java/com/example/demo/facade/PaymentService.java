package com.example.demo.facade;

public class PaymentService {
    public PaymentResult processPayment(String userId, double amount) {
        System.out.println("  [Payment] Processing Rs." + amount
                + " for user: " + userId);
        // Real code calls payment gateway API
        return new PaymentResult(true, "TXN-" + System.currentTimeMillis());
    }

    public void refundPayment(String transactionId) {
        System.out.println("  [Payment] Refunding transaction: "
                + transactionId);
    }

    // Inner result class
    public static class PaymentResult {
        private final boolean success;
        private final String  transactionId;

        public PaymentResult(boolean success, String transactionId) {
            this.success       = success;
            this.transactionId = transactionId;
        }

        public boolean isSuccess()        { return success; }
        public String  getTransactionId() { return transactionId; }
    }
}


