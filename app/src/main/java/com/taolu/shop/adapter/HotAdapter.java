package com.taolu.shop.adapter;

import android.app.Activity;

import com.taolu.shop.holder.BaseHolder;
import com.taolu.shop.holder.HotHolder;

import java.util.ArrayList;

/**
 * Created by yy on 2016/11/24.
 */
public class HotAdapter extends BasicAdapter{
    public Activity mActivity;
    public HotAdapter(ArrayList list) {
        super(list);
    }

    @Override
    protected BaseHolder getHolder(int position) {
        HotHolder hotHolder = new HotHolder();
        hotHolder.setActivity(mActivity);
        return hotHolder;
    }
    public void setAcyivity(Activity activity){
        this.mActivity = activity;
    }
}
