package com.example.mysecondproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.main.ReserveSelectHandler;
import com.example.service.ReserveSelectService;

import java.util.ArrayList;

public class SettingFragment extends Fragment {
    private ArrayList<String> lines = new ArrayList<>();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);


        ReserveSelectHandler reserveSelectHandler;
        reserveSelectHandler = new ReserveSelectHandler(this);
        ReserveSelectService reserveSelectService =
                new ReserveSelectService(reserveSelectHandler);
        reserveSelectService.bindNetworkModule(IntroActivity.networkModule);
        IntroActivity.networkThread.requestService(reserveSelectService);

        return view;
    }

    public void noneRecords() {
        TextView text = view.findViewById(R.id.text);
        text.setText("등록된 예약내역이 없습니다.");
    }
    public void updateRecords(ArrayList<String> lines) {
        lines = this.lines;
        LinearLayout containerLayout = getView().findViewById(R.id.recordsContainer);

        for (int i = 0; i < lines.size(); i += 5) {
            View recordView = getLayoutInflater().inflate(R.layout.record_layout, containerLayout, false);
            TextView numTextView = recordView.findViewById(R.id.num);
            TextView seatNumTextView = recordView.findViewById(R.id.seatNum);
            TextView startTimeTextView = recordView.findViewById(R.id.startTime);
            TextView endTimeTextView = recordView.findViewById(R.id.endTime);
            TextView dayTextView = recordView.findViewById(R.id.day);

            numTextView.setText(lines.get(i));
            seatNumTextView.setText("좌석 : " + lines.get(i + 1));

            String startTime = lines.get(i + 2);
            String[] startTimeParts = startTime.split(":");
            String formattedStartTime = startTimeParts[0] + "시";
            startTimeTextView.setText("시작 시간 : " + formattedStartTime);


            String endTime = lines.get(i + 3);
            String[] endTimeParts = endTime.split(":");
            String formattedEndTime = endTimeParts[0] + "시";
            endTimeTextView.setText("종료 시간 : " + formattedEndTime);


            String day = lines.get(i + 4);
            String[] dayParts = day.split(":");
            String formattedDay = dayParts[0] + ":" + dayParts[1];
            dayTextView.setText("예약 시간 : " + formattedDay);

            containerLayout.addView(recordView);
        }
    }
    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }
}