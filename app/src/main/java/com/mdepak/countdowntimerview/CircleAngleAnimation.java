package com.mdepak.countdowntimerview;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by MDeepak on 8/7/2016.
 */
public class CircleAngleAnimation extends Animation {

    private Circle circle;

    private float oldAngle;
    private float newAngle;

    public CircleAngleAnimation(Circle circle, int newAngle) {
        this.oldAngle = circle.getAngle();
        this.newAngle = newAngle;
        this.circle = circle;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {

//        float angle = oldAngle + (Math.abs(newAngle - oldAngle) * interpolatedTime);
        float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);
        
        circle.setAngle(angle);
        circle.requestLayout();
    }
}
