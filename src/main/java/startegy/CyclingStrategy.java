package startegy;

public class CyclingStrategy implements RouteStrategy {
    @Override
    public RouteResult calculateRoute(String from, String to) {
        System.out.println("  [CyclingStrategy] Computing cycling route...");
        System.out.println("  Checking dedicated cycle lanes...");
        System.out.println("  Avoiding steep gradients...");

        return new RouteResult(
                "5.8 km",
                "22 minutes (moderate pace)",
                from + " → Cycle Lane A → Lake Boulevard → " + to
        );
    }
}
