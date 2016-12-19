package com.taolu.shop.anim;

import android.view.View;

/**
 * Created by m1858 on 2016/11/29.
 */
public class PaddingTopAnim extends BaseAnim {
    public PaddingTopAnim(View target, int startValue, int endValue) {
        super(target, startValue, endValue);
    }

    @Override
    protected void doAnim(int animatedValue) {
        target.setPadding(target.getPaddingLeft(),animatedValue,target.getPaddingRight(),
                -animatedValue);

    }

}
