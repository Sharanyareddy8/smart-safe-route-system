package com.burugula.saferoute.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class MapboxService {

    private final String apiKey;

    public MapboxService(@Value("${mapbox.api.key}") String apiKey) {
        this.apiKey = apiKey;
        System.out.println("Injected API KEY = " + apiKey);
    }

    public String getRoute(double sourceLat, double sourceLon,
                           double destLat, double destLon) {

        String url = buildUrl(sourceLat, sourceLon, destLat, destLon);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
    public List<List<double[]>> getAllDecodedRoutes(double sourceLat, double sourceLon,
                                                    double destLat, double destLon) throws Exception {

        String url = buildUrl(sourceLat, sourceLon, destLat, destLon);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);

        JsonNode routesNode = root.path("routes");

        List<List<double[]>> allRoutes = new ArrayList<>();

        for (JsonNode routeNode : routesNode) {

            String geometry = routeNode.path("geometry").asText();

            List<double[]> decoded = decodePolyline(geometry);

            allRoutes.add(decoded);
        }

        return allRoutes;
    }
        public double calculateRisk(List<double[]> route, int hour) {

        String pythonUrl = "http://127.0.0.1:8000/calculate-route-risk";

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = new HashMap<>();
        request.put("route", route);
        request.put("hour", hour);

        Map response = restTemplate.postForObject(pythonUrl, request, Map.class);

        return Double.parseDouble(response.get("risk_score").toString());
    }

    private String buildUrl(double sourceLat, double sourceLon,
                            double destLat, double destLon) {

        return "https://api.mapbox.com/directions/v5/mapbox/driving/"
                + sourceLon + "," + sourceLat + ";"
                + destLon + "," + destLat
                + "?geometries=polyline&alternatives=true&access_token=" + apiKey;
    }
    private List<double[]> decodePolyline(String encoded) {

        List<double[]> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;

            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;

            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            poly.add(new double[]{lat / 1E5, lng / 1E5});
        }

        return poly;
    }
}