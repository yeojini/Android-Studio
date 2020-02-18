package com.example.p436;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    TextView textView;
    String [] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);
        textView = findViewById(R.id.textView);
        getData();
        //텍스트 뷰와 스피너 객체를 클래스 안에 선언된 변수에 할당

        //어댑터에 데이터(items) set
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items); //최초 화면 레이아웃 지정
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //항목을 선택하기 위한 창 레이아웃 설정

        spinner.setAdapter(adapter); //파라미터로 어댑터 객체 전달


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //스피너에 리스터 설정하기
        //스피너 객체가 아이템 선택 이벤트를 처리할 수 있도록 하는 리스너

            //스피너에서 아이템 선택 시 onItemSelected() 메서드 자동 호출
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(items[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView.setText("");
            }
        });

    }
    private void getData(){
        items = new String[]{
                "item1","item2","item3","item4","item5"
        };
    }


}
