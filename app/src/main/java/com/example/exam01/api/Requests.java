package com.example.exam01.api;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Requests {

    private static RequestQueue requestQueue;
    private OnResponseListener listener;

    public Requests(OnResponseListener listener) {
        this.listener = listener;
    }

    static {
        Cache cache = new DiskBasedCache(App.getContext().getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
    }

    public void get(String url, int requestCode) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Object json = new JSONTokener(response).nextValue();

                            if (json instanceof JSONObject) {
                                listener.onResponse(new JSONObject(response), requestCode);
                            } else {
                                listener.onResponse(new JSONArray(response), requestCode);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onResponseError(error, requestCode);
                    }
                });

        requestQueue.add(stringRequest);
    }

    public void post(String url, JSONObject data, int requestCode) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String temp = response.toString();
                            Object json = new JSONTokener(temp).nextValue();

                            if (json instanceof JSONObject) {
                                listener.onResponse(new JSONObject(temp), requestCode);
                            } else {
                                listener.onResponse(new JSONArray(temp), requestCode);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onResponseError(error, requestCode);
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}
