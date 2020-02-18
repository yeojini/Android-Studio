package com.example.p490;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView, imageView2, imageView3;
    TextView textView, textView2, textView3;
    int random1, random2, random3;

    MyHandler myHandler;
    Handler handler;
    MyTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);


        myHandler = new MyHandler();
        handler = new Handler();

        t.start();
        Thread t2 = new Thread(r);
        t2.start();

        task = new MyTask();
        task.execute();

    }

    Thread t = new Thread() {
        @Override
        public void run() {
            while(true) {
                random1 = (int) (Math.random() * 100) + 1;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final int rd1 = random1;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (rd1 >= 70) {
                            imageView.setImageResource(R.drawable.unhappy);
                        } else if (rd1 < 70 && rd1 >= 40) {
                            imageView.setImageResource(R.drawable.normal);
                        } else {
                            imageView.setImageResource(R.drawable.happy);
                        }
                        textView.setText(rd1 + "km");

                    }
                });
            }

        }
    };

    Runnable r = new Runnable() {

        @Override
        public void run() {
            while(true) {
                random2 = (int) (Math.random() * 100) + 1;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = myHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("temp", random2);
                message.setData(bundle);
                myHandler.sendMessage(message);

            }
        }
    };

    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int rd2 = bundle.getInt("temp");

            if (rd2 >= 70) {
                imageView2.setImageResource(R.drawable.unhappy);
            } else if (rd2 < 70 && rd2 >= 40) {
                imageView2.setImageResource(R.drawable.normal);
            } else {
                imageView2.setImageResource(R.drawable.happy);
            }
            textView2.setText(rd2 + "도");
        }
    }

    class MyTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            random3 = 0;
            textView3.setText(random3+"rpm");
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            while(isCancelled()==false) {
                random3 = (int) (Math.random() * 100) + 1;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress(random3);
            }
                return random3;


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int rd3 = values[0];
            if (rd3 >= 70) {
                imageView3.setImageResource(R.drawable.unhappy);

            } else if (rd3 < 70 && rd3 >= 40) {
                imageView3.setImageResource(R.drawable.normal);
            } else {
                imageView3.setImageResource(R.drawable.happy);
            }
            Log.d("---",rd3+"");
            textView3.setText(rd3 + "rpm");
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }
}
