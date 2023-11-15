package com.example.mysecondproject;

import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.mysecondproject.needHomeService.CustomDatePickerDialog;
import com.example.mysecondproject.needHomeService.TimePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import customfonts.MyTextView_Poppins_Medium;

public class HomeFragment extends Fragment {
    private TextView textViewDate;
    private Map<String, String[]> seatReservedTimes = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setAppLocale("ko");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textViewDate = view.findViewById(R.id.textViewDate);

        // 오늘 날짜를 설정
        setTodayDate();
        setTodayDate();

        //날짜선택버튼
        MyTextView_Poppins_Medium btnDateSelect = view.findViewById(R.id.buttonLogin);
        btnDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        setSeatButtonClickListeners(view);

        return view;
    }

    //지역불러오고 그에 맞는 언어설정해주기(캘린더)
    private void setAppLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }


    private void setTodayDate() {
        // 현재 날짜를 가져오기
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = "선택된 날짜 : " + dateFormat.format(calendar.getTime());

        // 가져온 날짜를 TextView에 설정(이건 사용자용)
        // TODO:서버로 보내는것도 추가해야함
        textViewDate.setText(todayDate);
    }


    // 날짜선택 다이얼로그
    private void showDatePicker() {
        // 현재 날짜 가져오기
        final Calendar calendar = Calendar.getInstance();
        //현재날짜에 10일 추가하고 90라인을 따라감
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        long maxDate = calendar.getTimeInMillis();

        // 커스텀 DatePickerDialog 생성
        CustomDatePickerDialog customDatePickerDialog = new CustomDatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                TextView textViewDate = getView().findViewById(R.id.textViewDate);
                textViewDate.setText("선택된 날짜 : " + selectedDate);
            }
        });
        customDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        //최대날짜 설정
        customDatePickerDialog.getDatePicker().setMaxDate(maxDate);
        customDatePickerDialog.show();
    }



    //좌석등록
    private void setSeatButtonClickListeners(View view) {
        int[] seatButtonIds = {
                R.id.seat_1, R.id.seat_2, R.id.seat_3, R.id.seat_4, R.id.seat_5,
                R.id.seat_6, R.id.seat_7, R.id.seat_8, R.id.seat_9, R.id.seat_10,
                R.id.seat_11, R.id.seat_12, R.id.seat_13, R.id.seat_14, R.id.seat_15,
                R.id.seat_16, R.id.seat_17, R.id.seat_18, R.id.seat_19, R.id.seat_20,
                R.id.seat_21, R.id.seat_22, R.id.seat_23, R.id.seat_24, R.id.seat_25,
                R.id.seat_26, R.id.seat_27, R.id.seat_28, R.id.seat_29, R.id.seat_30,
                R.id.seat_31, R.id.seat_32, R.id.seat_33, R.id.seat_34, R.id.seat_35
        };

        for (int seatButtonId : seatButtonIds) {
            View seatButton = view.findViewById(seatButtonId);
            seatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 좌석 버튼이 클릭되었을 때 해당 번호를 가져와서 팝업창을 띄움
                    String seatNumber = ((TextView) v).getText().toString();

                    //TODO:예약된 시간만 배열로 선언해놓음 좌석별로 분리해줘야함
                    //TODO:이런식으로 사용자에게 보이는 문자열을 반환해줘야함..
                    String[] reservedTimes = {"8시 ~ 9시", "9시 ~ 10시", "10시 ~ 11시"};

                    // 팝업창 띄우기
                    showTimePickerDialog(seatNumber, reservedTimes);
                }
            });
        }
    }

    //TimePickerDialogFragment클래스에서 처리
    private void showTimePickerDialog(String seatNumber, String[] reservedTimes) {
        TimePickerDialogFragment dialogFragment = new TimePickerDialogFragment(seatNumber, reservedTimes);
        dialogFragment.show(getParentFragmentManager(), "time_picker");
    }


}
