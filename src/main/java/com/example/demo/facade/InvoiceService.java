package com.example.demo.facade;

public class InvoiceService {

    public void generateInvoice(String userId, String transactionId,
                                double amount) {
        System.out.println("  [Invoice] Invoice generated for user: "
                + userId + " | Txn: " + transactionId
                + " | Rs." + amount);
    }
}
