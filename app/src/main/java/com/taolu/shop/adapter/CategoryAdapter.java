package com.taolu.shop.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.taolu.shop.holder.CategoryHolder;

import org.senydevpkg.base.AbsBaseAdapter;

import java.util.List;

/**
 * Created by RainLi on 2016/11/23.
 */
public class CategoryAdapter extends AbsBaseAdapter {

    public CategoryAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected org.senydevpkg.base.BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(mContext);
    }
}
