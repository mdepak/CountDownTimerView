package com.mdepak.countdowntimerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by MDeepak on 8/7/2016.
 */
public class Circle extends View {

    private static final int START_ANGLE_POINT = -90;
    private Paint innerPaint;
    private Paint progressPaint;
    private Paint pendingPaint;
    int strokeWidth = 5;
    private float angle;

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);
        angle = 0;
    }

    public void setInnerPaint(Paint innerPaint) {
        this.innerPaint = innerPaint;
    }

    public Paint getInnerPaint() {
        return innerPaint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int smallestSide = getWidth() > getHeight() ? getHeight() : getWidth();
        int radius = smallestSide / 2;

        RectF bounds = new RectF(getStrokeWidth(), getStrokeWidth(), smallestSide - getStrokeWidth(), smallestSide - getStrokeWidth());
        canvas.drawArc(bounds, angle + getStrokeWidth(), 360, true, pendingPaint);
        canvas.drawArc(bounds, START_ANGLE_POINT, angle, true, progressPaint);
        canvas.drawCircle(smallestSide / 2, smallestSide / 2, radius - (getStrokeWidth()), innerPaint);
    }

    public float getAngle() {
        return angle;
    }

    public void reset() {
        angle = 0;
//        strokeWidth = 0;
        this.invalidate();
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Paint getProgressPaint() {
        return progressPaint;
    }

    public void setProgressPaint(Paint progressPaint) {
        this.progressPaint = progressPaint;
    }

    public Paint getPendingPaint() {
        return pendingPaint;
    }

    public void setPendingPaint(Paint pendingPaint) {
        this.pendingPaint = pendingPaint;
    }
}