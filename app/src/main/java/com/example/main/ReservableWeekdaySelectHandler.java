package com.example.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;


import com.example.mysecondproject.HomeFragment;
import com.example.mysecondproject.need_home_service.CustomDatePickerDialog;
import com.example.mysecondproject.need_home_service.ShowSeatFragment;

public class ReservableWeekdaySelectHandler extends Handler {
    CustomDatePickerDialog customDatePickerDialog;
    ShowSeatFragment showSeatFragment;
    HomeFragment homeFragment;

    public ReservableWeekdaySelectHandler(CustomDatePickerDialog customDatePickerDialog) {
        super();
        this.customDatePickerDialog = customDatePickerDialog;
    }

    public ReservableWeekdaySelectHandler(ShowSeatFragment showSeatFragment) {
        super();
        this.showSeatFragment = showSeatFragment;
    }
    @Override
    public void handleMessage(@NonNull Message message) {
        super.handleMessage(message);
        Bundle bundle = message.getData();
        String response = bundle.getString("response");
        String serviceEnable = bundle.getString("serviceEnable");


        if (response.equals("<SUCCESS>")) {
            System.out.println("성공");
            if (serviceEnable.equals("0")) {
                if (customDatePickerDialog != null) {
                    customDatePickerDialog.updateFail();
                    customDatePickerDialog.setToday();
                } else if (showSeatFragment != null) {
                    if (serviceEnable.equals("0")) {
                        showSeatFragment.noneRecords1();
                        showSeatFragment.updateFail();
                    }
                }
            } else if (serviceEnable.equals("1")){
                if (showSeatFragment != null) {
                    showSeatFragment.showTimePickerDialog(showSeatFragment.getSeatNum());
                }
            }
        } else if (response.equals("<FAILURE>")) {
            System.out.println("N == 0");
        } else if (response.equals("<ERROR>")) {
            System.out.println("에러");
        }else{
            System.out.println("그외처리");
        }
    }

}
