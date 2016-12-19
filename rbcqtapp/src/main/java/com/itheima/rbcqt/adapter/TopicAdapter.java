package com.itheima.rbcqt.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.itheima.rbcqt.holder.TopicHolder;

import org.senydevpkg.base.AbsBaseAdapter;
import org.senydevpkg.base.BaseHolder;

import java.util.List;

/**
 * Created by xiongmc on 2016/11/21.
 */
public class TopicAdapter extends AbsBaseAdapter {
    /**
     * 接收AbsListView要显示的数据
     *
     * @param context
     * @param data    要显示的数据
     */
    public TopicAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TopicHolder(mContext);
    }
}
