package com.example.edupedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username_input, pass_input;
    Button btn_login;
    TextView sign_up, forgot_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_input = findViewById(R.id.username_input);
        pass_input = findViewById(R.id.pass_input);
        btn_login = findViewById(R.id.btn_login);
        sign_up = findViewById(R.id.sign_up);
        forgot_pass = findViewById(R.id.forgot_pass);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_input.getText().toString();
                String password = pass_input.getText().toString();

                // kirim data input ke fungsi input tambahData()
                checklogin(username, password);

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signup);
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgot = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgot);
            }
        });
    }

    void checklogin(final String username, final String password) {
        // ini sialisasi permintaan data
        String URL = "http://" + getString(R.string.ip_server) + ":8012/edupedia/login.php";

        // buka object lib yang digunakan melakukan requesr API
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // format pengiriman data ke API
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, // metode post
                URL, // url API
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));

                            if (jsonObject.names().get(0).equals("success")) {
                                Intent dashboard = new Intent(LoginActivity.this, MainActivity.class);
                                dashboard.putExtra( "username", username_input.getText().toString());
                                startActivity(dashboard);
                            } else {
                                Intent loginagain = new Intent(LoginActivity.this, LoginActivity.class);
                                Toast.makeText(LoginActivity.this, "Username atau password salah\nSilahkan login ulang", Toast.LENGTH_LONG).show();
                                startActivity(loginagain);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RestaRespone", error.toString());
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() {
                // input yang akan di kirim ke API
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        // eksekusi format request
        requestQueue.add(stringRequest);
    }

}