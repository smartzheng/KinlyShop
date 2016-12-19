package com.taolu.shop.adapter;

import com.taolu.shop.holder.BaseHolder;
import com.taolu.shop.holder.SearchVerticalHolder;

import java.util.ArrayList;

/**
 * Created by admin on 2016/11/23.
 */
public class SearchShowVerticalAdapter extends BasicAdapter {

    public SearchShowVerticalAdapter(ArrayList list) {

        super(list);
    }

    @Override
    protected BaseHolder getHolder(int position) {
        return new SearchVerticalHolder();
    }
}
