package startegy;

public class WalkingStrategy implements RouteStrategy{
    @Override
    public RouteResult calculateRoute(String from, String to) {
        System.out.println("  [WalkingStrategy] Computing pedestrian route...");
        System.out.println("  Checking footpaths and crossings...");
        System.out.println("  Including parks and shaded routes...");

        return new RouteResult(
                "3.2 km",
                "38 minutes (leisurely pace)",
                from + " → MG Road footpath → Central Park → " + to
        );
    }
}
