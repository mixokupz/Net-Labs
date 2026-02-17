package org.example.model.api;

import org.example.model.downloading.JsonDownloader;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class LocationApi {
    private static final String URL = "https://graphhopper.com/api/1/geocode";
    private final String apiKey;

    public LocationApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public CompletableFuture<Map<String, Object>> find(String locationName, int limit){

        String url = String.format("%s?q=%s&limit=%s&key=%s", URL,(URLEncoder.encode(locationName, StandardCharsets.UTF_8)), limit,apiKey);

        return JsonDownloader.download(url);
    }
}
