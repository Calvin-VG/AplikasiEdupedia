package com.example.edupedia;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText full_name_profile, username_profile, email_profile, phone_profile, password_profile;
    Button btn_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        full_name_profile = findViewById(R.id.full_name_profile);
        username_profile = findViewById(R.id.username_profile);
        email_profile = findViewById(R.id.email_profile);
        phone_profile = findViewById(R.id.phone_profile);
        password_profile = findViewById(R.id.password_profile);
        btn_sign_up = findViewById(R.id.btn_sign_up);

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String full_name = full_name_profile.getText().toString();
                String username = username_profile.getText().toString();
                String email = email_profile.getText().toString();
                String phone_number = phone_profile.getText().toString();
                String password = password_profile.getText().toString();

                // kirim data input ke fungsi input tambahData()
                SignUp(full_name, username, email, phone_number, password);
            }
        });
    }

    // fungsi tambah data
    void SignUp(final String full_name, final String username, final String email, final String phone_number, final String password){

        // ini sialisasi permintaan data
        String URL = "http://"+getString(R.string.ip_server)+":8012/edupedia/signup.php";

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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RestaRespone", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                // input yang akan di kirim ke API
                Map<String, String> params = new HashMap<String, String>();
                params.put("full_name", full_name);
                params.put("username", username);
                params.put("email", email);
                params.put("phone_number", phone_number);
                params.put("password", password);

                return params;
            }
        };

        // eksekusi format API
        requestQueue.add(stringRequest);
        Toast.makeText(this, "SignUp Berhasil\nSilahkan Login", Toast.LENGTH_LONG).show();
        onBackPressed();
    }
}