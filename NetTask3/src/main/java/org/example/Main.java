package org.example;

import org.example.controller.Controller;
import org.example.model.api_wrap.LocationApiWrapper;
import org.example.model.api_wrap.PlacesApiWrapper;
import org.example.model.api_wrap.WeatherApiWrapper;

import org.example.model.app.ApplicationWrapHolder;
import org.example.model.key_holder.ApiKeyHolder;
import org.example.model.downloading.HttpExeption;
import org.example.model.entities.Location;
import org.example.model.entities.Place;
import org.example.model.entities.Weather;
import org.example.result.TotalResult;
import org.example.view.ConsoleView;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {

    private static CompletableFuture<List<Location>> findLocations(ApplicationWrapHolder app, String locationName, int limit) throws IOException, HttpExeption, InterruptedException {

        return app.getLocationApiWrapper().find(locationName, limit);
    }
    private static CompletableFuture<TotalResult> loadWeatherAndPlaces(ApplicationWrapHolder app, Location location, int placesLimit) throws IOException, HttpExeption, InterruptedException {
        CompletableFuture<Weather> weatherFuture  = app.getWeatherApiWrapper().find(location);

        CompletableFuture<List<String>> nearByPlacesFuture = app.getPlacesApiWrapper().findPlaces(location, placesLimit);

        CompletableFuture<List<Place>> placesFuture = nearByPlacesFuture.thenCompose(names -> {

            List<CompletableFuture<Place>> placeFutures = names.stream()
                    .map(name -> {
                        try {
                            return app.getPlacesApiWrapper().findPlaceDescription(name);
                        } catch (IOException | HttpExeption | InterruptedException  e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();

            return CompletableFuture.allOf(placeFutures.toArray(new CompletableFuture[0]))

                    .thenApply(v -> placeFutures.stream()
                            .map(CompletableFuture::join)
                            .toList());
        });
        return weatherFuture.thenCombine(placesFuture, TotalResult::new);
        //return placesFuture;
    }


    public static void main(String[] args) {
        ApiKeyHolder keyHolder = new ApiKeyHolder();
        ConsoleView consoleView = new ConsoleView();
        Controller controller = new Controller(consoleView);

        ApplicationWrapHolder application = new ApplicationWrapHolder(new LocationApiWrapper(keyHolder.parseLocationApiKey()),
                new WeatherApiWrapper(keyHolder.parseWeatherApiKey()), new PlacesApiWrapper(keyHolder.parsePlacesApiKey()));

        try {
            String locationName = controller.inputNameOfLocation();

            findLocations(application,locationName, 5)
                    .thenCompose(locations -> {
                        int locationIndex = controller.inputIndexOfLocation(locations);
                        Location chosenLocation = locations.get(locationIndex);
                        try {
                            return loadWeatherAndPlaces(application,chosenLocation, 3);
                        } catch (IOException | HttpExeption | InterruptedException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    })
                    .thenAccept(result -> {
                        consoleView.showWeather(result.getWeather());
                        consoleView.showPlaces(result.getPlaces());

                    }).join();

        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка выполнения: " + e.getMessage());
        } catch (HttpExeption e) {
            e.printStackTrace();
        }
    }
}
