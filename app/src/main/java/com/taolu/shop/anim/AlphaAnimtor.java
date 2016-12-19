package com.taolu.shop.anim;

import android.view.View;

/**
 * Created by m1858 on 2016/11/29.
 */
public class AlphaAnimtor extends BaseAnim {

    public AlphaAnimtor(View target, int startValue, int endValue) {
        super(target, startValue, endValue);
    }

    @Override
    protected void doAnim(int animatedValue) {
        target.getBackground().setAlpha(animatedValue);
    }
}
