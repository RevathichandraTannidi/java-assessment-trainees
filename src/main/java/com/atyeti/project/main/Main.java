package main.java.com.atyeti.project.main;

import main.java.com.atyeti.project.ingestion.CsvRead;
import main.java.com.atyeti.project.reporting.CsvWriter;
import main.java.com.atyeti.project.data.Sensordata;
import main.java.com.atyeti.project.data.Threshold;
import main.java.com.atyeti.project.processing.DataProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {

            List<Sensordata> sensorData = CsvRead.SensorData();
            Map<String, Threshold> thresholds = CsvRead.Thresholds();

            Map<String, Map<String, Double>> monthlyStats = DataProcessor.monthlyStats(sensorData);
            List<String> monthlyStatsData = monthlyStatsToCSV(monthlyStats);
            CsvWriter.writeCSV("src/main/java/com/atyeti/project/outputfiles/Monthlydata.csv", monthlyStatsData);


            List<String> outliersData = outliersToCSV(sensorData, thresholds);
            CsvWriter.writeCSV("src/main/java/com/atyeti/project/outputfiles/Outliers.csv", outliersData);

            System.out.println("Processing complete.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static List<String> monthlyStatsToCSV(Map<String, Map<String, Double>> monthlyStats) {
        List<String> datastats = new ArrayList<>();
        datastats.add("sensor_type,month,avg_value,max_value,min_value");
        for (Map.Entry<String, Map<String, Double>> entry : monthlyStats.entrySet()) {
            String[] key = entry.getKey().split("-");
            String senType = key[0];
            String mon = key[1];
            Map<String, Double> stats = entry.getValue();
            datastats.add(senType + "," + mon + "," + stats.get("average") + "," + stats.get("max") + "," + stats.get("min"));
        }
        return datastats;
    }


    private static List<String> outliersToCSV(List<Sensordata> sensorData, Map<String, Threshold> thresholds) {
        List<String> dataoutliers = new ArrayList<>();
        dataoutliers.add("date,sensor_type,value,unit,location_id,threshold_exceeded");
        for (Sensordata read : sensorData) {
            Threshold threshold = thresholds.get(read.getSensorType());
            if (threshold != null) {
                if (read.getValue() < threshold.getMinThresholdvalue()) {
                    dataoutliers.add(read.getDate() + "," + read.getSensorType() + "," + read.getValue() + "," +
                            read.getUnit() + "," + read.getLocationId() + ",Min");
                } else if (read.getValue() > threshold.getMaxThresholdvalue()) {
                    dataoutliers.add(read.getDate() + "," + read.getSensorType() + "," + read.getValue() + "," +
                            read.getUnit() + "," + read.getLocationId() + ",Max");
                }
            }
        }
        return dataoutliers;
    }
}
