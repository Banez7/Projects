package com.victor.pr1brickapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSearch = findViewById(R.id.btnSearch);
        Button btnConfig = findViewById(R.id.btnConfig);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("vktr","onClick()"); /*veurem al logcat*/

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intent = new Intent (MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intent = new Intent (MainActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override public void onStart() { super.onStart(); Log.d("vkt","MainActivity.onStart()");}
    @Override public void onResume() { super.onResume(); Log.d("vkt","MainActivity.onResume()");}
    @Override public void onPause() { super.onPause(); Log.d("vkt","MainActivity.onPause()");}
    @Override public void onStop() { super.onStop(); Log.d("vkt","MainActivity.onStop()");}
    @Override public void onDestroy() { super.onDestroy(); Log.d("vkt","MainActivity.onDestroy()");}
}