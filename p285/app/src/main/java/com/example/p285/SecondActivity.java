package com.example.p285;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class SecondActivity extends AppCompatActivity {

    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView2 = findViewById(R.id.textView2);
        Intent intent =getIntent();
        Bundle bundle = intent.getBundleExtra("info");
        String id = bundle.getString("id");
        String pwd = bundle.getString("pwd");
        textView2.setText(id+"님 환영합니다!");
    }

    public void btck2(View v){
        clearstate();
        finish(); //다시 로그인 화면으로 이동
    }

    protected void clearstate(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        Log.v("aaaa",pref.getString("id",""));
        Log.v("aaaa",pref.getString("pwd",""));
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        Log.v("bbbb",pref.getString("id",""));
        Log.v("bbbb",pref.getString("pwd",""));
    }

}
