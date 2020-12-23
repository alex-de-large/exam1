package com.example.exam01;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.exam01.api.Data;
import com.example.exam01.api.OnResponseListener;
import com.example.exam01.api.Requests;
import com.example.exam01.model.Car;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NavActivity extends AppCompatActivity implements OnResponseListener {

    private AppBarConfiguration mAppBarConfiguration;

    private final static int REQUEST_CARS = 1;
    private final static int REQUEST_LOGOUT = 2;

    private ArrayList<Car> cars;
    private Requests requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requests = new Requests(this);
        cars = new ArrayList<>();
        requests.get("http://cars.areas.su/cars", REQUEST_CARS);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_map)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_history) {
                    startActivity(new Intent(NavActivity.this, HistoryActivity.class));
                    return true;
                } else if (id == R.id.nav_settings) {
                    startActivity(new Intent(NavActivity.this, SettingsActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }




    @Override
    public void onResponse(JSONObject jsonObject, int requestCode) {

    }

    @Override
    public void onResponse(JSONArray jsonArray, int requestCode) {
        if (requestCode == REQUEST_CARS) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Car car = new Car(
                            jsonObject.getString("id"),
                            jsonObject.getString("model"),
                            jsonObject.getString("lat"),
                            jsonObject.getString("lon")
                    );
                    cars.add(car);
                }
                Data.get().setCars(cars);

            } catch (Exception exception) {
                Toast.makeText(NavActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error, int requestCode) {
        Toast.makeText(NavActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
    }
}