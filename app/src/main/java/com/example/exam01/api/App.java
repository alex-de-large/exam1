package com.example.exam01.api;

import android.app.Application;
import android.content.Context;

/**
 * Класс для получения контекста приложения
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
