package org.example.model.key_holder;


public class ApiKeyHolder {

    public static final String LOCATION_API_KEY_NAME = "62f8d1bc-4bc5-4be6-916e-70d3cb799027";
    public static final String WEATHER_API_KEY_NAME  = "c5c67ca505d46933c56146c3c782d799";

    public static final String PLACES_API_KEY_NAME   = null;//"5ae2e3f221c38a28845f05b64fa94b1acbbfdc979dc0b29fdb7840dc";


    public ApiKeyHolder() {}

    public static String parseLocationApiKey() {
        return (LOCATION_API_KEY_NAME);
    }

    public static String parseWeatherApiKey() {
        return (WEATHER_API_KEY_NAME);
    }

    public static String parsePlacesApiKey() {
        return (PLACES_API_KEY_NAME);
    }


}
