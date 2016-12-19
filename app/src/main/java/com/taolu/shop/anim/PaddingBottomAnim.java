package com.taolu.shop.anim;

import android.view.View;

/**
 * Created by m1858 on 2016/11/29.
 */
public class PaddingBottomAnim extends BaseAnim {

    public PaddingBottomAnim(View target, int startValue, int endValue) {
        super(target, startValue, endValue);
    }

    @Override
    protected void doAnim(int animatedValue) {
        target.setPadding(target.getPaddingLeft(),-animatedValue,target.getPaddingRight(),
                animatedValue);
        if(endValue == animatedValue && listener != null) {
            listener.onAnimEnd();
        }
    }

    private onAnimEndListener listener;

    public interface onAnimEndListener{
        void onAnimEnd();
    }

    public PaddingBottomAnim setOnAnimEndListener(onAnimEndListener listener) {
        this.listener = listener;
        return this;
    }
}
