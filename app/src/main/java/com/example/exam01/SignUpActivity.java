package com.example.exam01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.exam01.api.API;
import com.example.exam01.api.OnResponseListener;
import com.example.exam01.api.Requests;

import org.json.JSONArray;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity implements OnResponseListener {

    private EditText loginEditText;
    private EditText emailEditText;
    private EditText pass1EditText;
    private EditText pass2EditText;
    private Button signUpButton;

    private Requests requests;

    private final static int REQUEST_SIGN_UP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        requests = new Requests(this);

        loginEditText = findViewById(R.id.login_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        pass1EditText = findViewById(R.id.password1_edit_text);
        pass2EditText = findViewById(R.id.password2_edit_text);

        signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p1 = pass1EditText.getText().toString();
                String p2 = pass2EditText.getText().toString();
                if (!p1.equals(p2)) {
                    Toast.makeText(SignUpActivity.this, "Passwords don`t match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                API.signup(
                        loginEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        p1,
                        SignUpActivity.this,
                        REQUEST_SIGN_UP);


            }
        });
    }

    @Override
    public void onResponse(JSONObject jsonObject, int requestCode) {
        if (requestCode == REQUEST_SIGN_UP) {
            try {
                JSONObject notice = jsonObject.getJSONObject("notice");
                String answer = notice.getString("answer");
                if (answer.equals("Success")) {
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, answer, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void onResponse(JSONArray jsonArray, int requestCode) {

    }

    @Override
    public void onResponseError(VolleyError error, int requestCode) {
        Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
    }
}