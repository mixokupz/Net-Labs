package org.example.model.app;

import org.example.model.api_wrap.LocationApiWrapper;
import org.example.model.api_wrap.WeatherApiWrapper;
import org.example.model.api_wrap.PlacesApiWrapper;

public class ApplicationWrapHolder {
    private LocationApiWrapper locationApiWrapper;
    private WeatherApiWrapper weatherApiWrapper;
    private PlacesApiWrapper placeApiWrapper;

    public ApplicationWrapHolder(LocationApiWrapper locationApiWrapper,
                       WeatherApiWrapper weatherApiWrapper,
                       PlacesApiWrapper placeApiWrapper) {
        this.locationApiWrapper = locationApiWrapper;
        this.weatherApiWrapper = weatherApiWrapper;
        this.placeApiWrapper = placeApiWrapper;
    }

    public LocationApiWrapper getLocationApiWrapper() {
        return locationApiWrapper;
    }
    public WeatherApiWrapper getWeatherApiWrapper() {
        return weatherApiWrapper;
    }
    public PlacesApiWrapper getPlacesApiWrapper() {
        return placeApiWrapper;
    }
}
