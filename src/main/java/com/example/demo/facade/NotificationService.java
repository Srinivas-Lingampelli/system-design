package com.example.demo.facade;

public class NotificationService {
    public void sendOrderConfirmation(String userId, String trackingId) {
        System.out.println("  [Notify] Sending confirmation email to user: "
                + userId + " | Tracking: " + trackingId);
    }

    public void sendCancellationNotice(String userId) {
        System.out.println("  [Notify] Sending cancellation notice to: "
                + userId);
    }

    public void sendPaymentFailureAlert(String userId) {
        System.out.println("  [Notify] Sending payment failure alert to: "
                + userId);
    }
}
