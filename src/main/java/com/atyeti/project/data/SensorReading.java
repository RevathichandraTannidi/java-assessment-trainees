package main.java.com.atyeti.project.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SensorReading {
    private LocalDateTime date;
    private String sensorType;
    private double value;
    private String unit;
    private int locationId;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SensorReading(String date, String sensorType, double value, String unit, String locationId) {
        try {
            this.date = LocalDateTime.parse(date.trim(), FORMAT);
            this.sensorType = sensorType;
            this.value = value;
            this.unit = unit;
            this.locationId = Integer.parseInt(locationId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid data format: " + date);
        }
    }

    public LocalDateTime getDate() { return date; }
    public String getSensorType() { return sensorType; }
    public double getValue() { return value; }
    public String getUnit() { return unit; }
    public int getLocationId() { return locationId; }
}
