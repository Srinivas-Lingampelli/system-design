package com.example.demo.decorator;

public class PlainCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Plain Coffee";
    }

    @Override
    public double getPrice() {
        return 2.0;
    }
}
