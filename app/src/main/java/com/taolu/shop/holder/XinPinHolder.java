package com.taolu.shop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.XinPinBean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;

/**
 * Created by yy on 2016/11/24.
 */
public class XinPinHolder extends BaseHolder<XinPinBean.ProductListBean> {
    private ImageView mImageView;
    private TextView mTv_panic_name;
    private TextView mTv_panic_marketPrice;
    private TextView mTv_panic_price;
    private TextView mViewById;
    private TextView mBiaoQian;
    private int position;

    @Override
    public View initHolderView() {
        View inflate = View.inflate(RedBabyApplication.context, R.layout.xinpin_item, null);
        mImageView = (ImageView) inflate.findViewById(R.id.v_panic_iicon);
        mTv_panic_name = (TextView) inflate.findViewById(R.id.tv_panic_name);
        mTv_panic_marketPrice = (TextView) inflate.findViewById(R.id.tv_panic_marketPrice);
        mTv_panic_price = (TextView) inflate.findViewById(R.id.tv_panic_price);
        mBiaoQian = (TextView) inflate.findViewById(R.id.product_biaoqian);

        return inflate;
    }

    @Override
    public void bindData(final XinPinBean.ProductListBean data) {
        if(position % 3 == 0) {
            mBiaoQian.setVisibility(View.VISIBLE);
        }else if(position % 3 == 1){
            mBiaoQian.setBackgroundResource(R.drawable.xinpinxiu_red);
            mBiaoQian.setText("");
        }else {

            mBiaoQian.setVisibility(View.INVISIBLE);
        }
        mTv_panic_name.setText(data.getName());
        mTv_panic_marketPrice.setText("￥"+data.getPrice()+"");
        mTv_panic_price.setText("￥"+data.getMarketPrice()+"");
        HttpLoader.getInstance(RedBabyApplication.context).
                display(mImageView, HttpApi.URL_Service+data.getPic(),R.drawable.image_loader_failure,R.drawable.image_loader_failure,0,0, ImageView.ScaleType.CENTER_CROP);
       // GetBitmapUtils.getBitmap(RedBabyApplication.context,HttpApi.URL_Service+data.getPic());

    }

    public void setPosition(int position) {
        this.position = position;
    }
}
