package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText,editText2;
    Button button;
    Socket socket;
    Sender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        editText2=findViewById(R.id.editText2);
        button = findViewById(R.id.button);
        socket = null;

        new Thread(){

            @Override
            public void run() {
                String address = "70.12.113.200";
                int port = 8888;
                try {
                    socket = new Socket(address, port);
                } catch (Exception e) {
                    while (true) {
                        Toast.makeText(MainActivity.this, "Retry", Toast.LENGTH_SHORT).show();
                        try {
                            Thread.sleep(1000);
                            socket = new Socket(address, port);

                            break;
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                Log.v("---","Connected");
                try {
                    sender = new Sender(socket);
                }catch(Exception e){

                }
            }
        }.start();
        //소켓 생성


        //클릭할 때마다 sender 쓰레드 생성
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cip = editText.getText().toString();
                String data = editText2.getText().toString();

                textView.append(cip+" : "+data+"\n");

                Msg msg = null;
                msg = new Msg(data,cip);

                sender.setmsg(msg);
                while(data!="q") new Thread(sender).start();

            }
        });

        //sender 는 보내기만 하니까 그대로 써도 됨
        //receiver 는 받아서 리스트뷰/텍스트뷰에 뿌려야함. 쓰레드에서 받아온 값을 메인액티비티의 위젯에 뿌려야한다. handler, RunonUIThread. asynctask 이용
    }

    class Sender extends Thread {
        OutputStream os;
        ObjectOutputStream oos;
        Msg msg;

        public Sender(Socket socket) throws IOException {
            os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);
        }

        public void setmsg(Msg msg) {
            this.msg = msg;
        }

        public void run() {
            if (oos != null)
                try {
                    oos.writeObject(msg);
                } catch (IOException e) {
                }
        }
    }




}
