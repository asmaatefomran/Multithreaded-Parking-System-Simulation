
public class Car implements Runnable {
    private final String carnum;
    private final int arrivaltime;
    private final int parkingtime;
    private final Gate gate;
    private final LoggingandReporting logger;
    private final ParkingLot parkinglot;
    
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
        try{
        Thread.sleep(arrivaltime*1000);
        logger.logAction("Car "+carnum + " from Gate " + gate.gatename + " arrived at time " + arrivaltime);
        long startWaitTime = System.currentTimeMillis();
        
        if(parkinglot.parkCar(carnum,parkingtime,gate)){
             gate.carEnters(carnum);
        }
        else{
            logger.logAction("Car "+carnum + " from Gate " + gate.gatename + " could not find a spot.");
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.err.println("Car "+carnum + " was interrupted");
    }
    }
    
}
