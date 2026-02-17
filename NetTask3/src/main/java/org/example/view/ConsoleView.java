package org.example.view;

import java.util.List;
import org.example.model.entities.Weather;
import org.example.model.entities.Address;
import org.example.model.entities.Place;
import org.example.model.entities.Location;

public class ConsoleView {

    public static void showLocationNameInput() {
        showInput("имя локации");
    }

    public static void showIndexOfLocationInput() {
        showInput("индекс локации");
    }

    public static void showLocations(List<Location> locations) {

        String locationStr = "локаций";
        System.out.println("Нашел " + locations.size() + " " + locationStr);

        for (int i = 0; i < locations.size(); i++) {
            Location l = locations.get(i);
            System.out.println("    " + (i + 1) + ". " + l.getName() + " (" + l.getLat() + ", " + l.getLng() + ")");
        }
        System.out.println("\n");
    }

    public static void showInvalidInput() {
        System.out.println("Неправильно. Попробуй еще раз\n");
    }

    public static void showEmptyLine() {
        System.out.println();
    }

    public static void showWeather(Weather weather) {
        System.out.println("Погода");
        System.out.println("    " + weather.getDescription());
        System.out.println("    температура " + weather.getTemp() + " °C");
        System.out.println("    ощущается как " + weather.getFeelsLike() + " °C");
        System.out.println("    скорость ветра " + weather.getWindSpeed() + " м/с");
        System.out.println();
    }

    public static void showPlaces(List<Place> places) {
        System.out.println("Прикольные места");

        for (Place p : places) {
            System.out.println("\n");
            System.out.println("Название: "+ p.getName());

            if (p.getDescription() != null) {
                System.out.println("       Описание: " + p.getDescription());
            }
        }
    }

    private static void showInput(String inputName) {
        System.out.print("Пиши " + inputName + ": ");
    }
}
