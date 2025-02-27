package main.java.com.atyeti.project.ingestion;

import main.java.com.atyeti.project.data.SensorReading;
import main.java.com.atyeti.project.data.Threshold;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvRead {
    public static List<SensorReading> readSensorData() throws IOException {
        List<SensorReading> readings = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get("src/main/java/com/atyeti/project/inputfiles/sensor_data.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                try {



                    String[] fields = line.split(",");
                    SensorReading reading = new SensorReading(fields[0].trim(), fields[1].trim(),
                            Double.parseDouble(fields[2].trim()), fields[3].trim(), fields[4].trim());
                    readings.add(reading);

                } catch (Exception e) {
                    System.out.println("file not found"+line);

                }
            }
        }
        return readings;
    }

    public static Map<String, Threshold> readThresholds() throws IOException {
        Map<String, Threshold> thresholds = new HashMap<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get("src/main/java/com/atyeti/project/inputfiles/thresholds.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                thresholds.put(fields[0].trim(), new Threshold(fields[0].trim(), Double.parseDouble(fields[1].trim()), Double.parseDouble(fields[2].trim())));

            }
        }
        return thresholds;
    }
}
