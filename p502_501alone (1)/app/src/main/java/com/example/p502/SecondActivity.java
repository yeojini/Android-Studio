package com.example.p502;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {
    TextView textView5,textView6;
    SupportMapFragment supportMapFragment;
    GoogleMap gmap;
    ArrayList<Hotdog> Hotdogs = new ArrayList<Hotdog>();
    ArrayList<Marker> array_marker = new ArrayList<Marker>();
    TextView hdname;
    View marker_root_view;
    private GoogleMap mMap;
    ImageView imageView;

    double a = 0.0003;
    double b = 0.0002;
    boolean flag;
    Hotdog hd1 = new Hotdog("6 guys",3000,R.drawable.hd01,0);
    Hotdog hd2 =new Hotdog("In and In",2500,R.drawable.hd02,0);
    Hotdog hd3 = new Hotdog("SakeSake",3500,R.drawable.hd03,0);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        final double latitude = bundle.getDouble("latitude");
        final double longitude = bundle.getDouble("longitude");
        flag = true;

        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView5.setText(""+latitude);
        textView6.setText(""+longitude);

        Hotdogs.add(hd1);
        Hotdogs.add(hd2);
        Hotdogs.add(hd3);



        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                setCustomMarkerView();
                gmap = googleMap;
                LatLng latLng = new LatLng(latitude,longitude);
                gmap.addMarker(new MarkerOptions().title("").position(latLng));
                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(flag) {


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    array_marker.add(addMarker(hd1,false,latitude+a,longitude+b));
                                    array_marker.add(addMarker(hd2,false,latitude+b,longitude+a));
                                    array_marker.add( addMarker(hd3,false,latitude,longitude+b));

                                    for (int i = 0; i < array_marker.size() - 1; i++) {
                                        Marker tmp = array_marker.get(i);
                                        tmp.setVisible(false);
                                    }
                                }
                            });
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            a += 0.0003;
                        }
                    }
                });
                t.start();


            }
        });


    }

    private Marker addMarker(Hotdog hotdog, boolean isSelectedMarker,double latitude, double longitude) {


        LatLng position = new LatLng(latitude,longitude);
        int price = hotdog.getHdcost();
        String formatted = NumberFormat.getCurrencyInstance().format((price));

        hdname.setText(hotdog.getHdname());

        imageView.setImageResource(hotdog.getHdimg());

        if (isSelectedMarker) {
            hdname.setText(formatted); //누르면 가격 표시
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(Integer.toString(price));
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));


        return mMap.addMarker(markerOptions);

    }

    private void setCustomMarkerView() {

        marker_root_view = LayoutInflater.from(this).inflate(R.layout.hotdoglayout, null);
        hdname = marker_root_view.findViewById(R.id.textView11);
        imageView = marker_root_view.findViewById(R.id.imageView2);

    }

    private Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache(); Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap); view.draw(canvas);
        return bitmap;
    }








}
