package com.example.demo.observer;

import java.util.ArrayList;
import java.util.List;

public class StockMarket implements Subject {

    private final String        symbol;
    private double              price;
    private final List<Observer> observers = new ArrayList<>();

    public StockMarket(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.price  = initialPrice;
        System.out.println("StockMarket created: "
                + symbol + " @ Rs." + initialPrice);
    }

    // ── Subject contract ─────────────────────────────────────
    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
        System.out.println("  [+] " + observer.getClass().getSimpleName()
                + " subscribed to " + symbol);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
        System.out.println("  [-] " + observer.getClass().getSimpleName()
                + " unsubscribed from " + symbol);
    }

    @Override
    public void notifyObservers() {
        // Loops through every subscriber and calls update()
        // It does NOT know what each observer does with the data
        for (Observer observer : observers) {
            observer.update(symbol, price, price);
        }
    }

    // ── State change — triggers notification ─────────────────
    public void setPrice(double newPrice) {
        double oldPrice = this.price;
        this.price      = newPrice;

        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("  PRICE CHANGE: %s  Rs.%.2f → Rs.%.2f%n",
                symbol, oldPrice, newPrice);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // Push new price to all observers
        for (Observer observer : observers) {
            observer.update(symbol, oldPrice, newPrice);
        }
    }

    public double getPrice()  { return price; }
    public String getSymbol() { return symbol; }

}
