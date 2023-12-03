package study.customer.gui;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mysecondproject.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import customfonts.MyTextView_Poppins_Medium;
import study.customer.handler.ReserveCancelHandler;
import study.customer.main.CustomerManager;
import study.customer.service.ReserveCancelService;

public class ReservationRecord extends Fragment
{
    private ReservationFragment reservationFragment;
    private LinearLayout containerLayout;
    private int fragmentId;
    private int reserveId;
    private int seatId;
    private int anymationReserve;
    private String timeBegin;
    private String timeEnd;
    private String reservationDate;

    private View recordView;
    private TextView numTextView;
    private TextView seatNumTextView;
    private TextView startTimeTextView;
    private TextView endTimeTextView;
    private TextView dayTextView;
    private TextView btnOpen;
    private TextView btnDelete;
    private TextView reserveIdView;


    public ReservationRecord(ReservationFragment _reservationFragment, ArrayList<String> _lines, int _startIndex)
    {
        reservationFragment = _reservationFragment;

        fragmentId = 1 + _startIndex / 5;

        reserveId = Integer.parseInt(_lines.get(_startIndex));

        seatId = Integer.parseInt(_lines.get(_startIndex + 1));

        //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String startTime = _lines.get(_startIndex + 2);
        String[] startTimeParts = startTime.split(":");
        timeBegin = startTimeParts[0] + "시";
        System.out.println(timeBegin);

        String endTime = _lines.get(_startIndex + 3);
        String[] endTimeParts = endTime.split(":");
        timeEnd = endTimeParts[0] + "시";

        String day = _lines.get(_startIndex + 4);
        String[] dayParts = day.split(":");
        reservationDate = dayParts[0] + ":" + dayParts[1];

        //timeBegin = LocalDateTime.parse(_lines.get(_startIndex + 2), dateTimeFormatter);
        //timeEnd = LocalDateTime.parse(_lines.get(_startIndex + 3), dateTimeFormatter);
        //reservationDate = LocalDateTime.parse(_lines.get(_startIndex + 4), dateTimeFormatter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        containerLayout = reservationFragment.getView().findViewById(R.id.recordsContainer);
        recordView = inflater.inflate(R.layout.record_layout_first, containerLayout, false);
        numTextView = recordView.findViewById(R.id.num);
        seatNumTextView = recordView.findViewById(R.id.seatNum);
        startTimeTextView = recordView.findViewById(R.id.startTime);
        endTimeTextView = recordView.findViewById(R.id.endTime);
        dayTextView = recordView.findViewById(R.id.day);
        btnOpen = recordView.findViewById(R.id.btnOk);
        btnDelete = recordView.findViewById(R.id.btnDelete);
        reserveIdView = recordView.findViewById(R.id.reserveId1);


        numTextView.setText(String.valueOf(fragmentId));
        reserveIdView.setText(String.format("%d", reserveId));
        seatNumTextView.setText(String.format("좌석 : %d", seatId));
        startTimeTextView.setText("시작 시간 : " + timeBegin);
        endTimeTextView.setText("종료 시간 : " + timeEnd);
        dayTextView.setText("등록한 시간\n" + reservationDate);

        // 현재 layoutParams == null임.
        ViewGroup.LayoutParams layoutParams = recordView.getLayoutParams();

        layoutParams.height = 205;
        anymationReserve = layoutParams.height;
        recordView.setLayoutParams(layoutParams);
        System.out.println();
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int targetHeight = 340;
                if (recordView.getHeight() == anymationReserve) {
                    btnOpen.setText("닫기");
                    expandView(recordView, targetHeight);
                } else {
                    btnOpen.setText("열기");
                    collapseView(recordView);
                }
            }
        });

        ReserveCancelHandler reserveCancelHandler = new ReserveCancelHandler(this.reservationFragment, this, this.recordView);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View dialogView = getLayoutInflater().inflate(R.layout.dialog_question, null);
                builder.setView(dialogView);

                customfonts.MyTextView_Poppins_Medium dialogTitle = dialogView.findViewById(R.id.dialog_title);
                MyTextView_Poppins_Medium btnNo = dialogView.findViewById(R.id.btnNo);
                MyTextView_Poppins_Medium btnYes = dialogView.findViewById(R.id.btnYes);

                dialogTitle.setText("정말로 삭제하시겠습니까?");
                btnNo.setText("아니요");
                btnYes.setText("네");

                AlertDialog dialog = builder.create();

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ReserveCancelService reserveCancelService = new ReserveCancelService(reserveCancelHandler, Integer.toString(reserveId));
                        CustomerManager.getManager().requestService(reserveCancelService);

                        dialog.dismiss();
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        containerLayout.addView(recordView);
        return recordView;
    }

    public void removeRecordFromView()
    {
        containerLayout.removeView(recordView);
    }

    public void setFragmentId(int _id)
    {
        fragmentId = _id;
        numTextView.setText(String.valueOf(fragmentId));
    }

    private void expandView(final View view, int targetHeight) {
        ValueAnimator slideAnimator = ValueAnimator
                .ofInt(view.getHeight(), targetHeight)
                .setDuration(300);

        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });

        slideAnimator.start();
    }

    private void collapseView(final View view) {
        ValueAnimator slideAnimator = ValueAnimator
                .ofInt(view.getHeight(), anymationReserve)
                .setDuration(300);

        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });

        slideAnimator.start();
    }
}
