package com.example.mysecondproject.need_home_service;

import android.app.Dialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.main.ReserveHandler;
import com.example.mysecondproject.HomeFragment;
import com.example.mysecondproject.IntroActivity;
import com.example.mysecondproject.R;
import com.example.service.ReserveService;

import java.util.ArrayList;
import java.util.List;

import customfonts.MyTextView_Poppins_Medium;

public class TimePickerDialogFragment extends DialogFragment {

    private String seatNum;
    private String uuId;
    private String startTime;
    private String endTime;
    private String day;
    private String[] reservedTimes;

    private String selectedTime;
    private TimePickerDialogFragment timePickerDialogFragment;
    private View view;
    private HomeFragment homeFragment;


    public TimePickerDialogFragment(HomeFragment homeFragment, String seatNum, String startTime, String endTime) {
        this.homeFragment = homeFragment;
        this.seatNum = seatNum;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @NonNull
    @Override
    //나중에 서버랑 이용시간 연동해야함?
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_time_picker, null);

        ListView timeListView = view.findViewById(R.id.timeListView);

        //몇시부터 몇시까지 할지 설정
        List<String> timeList = new ArrayList<>();
        for (int hour = 8; hour <= 22; hour++) {
            timeList.add(hour + "시 ~ " + (hour+1)+"시");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_single_choice, timeList);
        timeListView.setAdapter(adapter);
        timeListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = adapter.getItem(position);

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.TimePickerDialogTheme);
        builder.setTitle("시간 선택")
                .setView(view);
        View btnOk = view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ReserveHandler seatTimeHandler;

                    String[] h = selectedTime.split(" ~ ");
                    String startH = h[0].replace("시", "").trim();
                    String endH = h[1].replace("시", "").trim();

                    String day = homeFragment.getSelectedDate();
                    startTime = day + startH + ":00:00";
                    endTime = day + endH + ":00:00";

                    seatTimeHandler = new ReserveHandler(timePickerDialogFragment, v);
                    ReserveService seatTimeService = new ReserveService(seatTimeHandler, seatNum, startTime, endTime);
                    seatTimeService.bindNetworkModule(IntroActivity.networkModule);
                    IntroActivity.networkThread.requestService(seatTimeService);
                    dismiss();
            }
        });

        View btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }




    //예약실패 시 뜨는 팝업창
    private void showReservationErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        View dialogView = getLayoutInflater().inflate(R.layout.fail_dialog, null);
        builder.setView(dialogView);

        customfonts.MyTextView_Poppins_Medium dialogTitle = dialogView.findViewById(R.id.dialog_title);
        MyTextView_Poppins_Medium confirmButton = dialogView.findViewById(R.id.confirm_button);

        dialogTitle.setText("이미 예약된 좌석입니다.");
        confirmButton.setText("확인");

        AlertDialog dialog = builder.create();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //예약성공 시 뜨는 팝업창
    private void showConfirmationDialog(String selectedTime) {
        String confirmationMessage = seatNum + "번 좌석, " + selectedTime + "에 예약이 완료되었습니다.";
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        View dialogView = getLayoutInflater().inflate(R.layout.success_dialog, null);
        builder.setView(dialogView);

        MyTextView_Poppins_Medium dialogTitle = dialogView.findViewById(R.id.dialog_title);
        MyTextView_Poppins_Medium confirmButton = dialogView.findViewById(R.id.confirm_button);

        dialogTitle.setText(confirmationMessage);
        dialogTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        confirmButton.setText("확인");


        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();


    }


}
