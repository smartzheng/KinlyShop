package com.taolu.shop.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.HotBean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;

import java.util.Random;

/**
 * Created by yy on 2016/11/24.
 */
public class HotHolder extends BaseHolder<HotBean.ProductListBean> {
    private ImageView mImageView;
    private TextView mTv_panic_name;
    private TextView mTv_panic_marketPrice;
    private TextView mTv_panic_price;
    private TextView mHot;
    private Activity mActivity;
    private ProgressBar mMy_progress;
    private TextView mChushou;

    @Override
    public View initHolderView() {
        View inflate = View.inflate(RedBabyApplication.context, R.layout.hot_item, null);
        mImageView = (ImageView) inflate.findViewById(R.id.v_panic_iicon);
        mTv_panic_name = (TextView) inflate.findViewById(R.id.tv_panic_name);
        mTv_panic_marketPrice = (TextView) inflate.findViewById(R.id.tv_panic_marketPrice);
        mTv_panic_price = (TextView) inflate.findViewById(R.id.tv_panic_price);
        mHot = (TextView) inflate.findViewById(R.id.hot);
        mMy_progress = (ProgressBar) inflate.findViewById(R.id.my_progress);
        mChushou = (TextView) inflate.findViewById(R.id.chushou);
        return inflate;
    }
    public void setActivity(Activity mActivity){
        this.mActivity = mActivity;
    }
    @Override
    public void bindData(final HotBean.ProductListBean data) {
        Random random = new Random();
        int i = random.nextInt(100);
        mMy_progress.setMax(100);
        mMy_progress.setProgress(i);
        mChushou.setText("已经出售"+i+"%");
        mTv_panic_name.setText(data.getName());
        mTv_panic_marketPrice.setText("限时特价：" + "￥" + data.getMarketPrice() + "");
        mTv_panic_price.setText("手机专享：" + "￥" + data.getPrice() + "");
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        HttpLoader.getInstance(RedBabyApplication.context).display(mImageView, HttpApi.URL_Service + data.getPic(), R.drawable.image_loader_failure, R.drawable.image_loader_failure, 0, 0, ImageView.ScaleType.CENTER_CROP);
    }
}
