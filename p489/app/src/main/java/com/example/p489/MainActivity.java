package com.example.p489;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Button button,button2;
    ProgressBar progressBar;
    MyTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar2);
        button2.setEnabled(false);

    }

    public void ckbt(View v){
        if(v.getId()==R.id.button){
            task = new MyTask();
            task.execute(50); //초기화값 Integer 로 들어감

        }else {
            task.cancel(true);
            task.onCancelled();

        }
    }

    //스레드 제어
    //초기화값,중간값,끝값
    class MyTask extends AsyncTask<Integer,Integer,String>{

        @Override
        protected void onPreExecute() {
            progressBar.setMax(50);
            button.setEnabled(false);
            button2.setEnabled(true);
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
            button.setEnabled(true);
            button2.setEnabled(false);
        }

        //여기서만 스레드. 나머지 메서드에서는 자유롭게 UI 건드릴 수 있다.
        @Override
        protected String doInBackground(Integer... integers) {
            int cnt = integers[0]; //... : 한꺼번에 여러개 넣을 수 있다
            int sum = 0;
            for(int i=0;i<cnt;i++){
                if(isCancelled() == true){
                    break;
                }
                try {
                    Thread.sleep(100); //0.1 초에 한번씩 연산
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sum += i;
                publishProgress(i); //onProgress 의 매개변수 Integer 로 i 가 간다.
            }
            return "Result:"+sum;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int i = values[0].intValue();
            progressBar.setProgress(i);
            textView.setText(i+"");
            if(i%2==0){
                imageView.setImageResource(R.drawable.d);
            }else
                imageView.setImageResource(R.drawable.c);
        }

        @Override
        protected void onCancelled() {
            progressBar.setProgress(0);
            textView.setText("");
            button.setEnabled(true);
            button2.setEnabled(false);

        }
    }


}
