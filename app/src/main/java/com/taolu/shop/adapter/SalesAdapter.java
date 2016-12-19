package com.taolu.shop.adapter;

import com.taolu.shop.holder.BaseHolder;
import com.taolu.shop.holder.SalesHolder;

import java.util.ArrayList;

/**
 * Created by yy on 2016/11/23.
 */
public class SalesAdapter extends BasicAdapter {
    public SalesAdapter(ArrayList list) {
        super(list);
    }

    /**
     *
     * @param position
     * @return
     */

    @Override
    protected BaseHolder getHolder(int position) {
        return new SalesHolder();
    }
}
