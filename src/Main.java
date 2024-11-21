import java.util.*;

public class Main {
    public static void main(String[] args) {
        LoggingandReporting logger = new LoggingandReporting();
        ParkingLot parkingLot = new ParkingLot(4);

        Map<String, Gate> gates = new HashMap<>(); 
        List<Car> cars = ReadingInput.readInputFile("input/inputfile.txt", gates, logger, parkingLot);

        Map<String, Integer> gateStats = new HashMap<>();
        for (Gate gate : gates.values()) {
            gateStats.put(gate.gatename, 0);
        }

        List<Thread> threads = new ArrayList<>();
        for (Car car : cars) {
            Thread thread = new Thread(car);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        for (Gate gate : gates.values()) {
            gateStats.put(gate.gatename, gate.getCarsEntered());
        }

        logger.logFinalReport(cars.size(), parkingLot.getCurrentCars(), gateStats);
    }
}
