package com.mdepak.countdowntimerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by MDeepak on 8/7/2016.
 */
public class CountDownTimerView extends LinearLayout {

    private Circle circle;
    private TextView textView;
    long timeIntervalInMilliseconds = 0;
    private CountDownTimer countDownTimer;
    public long timeLeft = 0;
    final int STROKE_WIDTH = 15;
    private static final String LOG_TAG = "CountDownTimerView";

    public CountDownTimerView(Context context) {
        super(context);
        initializeViews(context);
    }

    public CountDownTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public CountDownTimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CountDownTimerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context);
    }

    public long getTimeIntervalInMilliseconds() {
        return timeIntervalInMilliseconds;
    }

    public void setTimeIntervalInMilliseconds(long timeIntervalInMilliseconds) {
        this.timeIntervalInMilliseconds = timeIntervalInMilliseconds;
        onFinishInflate();
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.count_down_timer_view, this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        circle = (Circle) findViewById(R.id.innerCircle);

        Paint innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setStrokeWidth(STROKE_WIDTH);
        innerPaint.setColor(Color.BLACK);

        Paint progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(STROKE_WIDTH);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setColor(Color.LTGRAY);

        Paint pendingPaint = new Paint();
        pendingPaint.setAntiAlias(true);
        pendingPaint.setStyle(Paint.Style.STROKE);
        pendingPaint.setStrokeWidth(STROKE_WIDTH);
        pendingPaint.setStrokeCap(Paint.Cap.ROUND);
        pendingPaint.setColor(Color.BLUE);

        circle.setPendingPaint(pendingPaint);
        circle.setProgressPaint(progressPaint);
        circle.setInnerPaint(innerPaint);
        circle.setStrokeWidth(STROKE_WIDTH);
        circle.bringToFront();

        textView = (TextView) findViewById(R.id.sidespinner_view_current_value);
//        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font name.ttf");
        textView.bringToFront();
    }

    private CountDownTimer getCountDownTimer(long timeIntervalInMilliseconds) {
        CountDownTimer timer = new CountDownTimer(timeIntervalInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                Log.d(LOG_TAG, "CountDownTimer - " + millisUntilFinished);
                textView.setText(String.valueOf(getParsedTime(millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {
                textView.setText("00:00");
                circle.reset();
                Log.d(LOG_TAG, "Completed timer");
            }
        };
        return timer;
    }

    private String getParsedTime(long timeInMilliSeconds)
    {
        return DateUtils.formatElapsedTime(timeInMilliSeconds);
    }

    public void startTimer() {
        circle.setStrokeWidth(STROKE_WIDTH);
        countDownTimer = getCountDownTimer(timeIntervalInMilliseconds);
        reset();
        countDownTimer.start();
        CircleAngleAnimation animation = new CircleAngleAnimation(circle, 360);
        animation.setDuration(timeIntervalInMilliseconds);
        circle.startAnimation(animation);
    }

    public void reset() {
        circle.reset();
        timeLeft = 0;
        countDownTimer.cancel();
    }

    public void stopTimer() {
        circle.clearAnimation();
        countDownTimer.cancel();
        timeLeft = 0;
    }

    public void pauseTimer() {
        countDownTimer.cancel();
        circle.clearAnimation();
    }

    public void resumeTimer() {
        countDownTimer = getCountDownTimer(timeLeft);
        countDownTimer.start();
        CircleAngleAnimation animation = new CircleAngleAnimation(circle, 360);
        animation.setDuration(timeLeft);
        circle.startAnimation(animation);
    }
}
