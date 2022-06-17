package com.example.edupedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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

public class ReviewsFragment extends Fragment {

    private ListView listView;
    ArrayList<String> image = new ArrayList<String>();
    ArrayList<String> full_name = new ArrayList<String>();
    ArrayList<String> rating = new ArrayList<String>();
    ArrayList<String> comment = new ArrayList<String>();

    private AdapterReviews adapter;
    TextView tv_judul;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        tv_judul = (TextView) view.findViewById(R.id.tv_judul);

        // koneksikan variable listview ke komponennya di .xml
        listView = (ListView) view.findViewById(R.id.lv_reviews);

        ambilData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    void ambilData(){
        // inisialisasi permintaan data
        String URL = "http://"+getString(R.string.ip_server)+":8012/edupedia/select_review.php";
        // memanggil object untuk request API
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // format request data
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, // menggunakan metode GET
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
                                image.add(jsArr.getJSONObject(i).getString("image"));
                                full_name.add(jsArr.getJSONObject(i).getString("full_name"));
                                rating.add(jsArr.getJSONObject(i).getString("rating"));
                                comment.add(jsArr.getJSONObject(i).getString("comment"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // S: Bagian Adapter
                        // melempar data ke java class ListViewAdapter
                        adapter = new AdapterReviews(image, full_name, rating, comment, getContext());
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


//        return inflater.inflate(R.layout.fragment_reviews, container, false);
    }
}