package com.taolu.shop.adapter;

import com.taolu.shop.holder.BaseHolder;
import com.taolu.shop.holder.XinPinHolder;

import java.util.ArrayList;

/**
 * Created by yy on 2016/11/24.
 */
public class XinPinAdapter extends BasicAdapter {
    public XinPinAdapter(ArrayList list) {
        super(list);
    }

    @Override
    protected BaseHolder getHolder(int position) {
        XinPinHolder holder = new XinPinHolder();
        holder.setPosition(position);
        return holder;
    }
}
