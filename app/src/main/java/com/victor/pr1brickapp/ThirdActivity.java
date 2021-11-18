package com.victor.pr1brickapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        final Button btnTokenPage = findViewById(R.id.btnTokenPage);
        final Button btnDismiss = findViewById(R.id.btnDismiss);

        btnTokenPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent (ThirdActivity.this, TokenActivity.class);
                startActivity(intent);
            }
        });

        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
            finishAffinity();
            }
        });

    }

}