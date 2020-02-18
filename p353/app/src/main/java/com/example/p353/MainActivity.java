package com.example.p353;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button;
    ProgressBar progressBar;
    TextView textView;
    Intent intent = null;

    //sintent 가 들어온다.
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        process(intent);
    }

    public void process(Intent intent){
        if(intent != null){
            int cnt = intent.getIntExtra("cnt",0);
            textView.setText("Cnt:"+cnt);
            progressBar.setProgress(cnt);
            if(cnt == 50){
                button.setEnabled(true);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(50);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(intent!=null){
            stopService(intent);
        }
    }

    public void ckbt(View v){
        intent = new Intent(getApplicationContext(),MyService.class);
        startService(intent);
        button.setEnabled(false);
    }
}
