package com.burugula.saferoute.Service;

import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.*;

@Service
public class DatasetLocationService {

    private final Map<String, double[]> locationMap = new HashMap<>();

    @PostConstruct
    public void loadLocations() {
        try {
            ClassPathResource resource = new ClassPathResource("dataset.csv");
            CSVReader reader = new CSVReader(
                    new InputStreamReader(resource.getInputStream())
            );

            String[] line;
            reader.readNext(); // skip header

            while ((line = reader.readNext()) != null) {

                // Adjust indexes if needed based on your CSV structure
                double latitude = Double.parseDouble(line[1]);
                double longitude = Double.parseDouble(line[2]);
                String areaName = line[3];

                locationMap.put(areaName, new double[]{latitude, longitude});
            }

            System.out.println("Locations Loaded: " + locationMap.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, double[]> getAllLocations() {
        return locationMap;
    }

    public double[] getCoordinates(String areaName) {
        return locationMap.get(areaName);
    }
}