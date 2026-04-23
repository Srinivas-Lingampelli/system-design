package com.example.demo.observer;

public class TradingBotObserver implements Observer {
    private final double  buyBelowPrice;
    private       boolean hasBought = false;

    public TradingBotObserver(double buyBelowPrice) {
        this.buyBelowPrice = buyBelowPrice;
    }

    @Override
    public void update(String symbol, double oldPrice, double newPrice) {
        if (!hasBought && newPrice < buyBelowPrice) {
            System.out.printf("  [TradingBot] AUTO-BUY triggered!"
                            + " %s at Rs.%.2f (target was below Rs.%.2f)%n",
                    symbol, newPrice, buyBelowPrice);
            hasBought = true;
        } else {
            System.out.printf("  [TradingBot] Monitoring %s @ Rs.%.2f"
                            + " (waiting for below Rs.%.2f)%n",
                    symbol, newPrice, buyBelowPrice);
        }
    }
}
