package main.java.com.atyeti.project.reporting;



import java.io.*;
import java.util.*;

public class CsvWriter {
    public static void writeCSV(String filePath, List<String> data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : data) {
                bw.write(line);
                bw.newLine();
            }
        }
    }
}

