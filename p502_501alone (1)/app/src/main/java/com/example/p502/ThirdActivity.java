package com.example.p502;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {
    TextView textView8;
    SupportMapFragment supportMapFragment;
    GoogleMap gmap2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent intent = getIntent();
        final ArrayList<Double> lats = (ArrayList<Double>) intent.getSerializableExtra("lats");
        final ArrayList<Double> lons = (ArrayList<Double>) intent.getSerializableExtra("lons");

        Log.d("---",""+lats);

        textView8 = findViewById(R.id.textView8);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap2 = googleMap;
                for (int i=0;i<lats.size();i++ ){
                    LatLng latLng = new LatLng(lats.get(i),lons.get(i));
                    Log.d("---",""+latLng);
                    gmap2.addMarker(new MarkerOptions().title("Hello World").position(latLng));
                }
                LatLng center = new LatLng(34.043462, -118.267233);
                gmap2.animateCamera(CameraUpdateFactory.newLatLngZoom(center,4));
                gmap2.setMyLocationEnabled(true);
            }
        });

    }
}