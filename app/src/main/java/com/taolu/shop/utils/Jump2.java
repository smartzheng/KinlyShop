package com.taolu.shop.utils;

import android.view.View;

import com.taolu.shop.global.RedBabyApplication;

/**
 * Created by yy on 2016/11/24.
 */
public class Jump2 {
    public static void Jump( View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RedBabyApplication.mViewPager != null) {
                    RedBabyApplication.mViewPager.setCurrentItem(1);

                }
            }
        });
    }
}
