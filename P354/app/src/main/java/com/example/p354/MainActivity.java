package com.example.p354;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button,button2;
    TextView textView,textView2;
    ProgressBar progressBar,progressBar2;

    MyService myService;

    ServiceConnection sconn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder mb =
                    (MyService.MyBinder) service;
            myService = mb.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        Intent intent = new Intent(this,
                MyService.class);
        bindService(intent,sconn, Context.BIND_AUTO_CREATE);

    }
    public void bt1(View v){
        myService.bt1();
    }
    public void bt2(View v){
        myService.bt2();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        process(intent);
    }
    public void process(Intent intent){
        int cnt1 = intent.getIntExtra("cnt1",0);
        int cnt2 = intent.getIntExtra("cnt2",0);
        if(cnt1 != 0){
            textView.setText("Result:"+cnt1);
            progressBar.setProgress(cnt1);
        }else if(cnt2 != 0){
            textView2.setText("Result:"+cnt2);
            progressBar2.setProgress(cnt2);
        }
    }
}

//메인 액티비티에 바인더 객체 전달
// 바인더에서는 myservice 를 끄집어 낼 수 있다.
//메인액티비티는 바인더 객체로 myservice 참조 가능. myservice 안에 있는 bt1() 과 bt2() 메서드 참조 가능.