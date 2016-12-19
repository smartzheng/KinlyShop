package com.taolu.shop.holder;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.PanicBean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;

import java.util.Random;

/**
 * Created by yy on 2016/11/23.
 */
public class PanicHolder extends BaseHolder <PanicBean.ProductListBean>{
    private ProgressBar my_progress;
    private ImageView mImageView;
    private TextView mTv_panic_name;
    private TextView mTv_panic_marketPrice;
    private TextView mTv_panic_price;
    private TextView mShopping;
    private Handler mMainHandler;
    private Message mObtain;

    private Activity mActivity;
    /*    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };*/

    @Override
    public View initHolderView() {

        View inflate = View.inflate(RedBabyApplication.context, R.layout.panic_item, null);
        mShopping = (TextView) inflate.findViewById(R.id.shopping);
        mImageView = (ImageView) inflate.findViewById(R.id.v_panic_iicon);
        mTv_panic_name = (TextView) inflate.findViewById(R.id.tv_panic_name);
        mTv_panic_marketPrice = (TextView) inflate.findViewById(R.id.tv_panic_marketPrice);
        mTv_panic_price = (TextView) inflate.findViewById(R.id.tv_panic_price);
        my_progress = (ProgressBar) inflate.findViewById(R.id.my_progress);
        return inflate;
    }

    @Override
    public void bindData(final PanicBean.ProductListBean data) {
        mTv_panic_name.setText(data.getName() + " 限时抢购 火热抢购中");
        mTv_panic_marketPrice.setText("￥" + data.getLimitPrice()+"");
        mTv_panic_price.setText(data.getPrice()+"");

        HttpLoader.getInstance(RedBabyApplication.context).display(mImageView,HttpApi.URL_Service+data.getPic(),
                R.drawable.image_loader_failure,R.drawable.image_loader_failure,0,0, ImageView.ScaleType.CENTER_CROP);

        Random random = new Random();
        int i = random.nextInt(100);
        my_progress.setMax(100);
        my_progress.setProgress(i);
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }


}
