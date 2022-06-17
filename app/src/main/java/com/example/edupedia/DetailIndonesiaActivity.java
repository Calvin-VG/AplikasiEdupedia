package com.example.edupedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailIndonesiaActivity extends AppCompatActivity {

    CardView card_reason, card_cost, card_education, card_study_provisions, card_univ;
    TextView tv_deskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_indonesia);

        card_reason = findViewById(R.id.card_reason);
        card_cost = findViewById(R.id.card_cost);
        card_education = findViewById(R.id.card_education);
        card_study_provisions = findViewById(R.id.card_study_provisions);
        card_univ = findViewById(R.id.card_univ);
        tv_deskripsi = findViewById(R.id.tv_deskripsi_indonesia);

        card_reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reason_indo = new Intent(DetailIndonesiaActivity.this, ReasonIndonesiaActivity.class);
                startActivity(reason_indo);
            }
        });

        card_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cost_indo = new Intent(DetailIndonesiaActivity.this, DetailIndonesiaActivity.class);
                startActivity(cost_indo);
            }
        });

        card_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent education_indo = new Intent(DetailIndonesiaActivity.this, DetailIndonesiaActivity.class);
                startActivity(education_indo);
            }
        });

        card_study_provisions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent study_indo = new Intent(DetailIndonesiaActivity.this, DetailIndonesiaActivity.class);
                startActivity(study_indo);
            }
        });
        card_univ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent univ_indo = new Intent(DetailIndonesiaActivity.this, DetailIndonesiaActivity.class);
                startActivity(univ_indo);
            }
        });

        dataIndonesia();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void dataIndonesia(){
        // ini sialisasi permintaan data
        String URL = "http://"+getString(R.string.ip_server)+":8012/edupedia/indonesia_description.php";

        // memanggil object untuk request API
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL, // URL API
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // buat log respone dari API
                        Log.d("RestaRespone", response.toString());

                        try {
                            JSONArray jsArr = response.getJSONArray("results");
                            // tangkap data dari API
                            String desc = jsArr.getJSONObject(0).getString("description");
                            tv_deskripsi.setText(desc);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // jika ada error
                        Log.d("RestaRespone", error.toString());
                    }
                }
        );

        // eksekusi format request
        requestQueue.add(jsonObjectRequest);

    }
}