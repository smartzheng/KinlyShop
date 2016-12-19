package com.taolu.shop.adapter;

import com.taolu.shop.holder.BaseHolder;
import com.taolu.shop.holder.FavoritesHolder;

import java.util.ArrayList;

/**
 * Created by yy on 2016/11/28.
 */
public class MyFavoritesAdapter extends BasicAdapter {
    public MyFavoritesAdapter(ArrayList list) {
        super(list);
    }

    @Override
    protected BaseHolder getHolder(int position) {
        FavoritesHolder favoritesHolder = new FavoritesHolder();
        favoritesHolder.setCollectorBean(list,position,this);
        return  favoritesHolder;
    }
}
