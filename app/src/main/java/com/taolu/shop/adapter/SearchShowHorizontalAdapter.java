package com.taolu.shop.adapter;

import com.taolu.shop.holder.BaseHolder;
import com.taolu.shop.holder.SearchHorizontalHolder;

import java.util.ArrayList;

/**
 * Created by admin on 2016/11/23.
 */
public class SearchShowHorizontalAdapter extends BasicAdapter {

    public SearchShowHorizontalAdapter(ArrayList list) {
        super(list);
    }

    @Override
    protected BaseHolder getHolder(int position) {
        return new SearchHorizontalHolder();
    }
}
