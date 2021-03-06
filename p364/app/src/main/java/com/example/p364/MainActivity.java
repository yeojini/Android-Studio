package com.example.p364;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        String[] permissions = {
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.CALL_PHONE
        };
        ActivityCompat.requestPermissions(
                this,permissions,101);

        int check =
                ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.RECEIVE_SMS
                );

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        if(check == PackageManager.PERMISSION_GRANTED){
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        }
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                ConnectivityManager cm = null;
                NetworkInfo mobile = null;
                NetworkInfo wifi = null;
                if(action.equals("android.net.conn.CONNECTIVITY_CHANGE")){
                    cm = (ConnectivityManager)context.getSystemService(
                            Context.CONNECTIVITY_SERVICE);
                    mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if(mobile != null && mobile.isConnected()){
                        Toast.makeText(context, "MOBILE", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.up);
                    }else if(wifi != null && wifi.isConnected()){
                        Toast.makeText(context, "WIFI", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.mk);

                    }else{
                        Toast.makeText(context, "DISCONNECTED", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.down);

                    }
                }else if(action.equals("android.provider.Telephony.SMS_RECEIVED")){
                    Toast.makeText(context, "SMS_RECEIVED", Toast.LENGTH_SHORT).show();
                    Bundle bundle = intent.getExtras();
                    Object [] objs = (Object [])bundle.get("pdus");
                    SmsMessage [] messages = new SmsMessage[objs.length];
                    for(int i=0;i<objs.length;i++){
                        String format = bundle.getString("format");
                        messages[i] = SmsMessage.createFromPdu(
                                (byte[])objs[i],format );
                    }
                    String msg = "";
                    if(messages != null && messages.length > 0){
                        msg += messages[0].getOriginatingAddress();
                        msg += messages[0].getMessageBody().toString();
                        msg +=
                                new Date(messages[0].getTimestampMillis()).toString();
                        textView.setText(msg);
                    }
                }
            }
        };
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public void ckbt(View v){
        if(v.getId() == R.id.button2){
            int check =
                    PermissionChecker.checkSelfPermission(
                            this,
                            Manifest.permission.CALL_PHONE
                    );
            if(check == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(
                        Intent.ACTION_CALL, Uri.parse("tel:010-0000-0000")
                );
                startActivity(intent);
            }

        }else{
            SmsManager smsManager =
                    SmsManager.getDefault();
            smsManager.sendTextMessage(
                    "tel:010-0000-0000",null,"ssss",null,null
            );
            Toast.makeText(this, "send", Toast.LENGTH_SHORT).show();
        }
    }
}
