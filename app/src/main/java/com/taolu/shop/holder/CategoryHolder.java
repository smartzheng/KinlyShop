package com.taolu.shop.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.CategoryResponse;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by RainLi on 2016/11/23.
 */
public class CategoryHolder extends org.senydevpkg.base.BaseHolder<CategoryResponse.Category> {

    @InjectView(R.id.tv_item_category)
    TextView tv_item_category;
    public CategoryHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.item_category,null);
        ButterKnife.inject(this,view);
        return view;
    }

    @Override
    public void bindData(CategoryResponse.Category data) {
        tv_item_category.setText(data.name.substring(0,4));
        if(data.isChecked) {
            tv_item_category.setTextColor(Color.rgb(0,171,206));
        }else {
            tv_item_category.setTextColor(Color.GRAY);
        }


    }



}
