package com.example.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button button;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        final EditText getid = (EditText) findViewById(R.id.editText);
        final EditText getpwd = (EditText)findViewById(R.id.editText2);
        final EditText getname = (EditText)findViewById(R.id.editText3);
        final EditText getemail = (EditText)findViewById(R.id.editText4);

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String id = getid.getText().toString();
                String pwd = getpwd.getText().toString();
                String name = getname.getText().toString();
                String email = getemail.getText().toString();

                HashMap users = new HashMap<>();
                users.put("id", id);
                users.put("pwd", pwd);
                users.put("name", name);
                users.put("emil", email);

                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users").push().setValue(users); //데이터베이스에 users 라는 폴더를 만들어서 그 안에 데이터를 저장하겠다.
            }
        });


        }
    }
