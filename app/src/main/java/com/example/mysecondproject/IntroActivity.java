package com.example.mysecondproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.main.NetworkThread;
import com.example.network.INetworkModule;
import com.example.network.NetworkModule;

import java.net.Socket;

public class IntroActivity extends AppCompatActivity {
    public static NetworkThread networkThread;

    public static INetworkModule networkModule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            networkThread = new NetworkThread();
            networkThread.start();
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },10);  //1000으로 변경
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}