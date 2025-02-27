package main.java.com.atyeti.project.ingestion;

import main.java.com.atyeti.project.data.Sensordata;
import main.java.com.atyeti.project.data.Threshold;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CsvRead {
    public static List<Sensordata> SensorData() throws IOException {
        List<Sensordata> readings = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get("src/main/java/com/atyeti/project/inputfiles/sensor_data.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                try {



                    String[] values = line.split(",");
                    Sensordata read = new Sensordata(values[0], values[1],
                            Double.parseDouble(values[2]), values[3], values[4]);
                    readings.add(read);

                } catch (Exception e) {
                    System.out.println("file not found "+line);

                }
            }
        }
        return readings;
    }

    public static Map<String, Threshold> Thresholds() throws IOException {
        Map<String, Threshold> thresholdmap = new HashMap<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get("src/main/java/com/atyeti/project/inputfiles/thresholds.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                thresholdmap.put(fields[0].trim(), new Threshold(fields[0].trim(), Double.parseDouble(fields[1].trim()), Double.parseDouble(fields[2].trim())));

            }
        }
        return thresholdmap;
    }
}
