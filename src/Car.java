
public class Car implements Runnable {
    //creating the needed terms about a car
    private final String carnum;
    private final int arrivaltime;
    private final int parkingtime;
    private final Gate gate;
    private final LoggingandReporting logger;
    private final ParkingLot parkinglot;
    
    // assign the passed info to the variables I created before
    public Car(String carn,int arrived,int dur,Gate g,LoggingandReporting log,ParkingLot parking){
        this.carnum=carn;
        this.arrivaltime=arrived;
        this.parkingtime=dur;
        this.gate=g;
        this.logger=log;
        this.parkinglot=parking;
    }
    
    public int getArrivalTime() {
        return arrivaltime;
    }

    @Override
    public void run(){
        // the error handling to handle if there is thread (car) interrupted an thread.sleep
        try{
        //handling the delay before the car arrived
        Thread.sleep(arrivaltime*1000);
        //log the action of arrival
        logger.logAction(carnum+" arrived after "+ arrivaltime+" seconds.");
        
        //checking if there is a free parking spot
        if(parkinglot.parkCar(carnum,parkingtime,gate.gatename)){
            // make the car enter throw the gate
            gate.carEnters(carnum);
        }
        else{
            // log that there is no free spots so the car didn't park
            logger.logAction(carnum+" could not find a spot.");
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.err.println(carnum + " was interrupted before parking.");
    }
    }
    
}
