package org.example.model.entities;

public class Address {
    private String country;
    private String state;
    private String county;
    private String city;
    private String cityDistrict;
    private String road;
    private String houseNumber;

    public Address(String country, String state, String county, String city,
                   String cityDistrict, String road, String houseNumber) {
        this.country = country;
        this.state = state;
        this.county = county;
        this.city = city;
        this.cityDistrict = cityDistrict;
        this.road = road;
        this.houseNumber = houseNumber;
    }


    public String getCountry() {
        return country;
    }
    public String getState() {
        return state;
    }
    public String getCounty() {
        return county;
    }
    public String getCity() {
        return city;
    }
    public String getCityDistrict() {
        return cityDistrict;
    }
    public String getRoad() {
        return road;
    }
    public String getHouseNumber() {
        return houseNumber;
    }

}
