package com.example.p7012;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.p7012.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    SupportMapFragment sMapFragment;
    GoogleMap gmap;
    LocationManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);


        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this,permissions, 101);



        sMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        sMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;
                LatLng latLng =
                        new LatLng(35.0101371,128.9475055);
                gmap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Oruk-Do"));
                gmap.animateCamera(CameraUpdateFactory.
                        newLatLngZoom(latLng,15));
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                gmap.setMyLocationEnabled(true);

                manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                MyLocation myLocation = new MyLocation();
                long minTime = 1;
                float minDistance = 0;
                manager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        minTime,
                        minDistance,
                        myLocation
                );
            }
        });

    } // onCreate


    class MyLocation implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            String loc = lat + " " + lon;
            textView.setText(loc);
            LatLng latLng =
                    new LatLng(lat,lon);

            gmap.animateCamera(CameraUpdateFactory.
                    newLatLngZoom(latLng,15));
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
