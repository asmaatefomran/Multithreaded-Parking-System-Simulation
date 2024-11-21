public class Gate {
    private int carsEntered = 0;
    private final LoggingandReporting logger;
    public final String gatename;

    public Gate(String name,LoggingandReporting log){
        this.logger=log;
        this.gatename=name;
    }

    public synchronized void carEnters(String carname){
        carsEntered++;
    }

    public int getCarsEntered(){
        return carsEntered;
    }
}
