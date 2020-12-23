package com.example.exam01.api;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Данный интерфейс содержит необходимые для обработки данных методы
 */
public interface OnResponseListener {

    void onResponse(JSONObject jsonObject, int requestCode);
    void onResponse(JSONArray jsonArray, int requestCode);
    void onResponseError(VolleyError error, int requestCode);
}
