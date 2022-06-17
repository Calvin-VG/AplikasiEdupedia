package com.example.edupedia;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView img_user;
    TextInputEditText full_name_user, username_user, email_user, phone_user, password_user;
    Button btn_edit_user;
    Bitmap bitmap;
    String id_user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SelectProfile();

        img_user = view.findViewById(R.id.img_user);
        full_name_user = view.findViewById(R.id.full_name_user);
        username_user= view.findViewById(R.id.username_user);
        email_user = view.findViewById(R.id.email_user);
        phone_user = view.findViewById(R.id.phone_user);
        password_user = view.findViewById(R.id.password_user);
        btn_edit_user = view.findViewById(R.id.btn_edit_user);

        //cek permission
        //Jika belum diberi permission maka add permission melalui setting HP
        //jika tidak aktivitas ini tidak tampil
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getActivity().getPackageName()));
            getActivity().finish();
            startActivity(intent);
        }

        //Tambahkan listener untuk tombol upload
        view.findViewById(R.id.btn_img_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if everything is ok we will open image chooser
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        btn_edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String full_name = full_name_user.getText().toString();
                String username = username_user.getText().toString();
                String email = email_user.getText().toString();
                String phone_number = phone_user.getText().toString();
                String password = password_user.getText().toString();

                // kirim data input ke fungsi input tambahData()
                Update(full_name, username, email, phone_number, password, id_user);
            }
        });

        return view;
    }

    // jika user telah memilih foto, proses menjadi bitmap dan tampilkan dalam preview
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), imageUri);

                //displaying selected image to imageview
                img_user.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void SelectProfile() {
        // ini sialisasi permintaan data
        String URL = "http://"+getString(R.string.ip_server)+":8012/edupedia/selectprofil.php";

        // memanggil object untuk request API
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

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
                            id_user= jsArr.getJSONObject(0).getString("id_profile");
                            String image_user = jsArr.getJSONObject(0).getString("image");
                            full_name_user.setText(jsArr.getJSONObject(0).getString("full_name"));
                            username_user.setText(jsArr.getJSONObject(0).getString("username"));
                            email_user.setText(jsArr.getJSONObject(0).getString("email"));
                            phone_user.setText(jsArr.getJSONObject(0).getString("phone_number"));
                            password_user.setText(jsArr.getJSONObject(0).getString("password"));

                            // membuat format url gambar
                            String urlgambar = "http://"+getContext().getString(R.string.ip_server)+":8012/edupedia/image/"+image_user;
                            // memasukkan format url gambar pada komponen tampilan gambar
                            Picasso.get().load(urlgambar).into(img_user);


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

    // fungsi tambah data
    void Update(final String full_name, final String username, final String email, final String phone_number, final String password, final  String id_user){

        // ini sialisasi permintaan data
        String URL = "http://"+getString(R.string.ip_server)+":8012/edupedia/editprofil.php";

        // buka object lib yang digunakan melakukan requesr API
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // ubah gambar bitmap yang telah dipilih user menjadi string base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        // gambar yang telah jadi string simpan dalam variable encoded
        final String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        // untuk keperluan log
        Log.d("zzzResponse", encoded);

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
                params.put("id_profile", id_user);
                params.put("fileUpload", encoded); // kirim data gambar juga
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
        Toast.makeText(getContext(), "Reload agar profile berubah", Toast.LENGTH_LONG).show();
    }

}
















