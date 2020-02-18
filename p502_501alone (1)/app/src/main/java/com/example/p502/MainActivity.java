package com.example.p502;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView narrowbar;
    ArrayList<Double> lats;
    ArrayList<Double> lons;
    ArrayList<Item> list;
    ItemAdapter itemAdapter;
    LinearLayout container;
    ProgressDialog progressDialog;
    ListView listView;
    double myLat;
    double myLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //권한 허가
        permissionCheck();
        narrowbar = findViewById(R.id.textView7);
        setNarrowbar();
        container = findViewById(R.id.container);
        listView = findViewById(R.id.listView);
        lats = new ArrayList<>();
        lons = new ArrayList<>();


        //내 위치 가져오기
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        myLocationListener locationListener = new myLocationListener();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 100, locationListener);


        progressDialog = new ProgressDialog(this);
        list = new ArrayList<>();
        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "sdf", Toast.LENGTH_SHORT).show();
                Item item = list.get(position);
                Log.d("---", "onclick");
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("latitude", item.getLatitude());
                bundle.putDouble("longitude", item.getLongitude());
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });//setOnItemClickListener Method
    }//onCreate Method

    public void permissionCheck() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this, permissions, 101);
    }

    private void setNarrowbar (){
        Intent preIntent = getIntent();
        String id = preIntent.getStringExtra("id");
        narrowbar.setText(id+"님 환영합니다");

    }

    private void getData() {
        String url = "http://70.12.113.194/webview/nba2.jsp";
        ItemAsync itemAsync = new ItemAsync(url);
        itemAsync.execute();
    }//getData Method

    public void ckbt(View v) {
        if (v.getId() == R.id.button) {
            Collections.sort(list, Item.sortReviews);
            itemAdapter = new ItemAdapter(list);
            listView.setAdapter(itemAdapter);
        } else if (v.getId() == R.id.button2) {
            Collections.sort(list, Item.sortDistance);
            itemAdapter = new ItemAdapter(list);
            listView.setAdapter(itemAdapter);
        } else if (v.getId() == R.id.button3) {
            Collections.sort(list, Item.sortName);
            itemAdapter = new ItemAdapter(list);
            listView.setAdapter(itemAdapter);
        }else if(v.getId()==R.id.button4){
            Log.d("---","bt4"+lats);
            Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
            intent.putExtra("lats",lats);
            intent.putExtra("lons",lons);
            startActivity(intent);
        }

    }

    class ItemAsync extends AsyncTask<Void, Void, String> {
        String url;

        public ItemAsync(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("HTTP Connect...");
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected String doInBackground(Void... voids) {
            Log.d("---", "ItemAsync 동작");
            String result = null;
            result = HttpHandler.getString(url);
            return result;
        }//doInBackground Method

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();



            Log.d("---","onPostExecute : "+s);
            JSONArray ja = null;
            try {

                ja = new JSONArray(s);

                Log.d("---","ja : "+ja);
                for (int i=0;i<ja.length();i++){
                    JSONObject jo = ja.getJSONObject(i);
                    String img = jo.getString("img");
                    String name = jo.getString("name");
                    String team = jo.getString("team");
                    int reviews = jo.getInt("reviews");
                    int capacity = jo.getInt("capacity");
                    double latitude = jo.getDouble("latitude");
                    double longitude = jo.getDouble("longitude");
                    double dist = getDistance(latitude, longitude);
                    Item item = new Item(img,name,team,capacity,reviews,latitude,longitude, dist);
                    lats.add(latitude);
                    lons.add(longitude);
                    list.add(item);
                    Log.d("---","list : "+list);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            itemAdapter = new ItemAdapter(list);
            listView.setAdapter(itemAdapter);
            Log.d("---","list : " + list.toString());



        }//onPostExecute Method

        public double getDistance (double lat, double lon){

            double dLat = Math.toRadians(myLat - lat);
            double dLon = Math.toRadians(myLon - lon);
            double lat1 = Math.toRadians(lat);
            double lat2 = Math.toRadians(myLat);

            double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.asin(Math.sqrt(a));
            //kilometer
            return 6372.8 * c;


        }




    }//ItemAsync inner class

    class ItemAdapter extends BaseAdapter{
        ArrayList<Item> ilist;


        public ItemAdapter(ArrayList<Item> ilist) {
            this.ilist = ilist;
        }

        @Override
        public int getCount() {
            return ilist.size();
        }

        @Override
        public Object getItem(int position) {
            return ilist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = null;
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item,container,true);
            //container를 전역변수로 설정해 줘야 한다.
            final ImageView imageView = itemView.findViewById(R.id.imageView);
            TextView name = itemView.findViewById(R.id.textView2);
            TextView team = itemView.findViewById(R.id.textView3);
            TextView capacity = itemView.findViewById(R.id.textView4);
            RatingBar reviews = itemView.findViewById(R.id.ratingBar);
            TextView dist = itemView.findViewById(R.id.textView0);

            Item item = ilist.get(position);

            name.setText(" "+item.getName());
            team.setText(" location: "+item.getTeam());
            capacity.setText(" cap: "+item.getCapacity() + " people");
            reviews.setRating(item.getReviews());
            dist.setText("dist:"+Math.round(item.getDist()*100)/100.0 + "km");

            String img = item.getImg();
            img = "http://70.12.113.194/webview/img/"+img;

            final String finalImg = img;

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    URL url = null;
                    InputStream is = null;
                    try {
                        url = new URL(finalImg);
                        is = url.openStream();
                        final Bitmap bm = BitmapFactory.decodeStream(is);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bm);
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            t.start();
            return itemView;
        }
    }//ItemAdapter inner class

    class myLocationListener implements LocationListener {

        @Override
        //위치가 확인되었을 때 자동으로 호출되는 메소드
        public void onLocationChanged(Location location) {
            myLat = location.getLatitude();
            myLon = location.getLatitude();

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




}//MainActivity