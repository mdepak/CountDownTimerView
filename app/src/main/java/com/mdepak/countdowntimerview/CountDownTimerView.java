package com.mdepak.countdowntimerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
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
    private long timeIntervalInMilliseconds = 0;
    private CountDownTimer countDownTimer;
    private long timeLeft = 0;
    private int strokeWidth = 10;
    private int bgColor;
    private int insideColor;
    private int leftColor;
    private int progressColor;
    private int timerTextStyleId;

    public CountDownTimerView(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public CountDownTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    public CountDownTimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CountDownTimerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context, attrs);
    }

    public long getTimeIntervalInMilliseconds() {
        return timeIntervalInMilliseconds;
    }

    public void setTimeIntervalInMilliseconds(long timeIntervalInMilliseconds) {
        this.timeIntervalInMilliseconds = timeIntervalInMilliseconds;
    }

    private void initializeViews(Context context, AttributeSet attributeSet) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.count_down_timer_view, this);
        getViewAttributes(attributeSet);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        circle = (Circle) findViewById(R.id.innerCircle);

        Paint innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setStrokeWidth(strokeWidth);
        innerPaint.setColor(insideColor);

        Paint progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setColor(progressColor);

        Paint pendingPaint = new Paint();
        pendingPaint.setAntiAlias(true);
        pendingPaint.setStyle(Paint.Style.STROKE);
        pendingPaint.setStrokeWidth(strokeWidth);
        pendingPaint.setStrokeCap(Paint.Cap.ROUND);
        pendingPaint.setColor(leftColor);

        circle.setBackgroundColor(bgColor);
        circle.setPendingPaint(pendingPaint);
        circle.setProgressPaint(progressPaint);
        circle.setInnerPaint(innerPaint);
        circle.setStrokeWidth(strokeWidth);
        circle.bringToFront();

        textView = (TextView) findViewById(R.id.sidespinner_view_current_value);
//        textView.setTextAppearance(context, timerTextStyleId);
        TextViewCompat.setTextAppearance(textView, timerTextStyleId);
        textView.bringToFront();
    }

    private CountDownTimer getCountDownTimer(long timeIntervalInMilliseconds) {
        return new CountDownTimer(timeIntervalInMilliseconds, 1000) {
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
    }

    private String getParsedTime(long timeInMilliSeconds) {
        return DateUtils.formatElapsedTime(timeInMilliSeconds);
    }

    /**
     *  Start the count down timer with the initialized settings
     */
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

    /**
     * Resets the CountDownTimerView
     * Once the view is reset it cannot be resumed.
     */
    public void reset() {
        circle.reset();
        timeLeft = 0;
        countDownTimer.cancel();
    }

    /**
     *  Stops the count down timer view
     *  Use pauseTimer() to stop for temporarily, this method stops the timer and to proceed you need to restart the timer again
     */
    public void stopTimer() {
        circle.clearAnimation();
        countDownTimer.cancel();
        timeLeft = 0;
    }

    /**
     *  Pauses the CountDownTimer view and it can be resumed from the point it is stopped.
     *
     */
    public void pauseTimer() {
        countDownTimer.cancel();
        circle.clearAnimation();
    }

    /**
     * Resumes the timer from the point it is stopped.
     *
     */
    public void resumeTimer() {
        countDownTimer = getCountDownTimer(timeLeft);
        countDownTimer.start();
        CircleAngleAnimation animation = new CircleAngleAnimation(circle, 360);
        animation.setDuration(timeLeft);
        animation.setInterpolator(context, android.R.anim.linear_interpolator);
        circle.startAnimation(animation);
    }

    private void getViewAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CountDownTimerView, 0, 0);

            try {
                strokeWidth = typedArray.getInt(R.styleable.CountDownTimerView_strokeWidth, 5);
                timeIntervalInMilliseconds = (long) typedArray.getFloat(R.styleable.CountDownTimerView_timeInMilliSeconds, 0);
                bgColor = ContextCompat.getColor(context, typedArray.getResourceId(R.styleable.CountDownTimerView_backgroundColor, R.color.viewBgColor));
                insideColor = ContextCompat.getColor(context, typedArray.getResourceId(R.styleable.CountDownTimerView_circleColor, R.color.circleColor));
                progressColor = ContextCompat.getColor(context, typedArray.getResourceId(R.styleable.CountDownTimerView_progressColor, R.color.progressColor));
                leftColor = ContextCompat.getColor(context, typedArray.getResourceId(R.styleable.CountDownTimerView_leftColor, R.color.leftColor));
                timerTextStyleId = typedArray.getResourceId(R.styleable.CountDownTimerView_textStyle, R.style.TimerText);
            } finally {
                typedArray.recycle();
            }
        }
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
