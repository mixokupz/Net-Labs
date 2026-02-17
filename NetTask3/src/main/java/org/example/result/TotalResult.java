package org.example.result;

import org.example.model.entities.Place;
import org.example.model.entities.Weather;

import java.util.List;

public class TotalResult {
    private final Weather weather;
    private final List<Place> places;

    public TotalResult(Weather weather, List<Place> places){
        this.weather = weather;
        this.places = places;
    }

    public Weather getWeather() {
        return weather;
    }
    public List<Place> getPlaces() {
        return places;
    }
}
