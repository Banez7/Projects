package com.victor.pr1brickapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Switch;

public class SecondActivity extends AppCompatActivity {
    
    Switch switch1, switch2;
    SeekBar seekBar, seekBar2, seekBar3, seekBar4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        seekBar = findViewById(R.id.seekBar);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar3 = findViewById(R.id.seekBar3);
        seekBar4 = findViewById(R.id.seekBar4);
    }

    switch1.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            seekBar.setEnabled(isChecked);
        }
    }
            );
    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean filterByYear = prefs.getBoolean("filterByYear", false);
        chkFilterByYear.setChecked(filterByYear);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        boolean filterByYear = switch1.isChecked();
        ed.putBoolean("filterByYear", filterByYear);
        //boolean filterByParts = switch2.isChecked();
        ed.apply();
    }


    @Override public void onStart() { super.onStart(); Log.d("vkt","MainActivity.onStart()");}
    @Override public void onResume() { super.onResume(); Log.d("vkt","MainActivity.onResume()");}
    @Override public void onPause() { super.onPause(); Log.d("vkt","MainActivity.onPause()");}
    @Override public void onStop() { super.onStop(); Log.d("vkt","MainActivity.onStop()");}
    @Override public void onDestroy() { super.onDestroy(); Log.d("vkt","MainActivity.onDestroy()");}
}