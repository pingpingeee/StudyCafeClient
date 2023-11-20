package com.example.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.mysecondproject.SettingFragment;


public class ReserveSelectHandler extends Handler {
    View view;

    SettingFragment settingFragment;

    public ReserveSelectHandler(SettingFragment settingFragment) {
        super();
        this.settingFragment = settingFragment;
    }
    @Override
    public void handleMessage(@NonNull Message message) {
        super.handleMessage(message);
        Bundle bundle = message.getData();
        String response = bundle.getString("response");

        if (response.equals("<SUCCESS>")) {
            System.out.println("통신성공");
        } else if (response.equals("<FAILURE")) {
            System.out.println("N == 0");
        } else if (response.equals("<ERROR>")) {
            System.out.println("에러");
        } else {
            System.out.println("test"+response);
        }
    }

}
