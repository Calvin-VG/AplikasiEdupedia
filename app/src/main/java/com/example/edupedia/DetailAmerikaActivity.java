package com.example.edupedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DetailAmerikaActivity extends AppCompatActivity {

    Toolbar toolbar;
    CardView card_reason, card_cost, card_education, card_study_provisions, card_univ;
    TextView tv_deskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_amerika);

        card_reason = findViewById(R.id.card_reason);
        card_cost = findViewById(R.id.card_cost);
        card_education = findViewById(R.id.card_education);
        card_study_provisions = findViewById(R.id.card_study_provisions);
        card_univ = findViewById(R.id.card_univ);
        tv_deskripsi = findViewById(R.id.tv_deskripsi);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        card_reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reason_amerika = new Intent(DetailAmerikaActivity.this, ReasonAmerikaActivity.class);
                startActivity(reason_amerika);
            }
        });

        card_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cost_amerika = new Intent(DetailAmerikaActivity.this, CostOfLivingAmerikaActivity.class);
                startActivity(cost_amerika);
            }
        });

        card_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent education_amerika = new Intent(DetailAmerikaActivity.this, EducationAmerikaActivity.class);
                startActivity(education_amerika);
            }
        });

        card_study_provisions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent study_amerika = new Intent(DetailAmerikaActivity.this, StudyProvisionsAmerikaActivity.class);
                startActivity(study_amerika);
            }
        });
        card_univ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent univ_amerika = new Intent(DetailAmerikaActivity.this, UnivAmerikaActivity.class);
                startActivity(univ_amerika);
            }
        });

        dataAmerika();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void dataAmerika(){
        // ini sialisasi permintaan data
        String URL = "http://"+getString(R.string.ip_server)+":8012/edupedia/america_description.php";

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}