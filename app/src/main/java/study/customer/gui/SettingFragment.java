package study.customer.gui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import study.customer.handler.ReserveSelectHandler;

import com.example.mysecondproject.R;

import study.customer.service.ReserveSelectService;

import java.util.ArrayList;

public class SettingFragment extends Fragment {
    private ArrayList<String> lines = new ArrayList<>();
    private View view;
    private String selectedRecordNum;
    private String selectedSeatNum;
    private String selectedReservationDate;

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
        int c = 1;
        LinearLayout containerLayout = getView().findViewById(R.id.recordsContainer);

        for (int i = 0; i < lines.size(); i += 5) {
            View recordView = getLayoutInflater().inflate(R.layout.record_layout_first, containerLayout, false);
            TextView numTextView = recordView.findViewById(R.id.num);
            TextView seatNumTextView = recordView.findViewById(R.id.seatNum);
            TextView startTimeTextView = recordView.findViewById(R.id.startTime);
            TextView endTimeTextView = recordView.findViewById(R.id.endTime);
            TextView dayTextView = recordView.findViewById(R.id.day);
            TextView btn = recordView.findViewById(R.id.btnOk);

            //numTextView.setText(lines.get(i));
            numTextView.setText(String.valueOf(c));
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
            recordView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int targetHeight = 508;
                    if (recordView.getHeight() == 263) {
                        btn.setText("닫기");
                        expandView(recordView, targetHeight);
                    } else {
                        btn.setText("열기");
                        collapseView(recordView);
                    }
                }
            });

            containerLayout.addView(recordView);
            c++;
        }

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