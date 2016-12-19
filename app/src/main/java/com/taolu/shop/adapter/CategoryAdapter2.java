package com.taolu.shop.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.taolu.shop.bean.CategoryResponse;
import com.taolu.shop.holder.CategoryHolder2;

import org.senydevpkg.base.AbsBaseAdapter;
import org.senydevpkg.base.BaseHolder;

import java.util.List;

/**
 * Created by RainLi on 2016/11/23.
 */
public class CategoryAdapter2 extends AbsBaseAdapter<CategoryResponse.Category> {
    /**
     * 接收AbsListView要显示的数据
     *
     * @param context
     * @param data    要显示的数据
     */
    public CategoryAdapter2(Context context, List<CategoryResponse.Category> data) {
        super(context, data);
    }

    /*@Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }*/

    @Override
    protected BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder2(mContext);
    }
}
