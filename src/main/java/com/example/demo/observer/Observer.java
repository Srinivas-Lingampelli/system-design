package com.example.demo.observer;

public interface Observer {
    void update(String stockSymbol, double oldPrice, double newPrice);
}
