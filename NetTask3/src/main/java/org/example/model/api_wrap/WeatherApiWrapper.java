package org.example.model.api_wrap;

import org.example.model.api.WeatherApi;
import org.example.model.downloading.HttpExeption;
import org.example.model.entities.Location;
import org.example.model.entities.Weather;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class WeatherApiWrapper {
    private final WeatherApi weatherApi;

    public WeatherApiWrapper(String apiKey) {
        this.weatherApi = new WeatherApi(apiKey);
    }

    public CompletableFuture<Weather> find(Location location){
        return weatherApi.find(location.getLat(), location.getLng(), "metric", "ru")
                .thenApply(apiData -> {
                    List<Map<String, Object>> weatherList = (List<Map<String, Object>>) apiData.get("weather");
                    Map<String, Object> main = (Map<String, Object>) apiData.get("main");
                    Map<String, Object> wind = (Map<String, Object>) apiData.get("wind");

                    String description = weatherList != null && !weatherList.isEmpty()
                            ? (String) weatherList.get(0).get("description")
                            : "No description";

                    double temp = ((Number) main.get("temp")).doubleValue();
                    double feelsLike = ((Number) main.get("feels_like")).doubleValue();
                    double windSpeed = ((Number) wind.get("speed")).doubleValue();

                    return new Weather(description, temp, feelsLike, windSpeed);
                });
    }
}
