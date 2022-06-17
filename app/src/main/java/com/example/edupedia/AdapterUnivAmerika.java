package com.example.edupedia;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUnivAmerika extends ArrayAdapter<String> {
    private ArrayList<String> logouniv = new ArrayList<String>();
    private ArrayList<String> namauniv = new ArrayList<String>();
    private ArrayList<String> description = new ArrayList<String>();
    private Context context;

    // membuat constructor / jembatan supaya class ini bisa panggil dr class java lain
    public AdapterUnivAmerika(ArrayList<String> logouniv,
                              ArrayList<String> namauniv,
                              ArrayList<String> description,
                              Context context) {

        // tangkap parameter dan inisialisasi tampilan xml
        super(context, R.layout.item_univ_amerika, namauniv);

        // inisialisasi variable lokal dan parameter kiriman
        this.context = context;
        this.logouniv = logouniv;
        this.namauniv = namauniv;
        this.description = description;
    }

    // untuk menyisipkan data ke tampilan item_layout.xml
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        // untuk tiap data akan menggunakan tampilan item_layout (menghubungkan inflate ke layout)
        View row = inflater.inflate(R.layout.item_univ_amerika, parent, false);

        // menghubungkan variable lokal dengan komponen di layout
        TextView univ_name = row.findViewById(R.id.tv_univ_name);
        CircleImageView univ_logo = row.findViewById(R.id.ic_univ_logo);
        TextView univ_description = row.findViewById(R.id.tv_univ_desc);

        // variable yg terhubung digunakan untuk set data kedalam komponen tampilan
        univ_name.setText(namauniv.get(position));
        univ_description.setText(description.get(position));

        // membuat format url gambar
        String urlgambar = "http://" + context.getString(R.string.ip_server) + ":8012/edupedia/universitylogo/" + logouniv.get(position);
        // memasukkan format url gambar pada komponen tampilan gambar
        Picasso.get().load(urlgambar).into(univ_logo);

        return row;
    }
}