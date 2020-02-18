package com.example.p331;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.con,fragment1).commit();

        bottomNavigationView =
                findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                if(menuItem.getItemId() == R.id.tab1){
                    fragmentManager.beginTransaction().replace(R.id.con,fragment1).commit();
                    //Toast.makeText(MainActivity.this, "tab1", Toast.LENGTH_SHORT).show();
                }else if(menuItem.getItemId() == R.id.tab2){
                    fragmentManager.beginTransaction().replace(R.id.con,fragment2).commit();
                    Toast.makeText(MainActivity.this, "tab2", Toast.LENGTH_SHORT).show();
                }else if(menuItem.getItemId() == R.id.tab3){
                    fragmentManager.beginTransaction().replace(R.id.con,fragment3).commit();
                    Toast.makeText(MainActivity.this, "tab3", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}
