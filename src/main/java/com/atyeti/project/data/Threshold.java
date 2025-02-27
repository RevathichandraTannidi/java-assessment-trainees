package main.java.com.atyeti.project.data;

public class Threshold {
    private String sensorType;
    private double minThresholdvalue;
    private double maxThresholdvalue;

    public Threshold(String sensorType, double minThreshold, double maxThreshold) {
        this.sensorType = sensorType;
        this.minThresholdvalue = minThreshold;
        this.maxThresholdvalue = maxThreshold;
    }

    public String getSensorType() { return sensorType; }
    public double getMinThresholdvalue() { return minThresholdvalue; }
    public double getMaxThresholdvalue() { return maxThresholdvalue; }
}
