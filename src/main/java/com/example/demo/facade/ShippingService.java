package com.example.demo.facade;

public class ShippingService {
    public String createShipment(String userId, String productId) {
        String trackingId = "TRACK-" + productId + "-" + userId;
        System.out.println("  [Shipping] Shipment created. Tracking ID: "
                + trackingId);
        return trackingId;
    }

    public String getTrackingStatus(String trackingId) {
        System.out.println("  [Shipping] Getting status for: " + trackingId);
        return "In Transit — Expected delivery: 3 days";
    }

    public void cancelShipment(String trackingId) {
        System.out.println("  [Shipping] Cancelling shipment: " + trackingId);
    }
}
