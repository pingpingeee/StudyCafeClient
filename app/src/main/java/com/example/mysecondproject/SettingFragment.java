package com.example.mysecondproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.main.ReserveSelectHandler;
import com.example.service.ReserveSelectService;

public class SettingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        ReserveSelectHandler reserveSelectHandler;
        reserveSelectHandler = new ReserveSelectHandler(this);
        ReserveSelectService reserveSelectService = new ReserveSelectService(reserveSelectHandler, Integer.toString(CustomerManager.getManager().getUuid()));
        reserveSelectService.bindNetworkModule(IntroActivity.networkModule);
        IntroActivity.networkThread.requestService(reserveSelectService);

        return view;
    }
}