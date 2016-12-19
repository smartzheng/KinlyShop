package com.taolu.shop.fragment;

import android.support.v4.app.Fragment;

/**
 * 创建Fragment的工厂类
 * Created by m1858 on 2016/11/22.
 */
public class FragmentFactory {

    /**
     * 根据索引创建Fragment
     * @param position
     */
    public static Fragment create(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;

            case 1:
                fragment = new SearchFragment();
                break;

            case 2:
                fragment = new BrandFragment();
                break;

            case 3:
                fragment = new CartFragment();
                break;

            case 4:
                fragment = new PersonalFragment();
                break;
        }

        return fragment;
    }
}
