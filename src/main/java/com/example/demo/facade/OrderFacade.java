package com.example.demo.facade;

    // ============================================================
// OrderFacade.java — THE FACADE
//
// This is the star of the pattern. Notice:
//
// 1. It instantiates all subsystems internally.
//    Clients never create InventoryService themselves.
//
// 2. Each public method (placeOrder, cancelOrder, trackOrder)
//    represents a complete business operation. Not a technical
//    step — a real user action.
//
// 3. The SEQUENCE of steps is locked inside here.
//    Every dev who calls placeOrder gets the same correct
//    sequence: fraud check → inventory → payment → shipping
//    → invoice → notification. In that exact order. Always.
//
// 4. Error handling and rollback logic lives here too.
//    If payment fails, we restore inventory and notify.
//    The client doesn't need to know any of this.
//
// Tech Lead note: This class is where business workflow
// rules live. It is a high-value, high-test-coverage class.
// ============================================================
    public class OrderFacade {

        // All subsystems wired in — client never sees these
        private final InventoryService    inventory;
        private final FraudService        fraud;
        private final PaymentService      payment;
        private final ShippingService     shipping;
        private final NotificationService notification;
        private final InvoiceService      invoice;

        public OrderFacade() {
            this.inventory    = new InventoryService();
            this.fraud        = new FraudService();
            this.payment      = new PaymentService();
            this.shipping     = new ShippingService();
            this.notification = new NotificationService();
            this.invoice      = new InvoiceService();
        }

        // ── OPERATION 1: Place an order ──────────────────────────
        // ONE method. SEVEN steps. Client calls one line.
        public boolean placeOrder(String userId,
                                  String productId,
                                  int    qty,
                                  double amount) {
            System.out.println("\n>>> PLACING ORDER <<<");
            System.out.println("User: " + userId + " | Product: "
                    + productId + " | Qty: " + qty);
            System.out.println("─────────────────────────────────");

            // Step 1 — Check stock
            if (!inventory.checkAvailability(productId, qty)) {
                System.out.println("  FAILED: Item out of stock.");
                return false;
            }

            // Step 2 — Fraud check (easy to forget without Facade!)
            if (!fraud.validateTransaction(userId, amount)) {
                System.out.println("  FAILED: Fraud detected.");
                return false;
            }

            // Step 3 — Reserve stock immediately
            inventory.reduceStock(productId, qty);

            // Step 4 — Process payment
            PaymentService.PaymentResult result =
                    payment.processPayment(userId, amount);

            if (!result.isSuccess()) {
                // Rollback stock if payment fails
                inventory.restoreStock(productId, qty);
                notification.sendPaymentFailureAlert(userId);
                System.out.println("  FAILED: Payment unsuccessful. Stock restored.");
                return false;
            }

            // Step 5 — Create shipment
            String trackingId = shipping.createShipment(userId, productId);

            // Step 6 — Generate invoice
            invoice.generateInvoice(userId, result.getTransactionId(), amount);

            // Step 7 — Notify customer
            notification.sendOrderConfirmation(userId, trackingId);

            System.out.println("─────────────────────────────────");
            System.out.println("  ORDER PLACED SUCCESSFULLY!");
            System.out.println("  Tracking ID : " + trackingId);
            System.out.println("  Transaction : " + result.getTransactionId());
            return true;
        }

        // ── OPERATION 2: Cancel an order ─────────────────────────
        public void cancelOrder(String userId,
                                String transactionId,
                                String trackingId,
                                String productId,
                                int    qty) {
            System.out.println("\n>>> CANCELLING ORDER <<<");
            System.out.println("─────────────────────────────────");

            // Step 1 — Cancel shipment
            shipping.cancelShipment(trackingId);

            // Step 2 — Refund payment
            payment.refundPayment(transactionId);

            // Step 3 — Restore inventory
            inventory.restoreStock(productId, qty);

            // Step 4 — Notify customer
            notification.sendCancellationNotice(userId);

            System.out.println("─────────────────────────────────");
            System.out.println("  ORDER CANCELLED. Refund initiated.");
        }

        // ── OPERATION 3: Track an order ──────────────────────────
        public String trackOrder(String trackingId) {
            System.out.println("\n>>> TRACKING ORDER: " + trackingId);
            String status = shipping.getTrackingStatus(trackingId);
            System.out.println("  Status: " + status);
            return status;
        }
    }
