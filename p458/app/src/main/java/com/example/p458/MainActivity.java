package com.example.p458;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        webView = findViewById(R.id.webView); //webView 객체 참조

        webView.setWebViewClient(new WebViewClient()); //엔진 장착
        //WebViewClient 를 상속한 객체를 만들어 WebView 에 설정
        webView.addJavascriptInterface(new JS(),"js");

        WebSettings webSettings = webView.getSettings();
        //webView의 getSettings() 메서드 사용해 WebSettings 객체 참조
        webSettings.setJavaScriptEnabled(true);
        //자바스크립트가 동작할 수 있는 환경
    }
    public void ckbt(View v){
        if(v.getId()==R.id.button){
            webView.loadUrl("http://m.naver.com");
            //웹페이지를 로딩해 화면에 보여줌
        }else if(v.getId()==R.id.button2){
            webView.loadUrl("http://70.12.113.200:8080/webview/index.html");

        }else if (v.getId()==R.id.button3){
            webView.loadUrl("javascript:s('zzzzz')");
        }
    }

    private class JS {

        @android.webkit.JavascriptInterface
        public void webclick(String str){
            textView.setText(str);
        }

    }
}
