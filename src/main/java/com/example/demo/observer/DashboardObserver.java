package com.example.demo.observer;

public class DashboardObserver implements Observer{
    private int    updateCount = 0;
    private double highestPrice;
    private double lowestPrice;

    public DashboardObserver(double initialPrice) {
        this.highestPrice = initialPrice;
        this.lowestPrice  = initialPrice;
    }

    @Override
    public void update(String symbol, double oldPrice, double newPrice) {
        updateCount++;
        if (newPrice > highestPrice) highestPrice = newPrice;
        if (newPrice < lowestPrice)  lowestPrice  = newPrice;

        System.out.printf("  [Dashboard] Update #%d | %s @ Rs.%.2f"
                        + " | High: Rs.%.2f | Low: Rs.%.2f%n",
                updateCount, symbol, newPrice, highestPrice, lowestPrice);
    }
}
