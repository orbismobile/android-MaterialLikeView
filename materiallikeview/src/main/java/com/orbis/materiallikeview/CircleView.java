package com.orbis.materiallikeview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;

/**
 * Created by Carlos Leonardo Camilo Vargas Huamán on 7/4/17.
 */

public class CircleView extends View {

    //prte float currentRad
    private Bitmap tempBitmap;
    private Canvas tempCanvas;

    private final Paint maskPaint = new Paint();
    private final Paint circlePaint = new Paint();

    private float outerCircleRadiusProgress = 0f;
    private float innerCircleRadiusProgress = 0f;

    private int maxCircleRadiusSize;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint.setStyle(Paint.Style.FILL);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void setColorCirclePaint(int color) {
        circlePaint.setColor(color);
    }

    /**
     * 1
     * This method is exectuted first before onDraw method
     *
     * @param w    The width size in pixels of this view
     * @param h    The width size in pixels of this view
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maxCircleRadiusSize = w / 2;
        Log.e("x-onSizeChanged", "x -onSizeChanged " + w);
        tempBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        tempCanvas = new Canvas(tempBitmap);
    }

    /**
     * 2
     *  This method is exectuted after onSizeChanged method
     * @param canvas
     */

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        tempCanvas.drawCircle(getWidth() / 2, getHeight() / 2, outerCircleRadiusProgress * maxCircleRadiusSize, circlePaint);
        tempCanvas.drawCircle(getWidth() / 2, getHeight() / 2, innerCircleRadiusProgress * maxCircleRadiusSize, maskPaint);
        canvas.drawBitmap(tempBitmap, 0, 0, null);
    }

    private float getInnerCircleRadiusProgress() {
        return innerCircleRadiusProgress;
    }

    public void setInnerCircleRadiusProgress(float innerCircleRadiusProgress) {
        this.innerCircleRadiusProgress = innerCircleRadiusProgress;
        // This method redraws the view, onDraw method is executed
        postInvalidate();
    }

    public void setOuterCircleRadiusProgress(float outerCircleRadiusProgress) {
        this.outerCircleRadiusProgress = outerCircleRadiusProgress;
        // This method redraws the view
        postInvalidate();
    }

    private float getOuterCircleRadiusProgress() {
        return outerCircleRadiusProgress;
    }


    /**
     * This method is used with an ObjectAnimator class and
     * This method is always executed while the animation is running
     */

    public static final Property<CircleView, Float> INNER_CIRCLE_RADIUS_PROGRESS =
            new Property<CircleView, Float>(Float.class, "innerCircleRadiusProgress") {
                @Override
                public Float get(CircleView object) {

                    return object.getInnerCircleRadiusProgress();
                }

                /**
                 * This method is always call between from min value to max value
                 * @param object refers to this view
                 * @param value return all values between a float values range
                 * For example for a range between 0.1f to 1
                 * value returns : 0.19457565, 0.3904039, 0.4021975, 0.4283119 and so on
                 * until get 1.0
                 */
                @Override
                public void set(CircleView object, Float value) {
                    object.setInnerCircleRadiusProgress(value);
                }
            };

    /**
     * This method is used with an ObjectAnimator class and
     * This method is always executed while the animation is running
     */

    public static final Property<CircleView, Float> OUTER_CIRCLE_RADIUS_PROGRESS =
            new Property<CircleView, Float>(Float.class, "outerCircleRadiusProgress") {
                @Override
                public Float get(CircleView object) {
                    return object.getOuterCircleRadiusProgress();
                }

                @Override
                public void set(CircleView object, Float value) {
                    object.setOuterCircleRadiusProgress(value);
                }
            };


}