package com.taolu.shop.holder;

import android.view.View;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.CheckouyParmBeen;
import com.taolu.shop.global.RedBabyApplication;

/**
 * Created by ${ghx} on 2016/11/24 0024.
 */
public class CheckoutPromHolder extends BaseHolder<CheckouyParmBeen> {


    private TextView pay_tv_prefareprivce;
    private TextView pay_tv_prefareinfo;

    @Override
    public View initHolderView() {
        View view = View.inflate(RedBabyApplication.context, R.layout.item_checkoutprom, null);
        pay_tv_prefareprivce = (TextView) view.findViewById(R.id.pay_tv_prefareprivce);
        pay_tv_prefareinfo = (TextView) view.findViewById(R.id.pay_tv_prefareinfo);
        pay_tv_prefareprivce.setVisibility(View.VISIBLE);
        pay_tv_prefareinfo.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void bindData(CheckouyParmBeen data) {
        pay_tv_prefareprivce.setText(data.deaultitem);


    }
}
