package org.example.model.api_wrap;

import org.example.model.downloading.HttpExeption;
import org.example.model.entities.Location;
import org.example.model.api.LocationApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class LocationApiWrapper {
    private final LocationApi locationApi;

    public LocationApiWrapper(String apiKey) {
        this.locationApi = new LocationApi(apiKey);
    }

    public CompletableFuture<List<Location>> find(String locationName, int limit){
        return locationApi.find(locationName,limit)
                .thenApply(apiData -> {
                    List<Map<String, Object>> hits = (List<Map<String, Object>>) apiData.get("hits");
                    List<Location> locations = new ArrayList<>();

                    if (hits != null) {
                        for (Map<String, Object> hit : hits) {
                            locations.add(extractLocation(hit));
                        }
                    }
                    return locations;
                });
    }

    private static Location extractLocation(Map<String, Object> hit) {
        Map<String, Object> point = (Map<String, Object>) hit.get("point");

        String name = (String) hit.get("name");

        return new Location(name, (Double) point.get("lat"), (Double) point.get("lng")
        );
    }
}
