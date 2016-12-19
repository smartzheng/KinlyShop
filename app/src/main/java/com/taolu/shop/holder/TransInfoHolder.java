package com.taolu.shop.holder;

import android.view.View;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;

/**
 * Created by ${ghx} on 2016/11/25 0025.
 */
public class TransInfoHolder extends BaseHolder {

    private TextView textview;

    @Override
    public View initHolderView() {

        View view = View.inflate(RedBabyApplication.context, R.layout.item_translate, null);
        textview = (TextView) view.findViewById(R.id.comm_tv_trans);
        LogUtil.e("zz","22222222222222");

        return view;
    }

    @Override
    public void bindData(Object data) {

    }
}
