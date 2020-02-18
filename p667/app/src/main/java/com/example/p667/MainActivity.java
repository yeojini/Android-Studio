package com.example.p667;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    LocationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        String [] permissions ={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(
                this,permissions,101); // 위험 권한 부여 요청하기

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocation myLocation = new MyLocation();
        long minTime = 1;
        float minDistance = 0;
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                myLocation
        );
    }

    public void ckbt(View v){
            startLocationService();
        Log.d("---","button clicked");
    }

    private void startLocationService(){
        //현재 나의 위치
        Log.d("---","startLocationService");
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //LocationManager 객체 참조하기
        try {
            Location location = null;
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //네트워크로 바꾸면 핸드폰에서 확인
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String message = "최근 위치 - latitue:"+latitude+"\nLongitude:"+longitude;
                textView.setText(message);
            }
/*
            MyLocation myLocation = new MyLocation();
            long minTime = 10000; // minTime 최소한 얼마만의 시간이 흐른후 위치정보를 받을건지 시간간격을 설정 설정하는 변수
            float minDistance = 0;  // minDistance 얼마만의 거리가 떨어지면 위치정보를 받을건지 설정하는 변수
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, myLocation);*/



        }catch(Exception e){
            e.printStackTrace();
        }

    }

    class MyLocation implements LocationListener{

        //바뀌는 것에 초점
        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            String loc = lat + " "+lon;
            textView.setText(loc);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


}
