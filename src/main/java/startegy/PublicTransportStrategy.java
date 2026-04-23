package startegy;

public class PublicTransportStrategy implements RouteStrategy
{
    @Override
    public RouteResult calculateRoute(String from, String to) {
        System.out.println("  [PublicTransportStrategy] Computing transit route...");
        System.out.println("  Checking metro, bus schedules...");
        System.out.println("  Finding nearest metro station...");

        return new RouteResult(
                "21 km (metro + walk)",
                "45 minutes (includes 5 min walk)",
                from + " → Metro Line 2 → " + to + " station → " + to
        );
    }
}
