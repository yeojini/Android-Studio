package com.example.p502;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText id;
    EditText pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.editText);
        pw = findViewById(R.id.editText2);

    }


    public void login(View v){
        if(id.getText().toString().equals("id01") && pw.getText().toString().equals("pw01")){

            Toast.makeText(this, "로그인하셨습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("id", "id01");
            startActivity(intent);
        }else{
            Toast.makeText(this, "다시 시도해 주세요", Toast.LENGTH_SHORT).show();
        }

    }
}
