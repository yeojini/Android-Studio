package com.example.p427;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<item> lists = new ArrayList<>();
    LinearLayout item_inner;
    ItemAdapter itemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        item_inner = findViewById(R.id.item_inner);
        getData();
        itemAdapter = new ItemAdapter(lists);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you deleted this Item");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     lists.remove(position);
                     itemAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.show();
            }
        });

    }

    private void getData() {
        //객체 생성 후 리스트에 삽입
        lists.add(new item(R.drawable.a,"이말숙","010-0000-0000"));
        lists.add(new item(R.drawable.b,"고말숙","010-0001-1000"));
        lists.add(new item(R.drawable.c,"최말숙","010-0002-2000"));
        lists.add(new item(R.drawable.d,"장말숙","010-0003-3000"));
        lists.add(new item(R.drawable.e,"신말숙","010-0004-4000"));
        lists.add(new item(R.drawable.a,"허말숙","010-0000-0002"));
        lists.add(new item(R.drawable.b,"김말숙","010-1001-1000"));
        lists.add(new item(R.drawable.c,"박말숙","010-1002-2000"));
        lists.add(new item(R.drawable.d,"황말숙","010-1003-3000"));
        lists.add(new item(R.drawable.e,"정말숙","010-1004-4000"));
    }

    class ItemAdapter extends BaseAdapter{
        ArrayList<item> lists;

        public ItemAdapter(){

        }
        public ItemAdapter(ArrayList<item> lists){
            this.lists = lists;
        }


        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_layout,item_inner,true);

            ImageView img = convertView.findViewById(R.id.imageView);
            TextView name = convertView.findViewById(R.id.textView);
            TextView phone = convertView.findViewById(R.id.textView2);

            item tem = lists.get(position);
            img.setImageResource(tem.getImg());
            name.setText(tem.getName());
            phone.setText(tem.getPhone());

            return convertView;

            /*
            ListView 를 스크롤하게 되면 새로운 데이터를 표시하기 위해 매번 새로운 View 를 생성
            위 코드는 새로운 View 를 생성하는데 시스템의 리소스를 많이 소비
            */




        }
    }
}
