package com.example.p473;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView,textView2;
    ProgressBar progressBar,progressBar2;
    Button button,button2,button3;

    MyHandler myHandler;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        myHandler = new MyHandler();
        handler = new Handler();
    }

    public void ckbt(View v){
        //thread 객체를 통해 만드는 방법
        if(v.getId() == R.id.button){
            t.start();
            button.setEnabled(false);
        }else if (v.getId() == R.id.button2){
            //러너블이라는 인터페이스를 통해 만드는 방법
            Thread t = new Thread(r);
            t.start();
            button2.setEnabled(false);
        }else if(v.getId() == R.id.button3){
            delay();
        }
    }

    Thread t = new Thread() {
        @Override
        public void run() {
            for(int i=0;i<50;i++){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //textView.setText("T1"+i);
                //서브 thread 에서는 mainthread 의 UI를 건드릴 수 없다.

                //해결책 - UI Manager
                final int finalI = i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(finalI+"");
                        progressBar.setProgress(finalI);
                    }
                });

                Log.d("---","T1:"+i);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    button.setEnabled(true);
                }
            });
        }
    };

//서브쓰레드에서 발생한 i 를 계쏙 main 에 던져서 값 수정
    Runnable r = new Runnable() {
        @Override
        public void run() {
            for(int i=0;i<50;i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //textView.setText(i + "");
                Log.d("---", "R1:" + i);
                Message message = myHandler.obtainMessage(); //메시지 객체 참조
                Bundle bundle = new Bundle();
                bundle.putInt("cnt",i);
                message.setData(bundle);
                myHandler.sendMessage(message);// 메시지 큐에 넣기
            }
        }
    };

    //핸들러 안에서 전달받은 메시지 객체 처리
    class MyHandler extends Handler{

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            //Message 객체 내의 Bundle 객체를 이용해 value 값을 전달
            Bundle bundle = msg.getData();
            int cnt = bundle.getInt("cnt");
            textView2.setText(cnt+"");
            progressBar2.setProgress(cnt);
            if(cnt == 49){
                button2.setEnabled(true);
            }
        }
    }


    //버튼 누르면 프로그레스바 띄우기
    //anonymous class 안에서 변수 사용 시 final 로 사용해야함
    //yes 클릭하면 5초 뒤 프로그레스다이얼로그 꺼짐
    public void delay(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delay..");
        dialog.setMessage("5 seconds ....");
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.setCancelable(false);
                progressDialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 5000);
            }
        });
        dialog.show();
    }

}
