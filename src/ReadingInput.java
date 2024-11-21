import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReadingInput {
    public static List<Car> readInputFile(String filename, Map<String, Gate> gates, LoggingandReporting logger, ParkingLot parkingLot) {
        List<Car> cars = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                String gateNum = parts[0].split(" ")[1];                     // Extract Gate number
                String carNum = parts[1].split(" ")[1];                      // Extract Car number
                int arriveTime = Integer.parseInt(parts[2].split(" ")[1]);   // Extract Arrive time
                int parkDuration = Integer.parseInt(parts[3].split(" ")[1]); // Extract Park duration

                Gate gate = gates.get(gateNum);
                if (gate == null) {
                    gate = new Gate(gateNum, logger);
                    gates.put(gateNum, gate);
                }

                Car car = new Car(carNum, arriveTime, parkDuration, gate, logger, parkingLot);
                cars.add(car);
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }

        return cars;
    }
}
