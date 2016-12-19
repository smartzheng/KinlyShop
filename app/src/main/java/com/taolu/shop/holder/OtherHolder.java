package com.taolu.shop.holder;

import android.view.View;
import android.widget.ImageView;

import com.taolu.shop.R;
import com.taolu.shop.bean.Bandbean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;

/**
 * Created by yy on 2016/11/23.
 */
public class OtherHolder extends BaseHolder<Object> {

    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;

    @Override
    public View initHolderView() {
        View view = View.inflate(RedBabyApplication.context, R.layout.recommend_nutrition_item, null);
        mImageView1 = (ImageView) view.findViewById(R.id.iv_recommend1);
       mImageView2 = (ImageView) view.findViewById(R.id.iv_recommend2);
        mImageView3 = (ImageView) view.findViewById(R.id.iv_recommend3);
        return view;
    }

    @Override
    public void bindData(Object data) {

        Bandbean.BrandBean.ValueBean b= (Bandbean.BrandBean.ValueBean) data;
        HttpLoader.getInstance(RedBabyApplication.context).display(mImageView1, HttpApi.URL_Service+b.getPic(),R.drawable.image_loader_failure,R.drawable.image_loader_failure,0,0, ImageView.ScaleType.CENTER_CROP);
       HttpLoader.getInstance(RedBabyApplication.context).display(mImageView2, HttpApi.URL_Service+b.getPic(),R.drawable.image_loader_failure,R.drawable.image_loader_failure,0,0, ImageView.ScaleType.CENTER_CROP);
        HttpLoader.getInstance(RedBabyApplication.context).display(mImageView3, HttpApi.URL_Service+b.getPic(),R.drawable.image_loader_failure,R.drawable.image_loader_failure,0,0, ImageView.ScaleType.CENTER_CROP);
        //if(data.get)

     //  System.out.println(data.getValue().get(0).getPic()+"22222222222222222");
        /*
        HttpLoader.getInstance(RedBabyApplication.context).display(mImageView1, HttpApi.URL_Service+data.getValue().get(1).getPic());
        HttpLoader.getInstance(RedBabyApplication.context).display(mImageView1, HttpApi.URL_Service+data.getValue().get(2).getPic());*/
      //  HttpLoader.getInstance(RedBabyApplication.context).display(mImageView1, HttpApi.URL_Service+data.getPic());

    }
}
