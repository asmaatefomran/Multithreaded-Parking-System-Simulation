import java.util.concurrent.Semaphore;

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

 }