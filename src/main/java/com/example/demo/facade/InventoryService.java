package com.example.demo.facade;

public class InventoryService {
    public boolean checkAvailability(String productId, int qty) {
        System.out.println("  [Inventory] Checking stock for product: "
                + productId + ", qty: " + qty);
        // Real code would query a database
        return true; // assume in stock
    }

    public void reduceStock(String productId, int qty) {
        System.out.println("  [Inventory] Reducing stock by "
                + qty + " for product: " + productId);
    }

    public void restoreStock(String productId, int qty) {
        System.out.println("  [Inventory] Restoring stock by "
                + qty + " for product: " + productId);
    }
}
