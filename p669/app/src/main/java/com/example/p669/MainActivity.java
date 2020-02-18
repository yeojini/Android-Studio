package com.example.p669;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    SupportMapFragment supportMapFragment;
    Button button, button2;

    GoogleMap gmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        String [] permissions ={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(
                this,permissions,101); // 위험 권한 부여 요청하기


        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;
                LatLng latLng = new LatLng(37.5065191,127.028721);
                gmap.addMarker(new MarkerOptions().position(latLng).title("First Position"));
                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
            }
        });
    }

    public void ckbt(View v){
        if(v.getId()==R.id.button){
            LatLng latLng2 = new LatLng(37.243810, 127.078151);
            gmap.addMarker(new MarkerOptions().position(latLng2).title("Second Position"));
            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2,15));
        }else{
            LatLng latLng3 = new LatLng(37.251501, 127.071217);
            gmap.addMarker(new MarkerOptions().position(latLng3).title("Second Position"));
            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng3,15)); //뒤 숫자 : 확대 배율
        }



    }

}
