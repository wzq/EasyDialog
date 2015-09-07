package com.wzq.dialog;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by wzq on 15/9/2.
 */
public class SuccessTickView extends View {
    private float mDensity = -1;

    private Paint mPaint, nPaint;

    private float minWidth;

    private float minHeight;

    private float angle, startAngle = 23;

    private final float CONST_RADIUS = dip2px(1.2f);
    private final float CONST_RECT_WEIGHT = dip2px(3);
    private final float CONST_LEFT_RECT_W = dip2px(15);
    private final float CONST_RIGHT_RECT_W = dip2px(25);
    private final float MIN_LEFT_RECT_W = dip2px(3.3f);
    private final float MAX_RIGHT_RECT_W = CONST_RIGHT_RECT_W + dip2px(6.7f);

    private float mMaxLeftRectWidth;
    private float mLeftRectWidth;
    private float mRightRectWidth;
    private boolean mLeftRectGrowMode;

    public SuccessTickView(Context context) {
        this(context, null);
    }

    public SuccessTickView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuccessTickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        minWidth = dip2px(50);
        minHeight = dip2px(50);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dip2px(2.5f));
        mPaint.setColor(getResources().getColor(R.color.success_stroke_color));
        nPaint = new Paint();
        nPaint.setColor(getResources().getColor(R.color.success_stroke_color));
        mLeftRectWidth = CONST_LEFT_RECT_W;
        mRightRectWidth = CONST_RIGHT_RECT_W;
        mLeftRectGrowMode = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.restore();
        Rect bounds = canvas.getClipBounds();

        float left, right, top, bottom;
        if (bounds.width() > bounds.height()) {
            float distance = (bounds.width() / 2 - bounds.height() / 2);
            left = bounds.left + distance;
            right = bounds.right - distance;
            top = bounds.top;
            bottom = bounds.bottom;
        } else if (bounds.width() < bounds.height()) {
            float distance = (bounds.height() / 2 - bounds.width() / 2);
            top = bounds.top + distance;
            bottom = bounds.bottom - distance;
            left = bounds.left;
            right = bounds.right;
        } else {
            left = bounds.left;
            right = bounds.right;
            top = bounds.top;
            bottom = bounds.bottom;
        }
        RectF oval = new RectF(left + dip2px(1), top + dip2px(1), right - dip2px(1), bottom - dip2px(1));
        canvas.drawArc(oval, startAngle, angle, false, mPaint);

        int totalW = getWidth();
        int totalH = getHeight();
        // rotate canvas first
        canvas.rotate(45, totalW / 2, totalH / 2);

        totalW /= 1.2;
        totalH /= 1.4;
        mMaxLeftRectWidth = (totalW + CONST_LEFT_RECT_W) / 2 + CONST_RECT_WEIGHT - 1;

        RectF leftRect = new RectF();
        if (mLeftRectGrowMode) {
            leftRect.left = 0;
            leftRect.right = leftRect.left + mLeftRectWidth;
            leftRect.top = (totalH + CONST_RIGHT_RECT_W) / 2;
            leftRect.bottom = leftRect.top + CONST_RECT_WEIGHT;
        } else {
            leftRect.right = (totalW + CONST_LEFT_RECT_W) / 2 + CONST_RECT_WEIGHT - 1;
            leftRect.left = leftRect.right - mLeftRectWidth;
            leftRect.top = (totalH + CONST_RIGHT_RECT_W) / 2;
            leftRect.bottom = leftRect.top + CONST_RECT_WEIGHT;
        }

        canvas.drawRoundRect(leftRect, CONST_RADIUS, CONST_RADIUS, nPaint);

        RectF rightRect = new RectF();
        rightRect.bottom = (totalH + CONST_RIGHT_RECT_W) / 2 + CONST_RECT_WEIGHT - 1;
        rightRect.left = (totalW + CONST_LEFT_RECT_W) / 2;
        rightRect.right = rightRect.left + CONST_RECT_WEIGHT;
        rightRect.top = rightRect.bottom - mRightRectWidth;
        canvas.drawRoundRect(rightRect, CONST_RADIUS, CONST_RADIUS, nPaint);

        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int desired = (int) (getPaddingLeft() + minWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int desired = (int) (getPaddingTop() + minHeight + getPaddingBottom());
            height = desired;
        }

        setMeasuredDimension(width, height);
    }

    public float dip2px(float dpValue) {
        if (mDensity == -1) {
            mDensity = getResources().getDisplayMetrics().density;
        }
        return dpValue * mDensity + 0.5f;
    }

    public void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                if (value <= 180)
                    angle = -value;
                else {
                    startAngle = 203 - value;
                    angle = value - 360;
                }
                invalidate();

            }
        });
        animator.setDuration(300);
        animator.setStartDelay(215);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        startTickAnim();
    }


    private void startTickAnim() {
        // hide tick
        mLeftRectWidth = 0;
        mRightRectWidth = 0;
        invalidate();
        Animation tickAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                if (0.54 < interpolatedTime && 0.7 >= interpolatedTime) {  // grow left and right rect to right
                    mLeftRectGrowMode = true;
                    mLeftRectWidth = mMaxLeftRectWidth * ((interpolatedTime - 0.54f) / 0.16f);
                    if (0.65 < interpolatedTime) {
                        mRightRectWidth = MAX_RIGHT_RECT_W * ((interpolatedTime - 0.65f) / 0.19f);
                    }
                    invalidate();
                } else if (0.7 < interpolatedTime && 0.84 >= interpolatedTime) { // shorten left rect from right, still grow right rect
                    mLeftRectGrowMode = false;
                    mLeftRectWidth = mMaxLeftRectWidth * (1 - ((interpolatedTime - 0.7f) / 0.14f));
                    mLeftRectWidth = mLeftRectWidth < MIN_LEFT_RECT_W ? MIN_LEFT_RECT_W : mLeftRectWidth;
                    mRightRectWidth = MAX_RIGHT_RECT_W * ((interpolatedTime - 0.65f) / 0.19f);
                    invalidate();
                } else if (0.84 < interpolatedTime && 1 >= interpolatedTime) { // restore left rect width, shorten right rect to const
                    mLeftRectGrowMode = false;
                    mLeftRectWidth = MIN_LEFT_RECT_W + (CONST_LEFT_RECT_W - MIN_LEFT_RECT_W) * ((interpolatedTime - 0.84f) / 0.16f);
                    mRightRectWidth = CONST_RIGHT_RECT_W + (MAX_RIGHT_RECT_W - CONST_RIGHT_RECT_W) * (1 - ((interpolatedTime - 0.84f) / 0.16f));
                    invalidate();
                }
            }
        };
        tickAnim.setDuration(750);
        tickAnim.setStartOffset(105);
        startAnimation(tickAnim);
    }
}
