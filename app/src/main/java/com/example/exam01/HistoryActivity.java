package com.example.exam01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    private ImageButton exitButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        exitButton = findViewById(R.id.button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryActivity.this.finish();
            }
        });

        textView = findViewById(R.id.textView);
        textView.setText("History");
    }
}