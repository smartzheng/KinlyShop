package com.taolu.shop.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by yy on 2016/11/25.
 */
public class ImagePagerAdpter extends PagerAdapter {
    ArrayList<String> list = new ArrayList<>();

    public ImagePagerAdpter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        PhotoView iv = new PhotoView(RedBabyApplication.context);
        HttpLoader.getInstance(RedBabyApplication.context).display(iv, HttpApi.URL_Service+list.get(position));
        container.addView(iv);
        return iv;
    }
}
