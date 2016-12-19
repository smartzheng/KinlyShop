package com.taolu.shop.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.taolu.shop.R;
import com.taolu.shop.bean.HomeBean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;

import java.util.ArrayList;

/**
 * Created by yy on 2016/11/22.
 */
public class HomeAdapter extends PagerAdapter {
    ArrayList list= new ArrayList();

    public HomeAdapter(ArrayList list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        HomeBean.HomeTopicBean o = (HomeBean.HomeTopicBean) list.get(position%list.size());
        ImageView m = new ImageView(RedBabyApplication.context);
        m.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String url = o.getPic();
        HttpLoader.getInstance(RedBabyApplication.context).display(m, HttpApi.URL_Service+url, R.drawable.menu_1_1,R.drawable.menu_1_1);
        container.addView(m);
        return m;
    }
}
