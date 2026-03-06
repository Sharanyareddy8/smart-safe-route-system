package com.burugula.saferoute.Controller;

import com.burugula.saferoute.Service.DatasetLocationService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LocationController {

    private final DatasetLocationService locationService;

    public LocationController(DatasetLocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public Map<String, double[]> getLocations() {
        return locationService.getAllLocations();
    }
}