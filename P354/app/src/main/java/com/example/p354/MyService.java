package com.example.p354;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    boolean flag = true;

    class MyBinder extends Binder {
        public MyService getService(){
            return MyService.this;
        }
    }
    IBinder binder = new MyBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    public void bt1(){
        final Intent sintent = new Intent(
                getApplicationContext(),MainActivity.class
        );
        sintent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
        );
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=50;i++){
                    if(flag != true){
                        break;
                    }
                    sintent.putExtra("cnt1",i);
                    startActivity(sintent);
                    Log.d("---","MyService .. onStartCommand:"+i);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
    public void bt2(){
        final Intent sintent = new Intent(
                getApplicationContext(),MainActivity.class
        );
        sintent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
        );
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=50;i++){
                    if(flag != true){
                        break;
                    }
                    sintent.putExtra("cnt2",i);
                    startActivity(sintent);
                    Log.d("---","MyService .. onStartCommand:"+i);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
        Log.d("---","MyService .. onDestroy");

    }


}
