
import java.util.Queue;
import java.util.Comparator;
import java.util.PriorityQueue;
public class Main {
    public static void main(String[] args) {
        LoggingandReporting logger = new LoggingandReporting();
        Gate gate = new Gate("Gate 1", logger);
        ParkingLot parkingLot = new ParkingLot(4); // 4 parking spots available
        // Priority queue sorted by arrival time
        Queue<Car> carQueue = new PriorityQueue<>(Comparator.comparingInt(Car::getArrivalTime));
        carQueue.add(new Car("Car 1", 5, 3, gate, logger, parkingLot));
        carQueue.add(new Car("Car 2", 3, 4, gate, logger, parkingLot));
        carQueue.add(new Car("Car 3", 6, 2, gate, logger, parkingLot));
        carQueue.add(new Car("Car 4", 2, 5, gate, logger, parkingLot));
        carQueue.add(new Car("Car 5", 4, 3, gate, logger, parkingLot));
        carQueue.add(new Car("Car 6", 3, 2, gate, logger, parkingLot));
        carQueue.add(new Car("Car 7", 7, 4, gate, logger, parkingLot));
        carQueue.add(new Car("Car 8", 8, 6, gate, logger, parkingLot));
        carQueue.add(new Car("Car 9", 9, 5, gate, logger, parkingLot));
        carQueue.add(new Car("Car 10", 10, 4, gate, logger, parkingLot));
        carQueue.add(new Car("Car 11", 11, 7, gate, logger, parkingLot));
        
        // Start each car thread based on its arrival time
        while (!carQueue.isEmpty()) {
            Car car = carQueue.poll();
            if (car != null) {
                Thread carThread = new Thread(car); // Create a new thread for each car
                carThread.start();
            }
        }
    }
}
