package com.example.chatclient2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

import tcpip2.Msg;

public class MainActivity extends AppCompatActivity {

    TextView textView,textView2,textView3;
    EditText editText, editText2,editText3,editText4;
    Socket socket;
    Msg msg;
    Sender sender;
    Receiver receiver;
    ConnectThread connectThread;
    ArrayList<String> list;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeUi();
    }

    private void makeUi(){
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
    }

    class ConnectThread extends Thread{
        String ip;
        int port;

        public ConnectThread(){ }

        public ConnectThread(String ip, int port){
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
                try {
                    list = new ArrayList<>();
                    socket = new Socket(ip, port);
                    textView2.setText("Connected");
                    Log.d("---", "Connected..");
                    sender = new Sender(socket);
                    Log.d("---", "Sender..");
                    receiver = new Receiver();
                    receiver.execute(socket);
                    Log.d("---", "Receiver..");
                } catch (Exception e) {
                    e.printStackTrace();
                    while (true) {
                        Log.d("---", "Retry..");
                        textView2.setText("Retry...");
                        try {
                            Thread.sleep(1000);
                            socket = new Socket(ip, port);

                            break;
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }

        }

    }

    class Sender implements Runnable {
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

    class Receiver extends AsyncTask<Socket,Msg,Msg>{

        InputStream is;
        ObjectInputStream ois;
        Msg msg = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Msg doInBackground(Socket... socket) {
            while(isCancelled()==false) {
                try {
                    is = socket[0].getInputStream();
                    ois = new ObjectInputStream(is);
                } catch (Exception e) { }

                while(ois!=null) {
                    try {
                        msg = (Msg) ois.readObject();
                        publishProgress(msg);
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
            return msg;
        }

        @Override
        protected void onProgressUpdate(Msg... msg) {
            textView.append(msg[0].getId()+":"+msg[0].getMsg()+"\n");
        }

        @Override
        protected void onPostExecute(Msg msg) {
            try {
                if(ois!=null) ois.close();
                if(socket!=null) socket.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            textView3.setText(msg.getIp());
        }


    }

    public void ckbt(View v){
        if(v.getId() == R.id.button){
            String ip = editText.getText().toString();
            int port = Integer.parseInt(editText2.getText().toString());
            //사용자가 입력한 값으로부터 ip 와 port 를 받아온다
            //어싱크태스크 - 스레드가 끝난 후 값을 받아올 수 있다는게 장점. post 함수 활용해 쓰레드가 끝난 후 뭔가 액션을 해야할 때 좋음
            //sender는 보내고 끝이니까 어싱크태스크 굳이 안써도 된다.
            connectThread = new ConnectThread(ip,port);
            connectThread.start();

        }else if(v.getId()==R.id.button2){
            String ip = editText3.getText().toString();
            String txt = editText4.getText().toString();
            Log.d("---",txt);
            Log.d("---",ip);
            Msg msg =null;
            if(ip==null||ip.equals("")){
                //msg = new Msg("Yeojin",txt,null);
            }else{
                msg = new Msg("Yeojin",txt,ip,list);//msg 를 sender 에 담아 스레드를 동작시킬 것이다
            }
            sender.setmsg(msg);
            textView.append("Yeojin : "+txt+"\n");
            new Thread(sender).start();
        }else if(v.getId()==R.id.button3){

            if(socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                textView2.setText("Disconnected");
            }
        }
    }
}





