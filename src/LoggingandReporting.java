import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoggingandReporting {
    public synchronized void logAction(String s) {
        System.out.println(s);
    }

    public synchronized void logEntry(String carName, String gateName) {
        System.out.println("Car "+carName + " from Gate" + gateName + " parked ");
    }

    public synchronized void logFinalReport(int totalCars, int currentCars, Map<String, Integer> gateStats) {
        System.out.println("\n--- Final Report ---");
        System.out.println("Total Cars Served: " + totalCars);
        System.out.println("Current Cars in Parking: " + currentCars);
        System.out.println("Details:");
        gateStats.forEach((gate, count) -> System.out.println("-Gate " + gate + " served " + count + " cars."));
    }

}
