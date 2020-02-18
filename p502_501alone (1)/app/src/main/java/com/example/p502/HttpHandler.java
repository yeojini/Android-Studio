package com.example.p502;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandler{
    public static String getString(String urlstr){
        String result = null;
        URL url = null;
        HttpURLConnection hucon = null;
        InputStream is = null;
        try {
            url = new URL(urlstr);
            hucon = (HttpURLConnection) url.openConnection();
            hucon.setConnectTimeout(2000);
            hucon.setRequestMethod("GET");
            is = new BufferedInputStream(hucon.getInputStream());
            Log.d("---","is"+is);
            result = convertstr(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("---","getString동작");
        Log.d("---","getString동작"+result);
        return result;
    }
    public static String convertstr(InputStream is) {
        String result = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(is));
            String temp = "";
            while ((temp = br.readLine())!=null){
                sb.append(temp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        result = sb.toString();
        return result;
    }

}
//기본적인 프로토콜 url정보를 주면 해당서버에서 데이터를 받아오고
//통신은 InputStream을 활용할 것이다. ,,,,,
