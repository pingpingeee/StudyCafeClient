package com.example.mysecondproject.needHomeService;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import com.example.mysecondproject.R;

import customfonts.MyTextView_Poppins_Medium;

public class CustomDatePickerDialog extends Dialog {

    private DatePicker datePicker;
    private MyTextView_Poppins_Medium buttonCancel;
    private MyTextView_Poppins_Medium buttonOk;

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public CustomDatePickerDialog(@NonNull Context context, DatePickerDialog.OnDateSetListener listener) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_date_picker_dialog);

        datePicker = findViewById(R.id.datePicker);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonOk = findViewById(R.id.buttonOk);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                listener.onDateSet(null, year, month, day);
                dismiss();
            }
        });
    }
}
