package com.taolu.shop.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.ProducListResponse;
import com.taolu.shop.global.Api;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by RainLi on 2016/11/28.
 */
public class ProductHolder extends org.senydevpkg.base.BaseHolder<ProducListResponse.ProducListInfo>{
    @InjectView(R.id.iv_product)
    ImageView iv_product;
    @InjectView(R.id.tv_productname)
    TextView tv_productname;
    @InjectView(R.id.tv_product_marketPrice)
    TextView tv_product_marketPrice;
    @InjectView(R.id.tv_product_price)
    TextView tv_product_price;
    @InjectView(R.id.pb_sale)
    ProgressBar pb_sale;
    @InjectView(R.id.tv_sale)
    TextView tv_sale;

    public ProductHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view = View.inflate(RedBabyApplication.context, R.layout.item_produclist,null);
        ButterKnife.inject(this,view);
        return view;
    }

    @Override
    public void bindData(ProducListResponse.ProducListInfo data) {
        Random random = new Random();
        int i = random.nextInt(100);
        pb_sale.setMax(100);
        pb_sale.setProgress(i);
        tv_sale.setText("已经出售"+i+"%");
        HttpLoader.getInstance(mContext).display(iv_product, Api.URL_SERVER + data.pic,R.drawable.image_loader_failure,R.drawable.image_loader_failure,0,0, ImageView.ScaleType.FIT_XY);
        tv_productname.setText(data.name);
        tv_product_marketPrice.setText("市场价:"+data.marketPrice);
        tv_product_price.setText("折扣价:"+data.price);

    }
}
