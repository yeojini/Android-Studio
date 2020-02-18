package com.example.p353;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    boolean flag = true;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("---","MyService .. onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("---","My Service ... onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("---","MyService .. onStartCommand");
        final Intent sintent = new Intent(
                getApplicationContext(),MainActivity.class
        );
        sintent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
        );



        Runnable r = new Runnable(){

            @Override
            public void run() {
                for(int i=0;i<=50;i++){
                    if(flag != true){
                        break;
                    }
                    sintent.putExtra("cnt",i);
                    startActivity(sintent);
                    Log.d("---","My Service ... onStartCommand"+i);
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
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
        Log.d("---","My Service ... onDestroy");
    }
}
