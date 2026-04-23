package com.example.demo.observer;

public class MobileAppObserver implements Observer {
    private final String userId;

    public MobileAppObserver(String userId) {
        this.userId = userId;
    }

    @Override
    public void update(String symbol, double oldPrice, double newPrice) {
        System.out.printf("  [MobileApp - %s] Price refreshed: %s"
                + " Rs.%.2f%n", userId, symbol, newPrice);
    }
}
