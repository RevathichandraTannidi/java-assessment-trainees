package main.java.com.atyeti.project.main;

import main.java.com.atyeti.project.ingestion.CsvRead;
import main.java.com.atyeti.project.reporting.CsvWriter;
import main.java.com.atyeti.project.data.SensorReading;
import main.java.com.atyeti.project.data.Threshold;
import main.java.com.atyeti.project.processing.DataProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {

            List<SensorReading> sensorData = CsvRead.readSensorData();
            Map<String, Threshold> thresholds = CsvRead.readThresholds();

            Map<String, Map<String, Double>> monthlyStats = DataProcessor.MonthlyStats(sensorData);
            List<String> monthlyStatsData = convertMonthlyStatsToCSV(monthlyStats);
            CsvWriter.writeCSV("src/main/java/com/atyeti/project/outputfiles/Monthlydata.csv", monthlyStatsData);


            List<String> outliersData = convertOutliersToCSV(sensorData, thresholds);
            CsvWriter.writeCSV("src/main/java/com/atyeti/project/outputfiles/Outliers.csv", outliersData);

            System.out.println("Processing complete.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Converts monthly statistics into CSV lines
    private static List<String> convertMonthlyStatsToCSV(Map<String, Map<String, Double>> monthlyStats) {
        List<String> data = new ArrayList<>();
        data.add("sensor_type,month,avg_value,max_value,min_value");
        for (Map.Entry<String, Map<String, Double>> entry : monthlyStats.entrySet()) {
            String[] keyParts = entry.getKey().split("-");
            String sensorType = keyParts[0];
            String month = keyParts[1];
            Map<String, Double> stats = entry.getValue();
            data.add(sensorType + "," + month + "," + stats.get("average") + "," + stats.get("max") + "," + stats.get("min"));
        }
        return data;
    }

    // Converts detected outliers into CSV lines
    private static List<String> convertOutliersToCSV(List<SensorReading> sensorData, Map<String, Threshold> thresholds) {
        List<String> data = new ArrayList<>();
        data.add("date,sensor_type,value,unit,location_id,threshold_exceeded");
        for (SensorReading reading : sensorData) {
            Threshold threshold = thresholds.get(reading.getSensorType());
            if (threshold != null) {
                if (reading.getValue() < threshold.getMinThreshold()) {
                    data.add(reading.getDate() + "," + reading.getSensorType() + "," + reading.getValue() + "," +
                            reading.getUnit() + "," + reading.getLocationId() + ",Min");
                } else if (reading.getValue() > threshold.getMaxThreshold()) {
                    data.add(reading.getDate() + "," + reading.getSensorType() + "," + reading.getValue() + "," +
                            reading.getUnit() + "," + reading.getLocationId() + ",Max");
                }
            }
        }
        return data;
    }
}
