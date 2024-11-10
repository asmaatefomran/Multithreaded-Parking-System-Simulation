public class Gate {
    private int carsEntered=0;
    private final LoggingandReporting logger;
    public final String gatename;

    public Gate(String name,LoggingandReporting log){
        this.logger=log;
        this.gatename=name;
    }

    //"synchronized" to make the function atomic and only can acessed by one thread at a time(one car)
    public synchronized void carEnters(String carname){
        carsEntered++;
        // log the entry of the car throw the currgate
        logger.logEntry(carname, gatename);
    }

    //to get all cars entered the parking using this gate
    public int entered(){
        return carsEntered;
    }
}
