package com.example.exam01.model;

public class Car {
    private String id;
    private String model;
    private String lat;
    private String lon;

    public Car() {}

    public Car(String id, String model, String lat, String lon) {
        this.id = id;
        this.model = model;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
