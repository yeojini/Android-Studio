package com.example.p275;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("---","onCreate");
    }
    protected void restoreState(){

    }
    protected void saveState(){
        SharedPreferences sp = getSharedPreferences("st", Activity.MODE_PRIVATE);
    }
    protected void 

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("---","onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("---","onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("---","onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("---","onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("---","onDestroy");
    }
}
