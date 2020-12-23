package com.example.exam01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.exam01.api.Data;
import com.example.exam01.api.OnResponseListener;
import com.example.exam01.api.Requests;

import org.json.JSONArray;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity implements OnResponseListener {

    private TextView textView;
    private ImageButton imageButton;
    private TextView exit;

    private Requests requests;

    private final static int REQUEST_LOGOUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        requests = new Requests(this);

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
    }

    @Override
    public void onResponse(JSONObject jsonObject, int requestCode) {
        if (requestCode == REQUEST_LOGOUT) {
            Intent intent = new Intent(this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
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