package com.taolu.shop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.SalesBean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;

/**
 * Created by yy on 2016/11/23.
 */
public class SalesHolder extends BaseHolder<SalesBean.TopicBean> {

    private ImageView mSales_holder_iv;
    private TextView mSales_holder_tv;
    private View mView;

    @Override
    public View initHolderView() {
        View view = View.inflate(RedBabyApplication.context, R.layout.sales_holder, null);
        mSales_holder_iv = (ImageView) view.findViewById(R.id.sales_holder_iv);
        mSales_holder_tv = (TextView) view.findViewById(R.id.sales_holder_tv);
        //mView = view.findViewById(R.id.sales_holder_view);
        return view;
    }

    @Override
    public void bindData(SalesBean.TopicBean data) {
        String name = data.name;
        mSales_holder_tv.setText(name);
        HttpLoader.getInstance(RedBabyApplication.context).
                display(mSales_holder_iv, HttpApi.URL_Service+data.pic,R.drawable.image_loader_failure,R.drawable.image_loader_failure,0,0, ImageView.ScaleType.CENTER_CROP);
        //GetBitmapUtils.getBitmap(RedBabyApplication.context, HttpApi.URL_Service+data.pic);

    }
}
