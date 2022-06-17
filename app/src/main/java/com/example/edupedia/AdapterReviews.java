package com.example.edupedia;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterReviews extends ArrayAdapter<String> {
    private ArrayList<String> image = new ArrayList<String>();
    private ArrayList<String> full_name = new ArrayList<String>();
    private ArrayList<String> rating = new ArrayList<String>();
    private ArrayList<String> comment = new ArrayList<String>();
    private Context context;

    // membuat constructor / jembatan supaya class ini bisa panggil dr class java lain
    public AdapterReviews(ArrayList<String> image,
                          ArrayList<String> full_name,
                          ArrayList<String> rating,
                          ArrayList<String> comment,
                          Context context){

        // tangkap parameter dan inisialisasi tampilan xml
        super(context, R.layout.item_reviews, full_name);

        // inisialisasi variable lokal dan parameter kiriman
        this.context = context;
        this.image = image;
        this.full_name = full_name;
        this.rating = rating;
        this.comment = comment;
    }

    // untuk menyisipkan data ke tampilan item_layout.xml
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        // untuk tiap data akan menggunakan tampilan item_layout (menghubungkan inflate ke layout)
        View row = inflater.inflate(R.layout.item_reviews, parent, false);

        // menghubungkan variable lokal dengan komponen di layout
        TextView fullnameReview = row.findViewById(R.id.tv_fullname);
        TextView ratingReview = row.findViewById(R.id.tv_rating1);
        TextView commentReview = row.findViewById(R.id.tv_comment);
        CircleImageView profileReview = row.findViewById(R.id.ic_profile);

        // variable yg terhubung digunakan untuk set data kedalam komponen tampilan
        fullnameReview.setText(full_name.get(position));
        ratingReview.setText(rating.get(position));
        commentReview.setText(comment.get(position));

        // membuat format url gambar
        String urlgambar = "http://"+context.getString(R.string.ip_server)+":8012/edupedia/imagereview/"+image.get(position);
        // memasukkan format url gambar pada komponen tampilan gambar
        Picasso.get().load(urlgambar).into(profileReview);

        return row;
    }
}