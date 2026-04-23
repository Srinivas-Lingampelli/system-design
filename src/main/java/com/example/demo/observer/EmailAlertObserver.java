package com.example.demo.observer;

public class EmailAlertObserver implements Observer{

    private final String  email;
    private final double  dropThresholdPercent;

    public EmailAlertObserver(String email, double dropThresholdPercent) {
        this.email                = email;
        this.dropThresholdPercent = dropThresholdPercent;
    }

    @Override
    public void update(String symbol, double oldPrice, double newPrice) {
        double changePercent = ((newPrice - oldPrice) / oldPrice) * 100;

        if (changePercent <= -dropThresholdPercent) {
            System.out.printf("  [EmailAlert → %s] ALERT! %s dropped"
                            + " %.1f%% to Rs.%.2f. Sending email!%n",
                    email, symbol, changePercent, newPrice);
        }
        // No alert if price rose or dropped less than threshold
    }
}
