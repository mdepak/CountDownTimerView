package com.mdepak.countdowntimerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by MDeepak on 8/7/2016.
 */
public class CountDownTimerView extends LinearLayout {

    private Context context;
    private Circle circle;
    private TextView textView;
    long timeIntervalInMilliseconds = 0;
    private CountDownTimer countDownTimer;
    public long timeLeft = 0;
    int strokeWidth = 10;
    private static final String LOG_TAG = "CountDownTimerView";
    int bgColor;
    int insideColor;
    int leftColor;
    int progressColor;

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
        this.context = context;
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
        innerPaint.setStrokeWidth(strokeWidth);
        innerPaint.setColor(Color.BLACK);

        Paint progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setColor(Color.LTGRAY);

        Paint pendingPaint = new Paint();
        pendingPaint.setAntiAlias(true);
        pendingPaint.setStyle(Paint.Style.STROKE);
        pendingPaint.setStrokeWidth(strokeWidth);
        pendingPaint.setStrokeCap(Paint.Cap.ROUND);
        pendingPaint.setColor(Color.BLUE);

        if (insideColor != 0) {
            innerPaint.setColor(insideColor);
        }
        if (progressColor != 0) {
            progressPaint.setColor(progressColor);
        }
        if (leftColor != 0) {
            pendingPaint.setColor(leftColor);
        }
        if (bgColor != 0) {
            circle.setBackgroundColor(bgColor);
        }

        circle.setPendingPaint(pendingPaint);
        circle.setProgressPaint(progressPaint);
        circle.setInnerPaint(innerPaint);
        circle.setStrokeWidth(strokeWidth);
        circle.bringToFront();

        textView = (TextView) findViewById(R.id.sidespinner_view_current_value);
        textView.bringToFront();
    }

    private CountDownTimer getCountDownTimer(long timeIntervalInMilliseconds) {
        CountDownTimer timer = new CountDownTimer(timeIntervalInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                textView.setText(String.valueOf(getParsedTime(millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {
                textView.setText("00:00");
                circle.reset();
            }
        };
        return timer;
    }

    private String getParsedTime(long timeInMilliSeconds) {
        return DateUtils.formatElapsedTime(timeInMilliSeconds);
    }

    public void startTimer() {
        circle.setStrokeWidth(strokeWidth);
        countDownTimer = getCountDownTimer(timeIntervalInMilliseconds);
        reset();
        countDownTimer.start();
        CircleAngleAnimation animation = new CircleAngleAnimation(circle, 360);
        animation.setInterpolator(context, android.R.anim.linear_interpolator);
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
        animation.setInterpolator(context, android.R.anim.linear_interpolator);
        circle.startAnimation(animation);
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getInsideColor() {
        return insideColor;
    }

    public void setInsideColor(int insideColor) {
        this.insideColor = insideColor;
    }

    public int getLeftColor() {
        return leftColor;
    }

    public void setLeftColor(int leftColor) {
        this.leftColor = leftColor;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
}
