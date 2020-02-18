package com.example.p285;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText,editText2;
    Button button;
    String id,pwd;
    Toast t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);

        button = findViewById(R.id.button);
        restoreState();
    }
    public void btck(View v){
        id = editText.getText().toString();
        pwd = editText2.getText().toString();
        Log.v("---",id);
        Log.v("---",pwd);
        if(id.equals("qqq")&&pwd.equals("111")){
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                Bundle bundle = new Bundle(); //번들에 담아 인텐트로 넘겨
                bundle.putString("id",id);
                bundle.putString("pwd",pwd);
                intent.putExtra("info",bundle);
                startActivity(intent);

        }else{
            t=Toast.makeText(this,"로그인 실패",Toast.LENGTH_SHORT);
            t.show();

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        editText2.setText("");
        editText.setText("");

    }

    protected void saveState(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id",id);
        editor.putString("pwd",pwd);
        editor.commit();
    }

    protected void restoreState(){
        SharedPreferences pref = getSharedPreferences("pref",Activity.MODE_PRIVATE);
        Log.v("cccc",pref.getString("id",""));
        Log.v("cccc",pref.getString("pwd",""));
        if((pref!=null)&&(pref.contains("id"))&&(pref.contains("pwd"))){
            String id = pref.getString("id","");
            String pwd = pref.getString("pwd","");
            editText.setText(id);
            editText2.setText(pwd);
            btck(new View(this)); //다시 얘를 호출함으로써 이 화면에서 SecondActivity 로 넘어가게
        }

    }

}
