package org.example.model.entities;

public class Weather {
    private String description;
    private double temp;
    private double feelsLike;
    private double windSpeed;

    public Weather(String description, double temp, double feelsLike, double windSpeed) {
        this.description = description;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.windSpeed = windSpeed;
    }

    public String getDescription() {
        return description;
    }
    public double getTemp() {
        return temp;
    }
    public double getFeelsLike() {
        return feelsLike;
    }
    public double getWindSpeed() {
        return windSpeed;
    }

}
