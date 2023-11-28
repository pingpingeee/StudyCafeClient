package study.customer.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import study.customer.gui.ReservationFragment;

public class ReserveCancelHandler extends Handler {
    ReservationFragment settingFragment;

    public ReserveCancelHandler(ReservationFragment settingFragment) {
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
        } else if (response.equals("<FAILURE>")) {
            System.out.println("없음");
            settingFragment.noneRecords();
        } else if (response.equals("<ERROR>")) {
            System.out.println("에러");
        } else {
            System.out.println("그 외 처리");
        }
    }
}
