package com.example.exam01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.exam01.api.Data;
import com.example.exam01.api.OnResponseListener;
import com.example.exam01.api.Requests;

import org.json.JSONArray;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity implements OnResponseListener {

    private EditText loginEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private TextView textView;

    private Requests requests;

    private final static int REQUEST_LOGIN = 1;
    private final static int REQUEST_LOGOUT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Exam01);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        requests = new Requests(this);
        logout();

        loginEditText = findViewById(R.id.login_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);


        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    JSONObject data = new JSONObject();
                    data.put("username", loginEditText.getText().toString());
                    data.put("password", passwordEditText.getText().toString());
                    requests.post("http://cars.areas.su/login", data, REQUEST_LOGIN);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        textView = findViewById(R.id.new_account);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    private void logout() {
        try {
            JSONObject data = new JSONObject();
            data.put("username", "rofl");
            requests.post("http://cars.areas.su/logout", data, REQUEST_LOGOUT );
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject, int requestCode) {
        if (requestCode == REQUEST_LOGIN) {
            try {
                JSONObject notice = jsonObject.getJSONObject("notice");

                if (notice.has("token")) {
                    Data data = Data.get();
                    data.setToken(notice.getString("token"));
                    startActivity(new Intent(SignInActivity.this, NavActivity.class));
                } else if (notice.has("answer")) {
                    Toast.makeText(this, notice.getString("answer"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onResponse(JSONArray jsonArray, int requestCode) {

    }

    @Override
    public void onResponseError(VolleyError error, int requestCode) {
        Toast.makeText(SignInActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
    }
}