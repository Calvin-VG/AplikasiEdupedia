package com.example.edupedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UnivAmerikaActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterUnivAmerika adapter;
    ArrayList<String> logouniv = new ArrayList<String>();
    ArrayList<String> namauniv = new ArrayList<String>();
    ArrayList<String> description = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_univ_amerika);

        // koneksikan variable listview ke komponennya di .xml
        listView = findViewById(R.id.lv_univ_amerika);

        getData();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void getData(){
        // ini sialisasi permintaan data
        String URL = "http://"+getString(R.string.ip_server)+":8012/edupedia/america_university.php";

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
                            // tangkap data dari API
                            for (int i = 0; i < jsArr.length(); i++) {
                                logouniv.add(jsArr.getJSONObject(i).getString("university_logo"));
                                namauniv.add(jsArr.getJSONObject(i).getString("university_name"));
                                description.add(jsArr.getJSONObject(i).getString("description"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // S: Bagian Adapter
                        // melempar data ke java class ListViewAdapter
                        adapter = new AdapterUnivAmerika(logouniv,namauniv, description, UnivAmerikaActivity.this);
                        // tampilkan hasil sisipan data dari java class
                        listView.setAdapter(adapter);
                        // E: Bagian Adapter

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