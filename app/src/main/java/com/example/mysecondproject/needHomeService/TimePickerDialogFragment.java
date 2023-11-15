package com.example.mysecondproject.needHomeService;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.mysecondproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import customfonts.MyTextView_Poppins_Medium;

public class TimePickerDialogFragment extends DialogFragment {

    private String seatNumber;
    private String[] reservedTimes;
    private String selectedTime;

    public TimePickerDialogFragment(String seatNumber, String[] reservedTimes) {
        this.seatNumber = seatNumber;
        this.reservedTimes = reservedTimes;
    }

    @NonNull
    @Override
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
                if (selectedTime != null) {
                    checkReservation(selectedTime);
                    dismiss();
                }
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

    private void checkReservation(String selectedTime) {
        // selectedTime에 이미 예약되어 있는지 확인로직필요
        // 테스트예약확인
        boolean isReserved = false;

        // Ensure reservedTimes is not null before iterating
        if (reservedTimes != null) {
            for (String reservedTime : reservedTimes) {
                if (reservedTime.matches(".*" + Pattern.quote(selectedTime) + ".*")) {
                    isReserved = true;
                    break;
                }
            }
        }

        if (isReserved) {
            // 이미 예약됐을 때
            System.out.println("실패");
            showReservationErrorDialog();
        } else {
            // 예약성공
            System.out.println("성공");
            dismiss();
            showConfirmationDialog(selectedTime);
        }
    }


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

    private void showConfirmationDialog(String selectedTime) {
        String confirmationMessage = seatNumber + "번 좌석, " + selectedTime + "에 예약이 완료되었습니다.";
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
