package com.taolu.shop.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.global.RedBabyApplication;

import java.util.ArrayList;

/**
 * Created by yy on 2016/11/29.
 */
public class VerticalViewPagerAdapter extends PagerAdapter {
    ArrayList<String> list  = new ArrayList<>();

    public VerticalViewPagerAdapter(ArrayList<String> list) {
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
        TextView view = (TextView) View.inflate(RedBabyApplication.context, R.layout.verticalviewpager_item, null);
        view.setLines(1);
        view.setText(list.get(position%5));
        container.addView(view);
        return view;
    }
}
