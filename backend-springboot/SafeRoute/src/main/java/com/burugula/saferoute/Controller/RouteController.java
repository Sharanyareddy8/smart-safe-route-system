package com.burugula.saferoute.Controller;

import com.burugula.saferoute.Service.DatasetLocationService;
import com.burugula.saferoute.Service.MapboxService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class RouteController {

    private final MapboxService mapboxService;
    private final DatasetLocationService datasetLocationService;

    public RouteController(MapboxService mapboxService,
                           DatasetLocationService datasetLocationService) {
        this.mapboxService = mapboxService;
        this.datasetLocationService = datasetLocationService;
    }

    @GetMapping("/health")
    public String health() {
        return "Backend Running";
    }

    @PostMapping("/safe-route")
    public Map<String, Object> getSafeRoute(@RequestBody Map<String, Object> input) throws Exception {

        String sourceName = input.get("sourceName").toString();
        String destName = input.get("destName").toString();
        int hour = Integer.parseInt(input.get("hour").toString());

        double[] sourceCoords = datasetLocationService.getCoordinates(sourceName);
        double[] destCoords = datasetLocationService.getCoordinates(destName);

        if (sourceCoords == null || destCoords == null) {
            throw new RuntimeException("Invalid location selected");
        }

        double sourceLat = sourceCoords[0];
        double sourceLon = sourceCoords[1];
        double destLat = destCoords[0];
        double destLon = destCoords[1];

        List<List<double[]>> routes =
                mapboxService.getAllDecodedRoutes(sourceLat, sourceLon, destLat, destLon);

        if (routes.isEmpty()) {
            throw new RuntimeException("No routes found from Mapbox");
        }

        double minRisk = Double.MAX_VALUE;
        int safestIndex = 0;
        List<Double> allRisks = new ArrayList<>();

        for (int i = 0; i < routes.size(); i++) {

            double risk = mapboxService.calculateRisk(routes.get(i), hour);
            allRisks.add(risk);

            if (risk < minRisk) {
                minRisk = risk;
                safestIndex = i;
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("safestRouteIndex", safestIndex);
        result.put("lowestRisk", minRisk);
        result.put("allRisks", allRisks);

        return result;
    }
}