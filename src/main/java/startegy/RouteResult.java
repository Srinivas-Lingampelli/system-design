package startegy;

public class RouteResult {
    private final String distance;
    private final String duration;
    private final String description;

    public RouteResult(String distance, String duration, String description) {
        this.distance    = distance;
        this.duration    = duration;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("  Distance : %s%n  Duration : %s%n  Route    : %s",
                distance, duration, description);
    }
}
