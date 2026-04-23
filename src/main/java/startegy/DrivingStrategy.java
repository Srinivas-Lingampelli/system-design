package startegy;

public class DrivingStrategy implements  RouteStrategy{
    @Override
    public RouteResult calculateRoute(String from, String to) {
        System.out.println("  [DrivingStrategy] Computing fastest road route...");
        System.out.println("  Checking live traffic data...");
        System.out.println("  Prioritising highways and flyovers...");

        // Real code would call a mapping API
        return new RouteResult(
                "18.5 km",
                "32 minutes (with current traffic)",
                from + " → NH44 → Outer Ring Road → " + to
        );
    }
}
