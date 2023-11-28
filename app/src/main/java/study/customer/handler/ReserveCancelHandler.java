package study.customer.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import study.customer.gui.SettingFragment;

public class ReserveCancelHandler extends Handler {
    SettingFragment settingFragment;

    public ReserveCancelHandler(SettingFragment settingFragment) {
        super();
        this.settingFragment = settingFragment;
    }

    @Override
    public void handleMessage(@NonNull Message message) {
        super.handleMessage(message);
        Bundle bundle = message.getData();
        String response = bundle.getString("response");


        if (response.equals("<SUCCESS>")) {
            System.out.println("유저 개인내역 통신성공");
        } else if (response.equals("<FAILURE>")) {
            System.out.println("유저 개인내역 없음");
            settingFragment.noneRecords();
        } else if (response.equals("<ERROR>")) {
            System.out.println("에러");
        } else {
            System.out.println("그 외 처리");
        }
    }
}
