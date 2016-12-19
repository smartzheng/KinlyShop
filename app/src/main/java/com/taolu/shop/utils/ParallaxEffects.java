package com.taolu.shop.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by ${ZM} on 2016/11/29.
 */
public class ParallaxEffects extends ListView {

    private ImageView iv_header;
    private int orignalHeight;
    private int drawableHeight;

    public ParallaxEffects(Context context) {
        super(context);
    }

    public ParallaxEffects(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxEffects(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setParallaxImage(ImageView iv_header) {
        this.iv_header = iv_header;
        // ImageView 初始高度
        orignalHeight = iv_header.getHeight();
        // 图片的原始高度
        drawableHeight = iv_header.getDrawable().getIntrinsicHeight();
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (deltaY < 0 && isTouchEvent) {
            // deltaY的绝对值, 累加给Header
            int newHeight = iv_header.getHeight() + Math.abs(deltaY / 3);
            if (newHeight <= drawableHeight) {
                System.out.println("newHeight: " + newHeight);
                // 让新的值生效
                iv_header.getLayoutParams().height = newHeight;
                iv_header.requestLayout();
            }

        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:

                // 把当前的头布局的高度currentHeight恢复到初始高度orignalHeight
                final int currentHeight = iv_header.getHeight();

                // 300 -> 160
                ValueAnimator animator = ValueAnimator.ofInt(currentHeight, orignalHeight);
                // 动画更新的监听
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        // 0.0 -> 1.0
                        // 获取动画执行过程中的分度值
                        float fraction = animation.getAnimatedFraction();
                        // 获取中间的值
                        Integer animatedValue = (Integer) animation.getAnimatedValue();
                        // 让新的高度值生效
                        iv_header.getLayoutParams().height = animatedValue;
                        iv_header.requestLayout();
                    }
                });
                animator.setInterpolator(new OvershootInterpolator(2));
                animator.setDuration(500);
                animator.start();
                break;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (startInt + fraction * (endValue - startInt));
    }

}
