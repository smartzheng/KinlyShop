package com.taolu.shop.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.CategoryResponse;
import com.taolu.shop.global.Api;

import org.senydevpkg.net.HttpLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by RainLi on 2016/11/23.
 */
public class CategoryHolder2 extends org.senydevpkg.base.BaseHolder<CategoryResponse.Category> {
    @InjectView(R.id.iv_image1)
    ImageView iv_image1;
    @InjectView(R.id.tv_name1)
    TextView tv_name1;
    /*@InjectView(R.id.iv_image2)
    ImageView iv_image2;
    @InjectView(R.id.iv_image3)
    ImageView iv_image3;

    @InjectView(R.id.tv_name1)
    TextView tv_name2;
    @InjectView(R.id.tv_name1)
    TextView tv_name3;*/

    public CategoryHolder2(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.item_category2,null);
        ButterKnife.inject(this,view);
        return view;
    }


    @Override
    public void bindData(CategoryResponse.Category data) {
        HttpLoader.getInstance(mContext).display(iv_image1, Api.URL_SERVER + data.pic,R.drawable.image_loader_failure,R.drawable.image_loader_failure,0,0, ImageView.ScaleType.FIT_XY);
        tv_name1.setText(data.name);

    }
}
