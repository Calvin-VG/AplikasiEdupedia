package com.example.edupedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

public class EducationAmerikaActivity extends AppCompatActivity {

    TextView education_amerika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_amerika);

        education_amerika = findViewById(R.id.tv_education_amerika);

        eduAmerika();

    }
    void eduAmerika(){
        // ini sialisasi permintaan data
        String URL = "http://"+getString(R.string.ip_server)+":8012/edupedia/america_education.php";

        // memanggil object untuk request API
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
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
                            for (int i = 0; i < jsArr.length(); i++) {
                                JSONObject amerika = jsArr.getJSONObject(i);
                                String reason = amerika.getString("reason");
                                education_amerika.setText(reason);
                            }

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