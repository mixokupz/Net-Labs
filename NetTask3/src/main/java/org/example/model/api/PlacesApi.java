package org.example.model.api;

import org.example.model.downloading.HttpExeption;
import org.example.model.downloading.JsonDownloader;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PlacesApi {
    private final String apiKey;

    private static final String WIKI_URL = "https://ru.wikipedia.org/w/api.php";
    //private static final String PLACE_DESCRIPTION_URL = "https://api.opentripmap.com/0.1/ru/places/xid";

    public PlacesApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public CompletableFuture<Map<String, Object>> findPlacesByRadius(double lat, double lon, int radius, int limit) {
        String encodedCoord = String.format(Locale.US, "%f%%7C%f", lat, lon);

        String url = String.format(
                "%s?action=query&list=geosearch&gsradius=%d&gscoord=%s&gslimit=%d&format=json",
                WIKI_URL,
                radius,
                encodedCoord,
                limit
        );

        return JsonDownloader.download(url);
    }

    public CompletableFuture<Map<String, Object>> findPlaceDescription(String placeName) throws HttpExeption {

        String encodedName = URLEncoder.encode(placeName, StandardCharsets.UTF_8);

        String url = String.format("%s?action=query&prop=extracts&exintro=true&explaintext=true&titles=%s&format=json",
                WIKI_URL, encodedName);

        return JsonDownloader.download(url);
    }
}
