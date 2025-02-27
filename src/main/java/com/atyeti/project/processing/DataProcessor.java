package main.java.com.atyeti.project.processing;

import main.java.com.atyeti.project.data.Sensordata;
import java.util.*;
import java.util.stream.Collectors;

public class DataProcessor {
    public static Map<String, Map<String, Double>> monthlyStats(List<Sensordata> readings) {
        Map<String, List<Double>> data = readings.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getSensorType() + "-" + r.getDate().getMonth(),
                        Collectors.mapping(Sensordata::getValue, Collectors.toList())
                ));

        Map<String, Map<String, Double>> statsdata = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : data.entrySet()) {
            List<Double> values = entry.getValue();
            statsdata.put(entry.getKey(), Map.of("avg", values.stream().mapToDouble(v -> v).average().orElse(0.0),
                    "max", Collections.max(values), "min", Collections.min(values)
            ));
        }
        return statsdata;
    }
}
