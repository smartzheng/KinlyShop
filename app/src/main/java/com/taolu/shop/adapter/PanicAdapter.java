package com.taolu.shop.adapter;

import android.app.Activity;

import com.taolu.shop.holder.BaseHolder;
import com.taolu.shop.holder.PanicHolder;

import java.util.ArrayList;

/**
 * Created by yy on 2016/11/23.
 */
public class PanicAdapter extends BasicAdapter {
    private Activity mActivity;
    public PanicAdapter(ArrayList list) {
        super(list);
    }

    @Override
    protected BaseHolder getHolder(int position) {
        PanicHolder holder = new PanicHolder();
        holder.setActivity(mActivity);
        return holder;
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }
}
