package com.example.exam01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.exam01.api.API;
import com.example.exam01.api.Data;
import com.example.exam01.api.OnResponseListener;
import com.example.exam01.api.Requests;

import org.json.JSONArray;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity implements OnResponseListener {

    private TextView textView;
    private ImageButton imageButton;
    private TextView exit;

    private TextView username;
    private TextView driveTime;
    private TextView email;
    private TextView cash;

    private Requests requests;
    private Data data;

    private final static int REQUEST_LOGOUT = 1;
    private final static int REQUEST_PROFILE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        data = Data.get();
        requests = new Requests(this);

        username = findViewById(R.id.username);
        driveTime = findViewById(R.id.time_drive);
        email = findViewById(R.id.email);
        cash = findViewById(R.id.cash);

        imageButton = findViewById(R.id.button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textView = findViewById(R.id.textView);
        textView.setText("Settings");

        exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject data = new JSONObject();
                    data.put("username", Data.get().getUsername());
                    requests.post("http://cars.areas.su/logout", data, REQUEST_LOGOUT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        updateUI();
    }

    private void updateUI() {
        try {
            API.profile(data.getToken(), this, REQUEST_PROFILE);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResponse(JSONObject jsonObject, int requestCode) {
        if (requestCode == REQUEST_LOGOUT) {
            Intent intent = new Intent(this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else if (requestCode == REQUEST_PROFILE) {
            try {
                username.setText(
                        jsonObject.getString("firstname") + " " +
                        jsonObject.getString("secondname"));
                driveTime.setText(jsonObject.getString("timeDrive") + " hours");
                cash.setText("$" + jsonObject.getString("cash"));
                email.setText("Email: " + jsonObject.getString("email"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onResponse(JSONArray jsonArray, int requestCode) {

    }

    @Override
    public void onResponseError(VolleyError error, int requestCode) {
        Toast.makeText(SettingsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
    }
}