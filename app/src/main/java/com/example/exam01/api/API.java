package com.example.exam01.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Класс-обертка над классом Requests. Предназначен для более удобного взаимодействия с api
 */

public class API {

    public static void profile(String token, OnResponseListener listener, int requestCode) {
        String url = "http://cars.areas.su/profile";
        Requests requests = new Requests(listener);
        JSONObject json = new JSONObject();
        try {
            json.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requests.post(url, json, requestCode);
    }

    public static void cars(OnResponseListener listener, int requestCode) {
        String url = "http://cars.areas.su/cars";
        Requests requests = new Requests(listener);
        requests.get(url, requestCode);
    }

    public static void login(String username, String password, OnResponseListener listener, int requestCode) {
        String url = "http://cars.areas.su/login";
        Requests requests = new Requests(listener);
        JSONObject data = new JSONObject();
        try {
            data.put("username", username);
            data.put("password", password);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        requests.post(url, data, requestCode);

    }

    public static void logout(String username, OnResponseListener listener, int requestCode) {
        String url = "http://cars.areas.su/logout";
        Requests requests = new Requests(listener);
        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requests.post(url, json, requestCode );
    }

    public static void signup(String username, String email, String password, OnResponseListener listener, int requestCode) {
        String url = "http://cars.areas.su/signup";
        Requests requests = new Requests(listener);
        JSONObject data = new JSONObject();
        try {
            data.put("username", username);
            data.put("email", email);
            data.put("password", password);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        requests.post(url, data, requestCode);
    }


}
