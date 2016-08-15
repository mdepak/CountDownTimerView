package com.mdepak.countdowntimerview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CountDownTimerView timer;
    Button startBtn;
    Button stopBtn;
    Button pauseBtn;
    Button resumeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = (CountDownTimerView) findViewById(R.id.countDown);

//        timer.setProgressColor(Color.GREEN);
//        timer.setLeftColor(Color.RED);
//        timer.setInsideColor(Color.WHITE);
//        timer.setBgColor(Color.GRAY);

        startBtn = (Button) findViewById(R.id.startBtn);
        stopBtn = (Button) findViewById(R.id.stopBtn);
        pauseBtn = (Button) findViewById(R.id.pauseBtn);
        resumeBtn = (Button) findViewById(R.id.resumeBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.startTimer();
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.stopTimer();
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.pauseTimer();
            }
        });

        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.resumeTimer();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.setTimeIntervalInMilliseconds(60 * 1000);
    }

    public static void main(String args[])
    {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.US);

        Date date = new Date();
        date.setTime(20*1000);
        System.out.println(format.format(date).toString());
    }
}
