package com.example.p287;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View1Fragment.View1Manager {

    View1Fragment view1Fragment;
    View2Fragment view2Fragment;
    View3Fragment view3Fragment;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.m6);
        actionBar.setTitle("MYAPP");
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_HOME |
                        ActionBar.DISPLAY_SHOW_TITLE
        );
        view1Fragment = (View1Fragment) getSupportFragmentManager().findFragmentById(
                R.id.fragment
        );
        //view1Fragment = new View1Fragment();
        view2Fragment = new View2Fragment();
        view3Fragment = new View3Fragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mi1){
            changeView(1);
        }else if(item.getItemId() == R.id.mi2){
            changeView(2);
        }else if(item.getItemId() == R.id.mi3){
            changeView(3);
        }

        return super.onOptionsItemSelected(item);
    }

    public void ckbt(View v){
        if(v.getId() == R.id.button){
            changeView(1);
        }else if(v.getId() == R.id.button2){
            changeView(2);
        }else if(v.getId() == R.id.button3) {
            changeView(3);
        }
    }
    public void changeView(int i){
        if(i == 1){
            changeTx("OK");
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.container,view1Fragment
            ).commit();

        }else if(i == 2){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.container,view2Fragment
            ).commit();

        }else if(i == 3){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.container,view3Fragment
            ).commit();
        }
    }

    @Override
    public void changeTx(String str) {
        view1Fragment.setText("ss");

    }
}
