package com.example.edupedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email_input;
    Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email_input = findViewById(R.id.email_input);
        btn_reset = findViewById(R.id.btn_reset);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_input_user = email_input.getText().toString();

                // kirim data input ke fungsi input tambahData()
                checkemail(email_input_user);
            }
        });
    }

    void checkemail(final String email_input_user) {
        // ini sialisasi permintaan data
        String URL = "http://" + getString(R.string.ip_server) + ":8012/edupedia/forgot_password.php";

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
                                Intent resetemail = new Intent(ForgotPasswordActivity.this, ResetEmailActivity.class);
                                startActivity(resetemail);
                            } else {
                                Intent emailnotfound = new Intent(ForgotPasswordActivity.this, EmailNotFoundActivity.class);
                                startActivity(emailnotfound);
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
                params.put("email", email_input_user);
                return params;
            }
        };

        // eksekusi format request
        requestQueue.add(stringRequest);
    }
}
