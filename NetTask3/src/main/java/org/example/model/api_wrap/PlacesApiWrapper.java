package org.example.model.api_wrap;

import org.example.model.api.PlacesApi;
import org.example.model.downloading.HttpExeption;
import org.example.model.entities.Location;
import org.example.model.entities.Place;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PlacesApiWrapper {
    private final PlacesApi placesApi;

    public PlacesApiWrapper(String apiKey) {
        this.placesApi = new PlacesApi(apiKey);
    }

    public CompletableFuture<List<String>> findPlaces(Location location, int limit){
        return placesApi.findPlacesByRadius(location.getLat(), location.getLng(), 10000, limit)
                .thenApply(apiData -> {
                    List<String> titles = new ArrayList<>();

                    Map<String, Object> query = (Map<String, Object>) apiData.get("query");
                    if (query == null) return titles;

                    List<Map<String, Object>> geosearch = (List<Map<String, Object>>) query.get("geosearch");
                    if (geosearch == null) return titles;

                    for (Map<String, Object> place : geosearch) {

                        String title = (String) place.get("title");
                        if (title != null) {
                            titles.add(title);
                        }
                    }

                    return titles;
                });
    }


    public CompletableFuture<Place> findPlaceDescription(String placeName) throws IOException, HttpExeption, InterruptedException {

        return placesApi.findPlaceDescription(placeName)
                .thenApply(PlacesApiWrapper::extractPlace);
    }

    private static Place extractPlace(Map<String, Object> placeData) {
        if (placeData == null) return null;

        Map<String, Object> query = (Map<String, Object>) placeData.get("query");
        Map<String, Object> pages = (Map<String, Object>) query.get("pages");

        Map<String, Object> page = (Map<String, Object>) pages.values().iterator().next();
        String title = (String) page.get("title");
        String extract = (String) page.get("extract");

        return new Place(title,extract);
    }


}
