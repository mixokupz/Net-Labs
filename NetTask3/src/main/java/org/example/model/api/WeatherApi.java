package org.example.model.api;

import org.example.model.downloading.HttpExeption;
import org.example.model.downloading.JsonDownloader;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class WeatherApi {
    private static final String URL = "https://api.openweathermap.org/data/2.5/weather";
    private final String apiKey;

    public WeatherApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public CompletableFuture<Map<String, Object>> find(double lat, double lon, String units, String lang){
        String url =  String.format("%s?lat=%f&lon=%f&appid=%s&units=%s&lang=%s",
                URL, lat, lon, apiKey, units, lang);
        return JsonDownloader.download(url);
    }
}
