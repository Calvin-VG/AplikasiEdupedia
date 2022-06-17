package com.example.edupedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {

    CardView card_amerika, card_australia, card_indonesia, card_malaysia, card_singapura;
    TextView username_input;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        card_amerika = view.findViewById(R.id.card_amerika);
        card_australia = view.findViewById(R.id.card_australia);
        card_indonesia = view.findViewById(R.id.card_indonesia);
        card_malaysia = view.findViewById(R.id.card_malaysia);
        card_singapura = view.findViewById(R.id.card_singapura);
        username_input = view.findViewById(R.id.username_input);

        Bundle datalogin = getActivity().getIntent().getExtras();
        String username = datalogin.getString("username");
        username_input.setText(username);

        card_amerika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent amerika = new Intent(getContext(), DetailAmerikaActivity.class);
                startActivity(amerika);
            }
        });

        card_australia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent australia = new Intent(getContext(), DetailAustraliaActivity.class);
                startActivity(australia);
            }
        });

        card_indonesia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent indo = new Intent(getContext(), DetailIndonesiaActivity.class);
                startActivity(indo);
            }
        });

        return  view;
    }
}















