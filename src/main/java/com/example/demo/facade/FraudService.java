package com.example.demo.facade;

public class FraudService {

    public boolean validateTransaction(String userId, double amount) {
        System.out.println("  [Fraud] Validating transaction for user: "
                + userId + ", amount: Rs." + amount);
        // Real code checks transaction patterns, location, etc.
        return true; // assume legitimate
    }
}
