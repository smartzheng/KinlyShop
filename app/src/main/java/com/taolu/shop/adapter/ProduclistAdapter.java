package com.taolu.shop.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.taolu.shop.holder.ProductHolder;

import org.senydevpkg.base.AbsBaseAdapter;
import org.senydevpkg.base.BaseHolder;

import java.util.List;

/**
 * Created by RainLi on 2016/11/28.
 */
public class ProduclistAdapter extends AbsBaseAdapter{
    /**
     * 接收AbsListView要显示的数据
     *
     * @param context
     * @param data    要显示的数据
     */
    public ProduclistAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductHolder(mContext);
    }
}
