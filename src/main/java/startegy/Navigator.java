package startegy;

public class Navigator {
    private RouteStrategy strategy; // has-a Strategy

    // Constructor injection — strategy set at creation
    public Navigator(RouteStrategy strategy) {
        this.strategy = strategy;
    }

    // Runtime swap — same navigator, new algorithm
    public void setStrategy(RouteStrategy strategy) {
        System.out.println("\n  [Navigator] Switching strategy to: "
                + strategy.getClass().getSimpleName());
        this.strategy = strategy;
    }

    // Delegates entirely to the strategy — Navigator has no algorithm
    public void navigate(String from, String to) {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("  Navigating from '" + from + "' to '" + to + "'");
        System.out.println("  Using: " + strategy.getClass().getSimpleName());
        System.out.println("└─────────────────────────────────────────┘");

        RouteResult result = strategy.calculateRoute(from, to);

        System.out.println("\n  ── ROUTE FOUND ──");
        System.out.println(result);
        System.out.println();
    }
}
