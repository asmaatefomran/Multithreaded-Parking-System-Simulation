import java.util.concurrent.Semaphore;
import java.util.Queue;
import java.util.LinkedList;


 class ParkingLot {

    private final Semaphore parking_spots;
    private int currentCars = 0;
    long startWaitTime;

    public ParkingLot(int numSpots) {
        parking_spots = new Semaphore(numSpots); //determined number of spots in parking
    }


    public  boolean parkCar(String carName, int parkDuration) {
        //try enter car in parking
        try {
            synchronized (this){startWaitTime = System.currentTimeMillis();}

            System.out.println(carName + " is attempting to park...");
            parking_spots.acquire(); // Try to acquire a parking spot

            synchronized (this){
                long endWaitTime = System.currentTimeMillis();
                long waitTime = (endWaitTime - startWaitTime) / 1000;
                System.out.println(carName + " waited for " + waitTime + " seconds before parking.");
            }
            synchronized (this) {
                // Car is parked
                currentCars++;
                System.out.println(carName + " parked. (Parking Status: " + currentCars + " spots occupied)");
            }

            Thread.sleep(parkDuration * 1000); // car stays in parking
            //synchronized to prevent two thread from accessing at the same time
            synchronized(this){
                currentCars--;  // car leaves the parking
                System.out.println(carName + " left after " + parkDuration + " seconds. (Parking Status: " + currentCars + " spots occupied)");
            }
            parking_spots.release(); // increase the available spots
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // if failed return false
        return false;
    }



    public static void main(String[] args) {
        LoggingandReporting logger = new LoggingandReporting();
        Gate gate = new Gate("Gate 1", logger);
        ParkingLot parkingLot = new ParkingLot(4); // 4 parking spots available

        Queue<Car> carQueue = new LinkedList<>();
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

        // Controller thread to manage parking in the order of arrival
        Thread controllerThread = new Thread(() -> {
            while (!carQueue.isEmpty()) {
                Car car = carQueue.poll(); // Get the next car in the queue
                if (car != null) {
                    Thread carThread = new Thread(car);  // Create a new thread for each car
                    carThread.start();
                    try {
                        carThread.join(); // Wait for the car to finish parking before starting the next car
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        controllerThread.start();  // Start the controller thread
   }


 }