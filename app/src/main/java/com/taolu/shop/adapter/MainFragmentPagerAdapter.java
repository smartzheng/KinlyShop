package com.taolu.shop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.taolu.shop.R;
import com.taolu.shop.fragment.FragmentFactory;
import com.taolu.shop.utils.CommonUtil;

/**
 * Created by m1858 on 2016/11/22.
 */
public class MainFragmentPagerAdapter extends FragmentStatePagerAdapter{

    String[] tabs = CommonUtil.getStringArray(R.array.tab_names);

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.create(position);
    }

    @Override
    public int getCount() {
        return tabs.length;
    }


}
