package com.example.exam01.api;

import com.example.exam01.model.Car;

import java.util.List;

/**
 * Класс для хранения данных о пользователе
 */
public class Data {

    private String token;
    private String username;
    private List<Car> cars;

    private static Data data;

    private Data() {}

    public static Data get() {
        if (data == null) {
            data = new Data();
        }
        return data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
