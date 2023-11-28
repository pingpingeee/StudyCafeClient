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
import androidx.fragment.app.FragmentTransaction;

import customfonts.MyTextView_Poppins_Medium;
import study.customer.handler.ReserveCancelHandler;
import study.customer.handler.ReserveSelectHandler;

import com.example.mysecondproject.R;

import study.customer.service.ReserveCancelService;
import study.customer.service.ReserveSelectService;

import java.io.IOError;
import java.util.ArrayList;

public class ReservationFragment extends Fragment {
    ReserveCancelHandler reserveCancelHandler;
    private String reserveId;
    private ArrayList<String> lines = new ArrayList<>();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reservation, container, false);


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
        int c = 1;
        LinearLayout containerLayout = getView().findViewById(R.id.recordsContainer);

            for (int i = 0; i < lines.size(); i += 5) {
                View recordView = getLayoutInflater().inflate(R.layout.record_layout_first, containerLayout, false);
                TextView numTextView = recordView.findViewById(R.id.num);
                TextView seatNumTextView = recordView.findViewById(R.id.seatNum);
                TextView startTimeTextView = recordView.findViewById(R.id.startTime);
                TextView endTimeTextView = recordView.findViewById(R.id.endTime);
                TextView dayTextView = recordView.findViewById(R.id.day);
                TextView btnOpen = recordView.findViewById(R.id.btnOk);
                TextView btnDelete = recordView.findViewById(R.id.btnDelete);
                TextView reserveId1 = recordView.findViewById(R.id.reserveId1);


                numTextView.setText(String.valueOf(c));
                reserveId1.setText(String.valueOf(lines.get(i)));
                //numTextView.setText(String.valueOf(lines.get(i)));


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
                dayTextView.setText("등록한 시간\n" + formattedDay);


                ViewGroup.LayoutParams layoutParams = recordView.getLayoutParams();
                layoutParams.height = 263;
                recordView.setLayoutParams(layoutParams);
                btnOpen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int targetHeight = 508;
                        if (recordView.getHeight() == 263) {
                            btnOpen.setText("닫기");
                            expandView(recordView, targetHeight);
                        } else {
                            btnOpen.setText("열기");
                            collapseView(recordView);
                        }
                    }
                });

                TextView text = view.findViewById(R.id.text);
                text.setText("");
                reserveCancelHandler = new ReserveCancelHandler(this, containerLayout, recordView);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        View dialogView = getLayoutInflater().inflate(R.layout.question_mark_dialog, null);
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
                                reserveId = String.valueOf(reserveId1.getText());

                                ReserveCancelService reserveCancelService =
                                        new ReserveCancelService(reserveCancelHandler, reserveId);
                                reserveCancelService.bindNetworkModule(IntroActivity.networkModule);
                                IntroActivity.networkThread.requestService(reserveCancelService);

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
                c++;
            }

    }
    public void updateFail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View dialogView = getLayoutInflater().inflate(R.layout.fail_dialog, null);
        builder.setView(dialogView);

        customfonts.MyTextView_Poppins_Medium dialogTitle = dialogView.findViewById(R.id.dialog_title);
        MyTextView_Poppins_Medium confirmButton = dialogView.findViewById(R.id.confirm_button);

        dialogTitle.setText("예약시간이 지나 취소할 수 없습니다.");
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
                .ofInt(view.getHeight(), 263)
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
    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }
}