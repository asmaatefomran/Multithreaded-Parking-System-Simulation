 class ParkingLot {

    private final CustomCountingSemaphore parking_spots;
    private int currentCars = 0;

    public ParkingLot(int numSpots) {
        parking_spots = new CustomCountingSemaphore(numSpots); 
    }

    public int getCurrentCars() {
        return currentCars;
    }
    
    


    public boolean parkCar(String carName, int parkDuration, Gate gate) {
        try {
            long startWaitTime = System.currentTimeMillis();
    
            if (parking_spots.availablePermits() == 0) {
                System.out.println("Car " + carName + " from Gate " + gate.gatename + " is waiting for a spot.");
            }
    
            parking_spots.acquire(); 
            long waitTime = (System.currentTimeMillis() - startWaitTime) / 1000; 
    
            if (waitTime > 0) {
                System.out.println("Car " + carName + " from Gate " + gate.gatename + " waited " + waitTime + " seconds to get a spot.");
            }
    
            synchronized (this) {
                currentCars++;
                System.out.println("Car " + carName + " from Gate " + gate.gatename + " parked. (Parking Status: " + currentCars + " spots occupied)");
            }
    
            Thread.sleep(parkDuration * 1000); 
    
            synchronized (this) {
                currentCars--;
                System.out.println("Car " + carName + " from Gate " + gate.gatename + " left after " + parkDuration + " seconds. (Parking Status: " + currentCars + " spots occupied)");
            }
    
            parking_spots.release(); 
            return true;
    
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
    

 }