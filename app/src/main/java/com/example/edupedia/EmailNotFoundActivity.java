package com.example.edupedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmailNotFoundActivity extends AppCompatActivity {

    Button btn_repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_not_found);

        btn_repeat = findViewById(R.id.btn_repeat);

        btn_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(EmailNotFoundActivity.this, ForgotPasswordActivity.class);
                startActivity(signup);
            }
        });

    }
}