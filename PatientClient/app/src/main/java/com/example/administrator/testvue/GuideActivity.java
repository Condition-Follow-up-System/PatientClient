package com.example.administrator.testvue;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class GuideActivity extends AppCompatActivity {

    private Button bt;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            bt.setText("跳过  "+msg.what+"s");
            if(msg.what==1){
                Intent intent=new Intent(GuideActivity.this,MainActivity.class);
                //启动
                startActivity(intent);

                finish();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        bt = findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                 for(int i=3;i>0;i--){
                     try {
                         Thread.sleep(1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                     Message message = new Message();
                     message.what = i;
                     mHandler.sendMessage(message);
                 }
            }
        }).start();
    }








}
