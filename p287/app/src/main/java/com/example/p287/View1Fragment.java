package com.example.p287;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class View1Fragment extends Fragment {

    EditText editText,editText2;
    Button button4;
    TextView textView;
    String str;
    public View1Manager manager;

    public View1Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_view1,container,false);
        editText = viewGroup.findViewById(R.id.editText);
        editText2 = viewGroup.findViewById(R.id.editText2);
        button4 = viewGroup.findViewById(R.id.button4);
        textView = viewGroup.findViewById(R.id.textView);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editText.getText().toString();
                String pwd = editText2.getText().toString();
                textView.setText(id+" "+pwd);
            }
        });
        return viewGroup;
    }

    public void setText(String str){
        this.str = str;
        textView.setText(str);
    }
    public static interface View1Manager{
        public void changeTx(String str);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof View1Manager){
            manager = (View1Manager)context;
        }
    }
}



