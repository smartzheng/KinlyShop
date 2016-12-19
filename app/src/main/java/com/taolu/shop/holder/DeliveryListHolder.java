package com.taolu.shop.holder;

import android.view.View;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.Paymentbean;
import com.taolu.shop.global.RedBabyApplication;

/**
 * Created by ${ghx} on 2016/11/24 0024.
 */
public class DeliveryListHolder extends BaseHolder<Paymentbean> {

    private TextView pay_tv_transtaly;

    @Override
    public View initHolderView() {

        View view = View.inflate(RedBabyApplication.context, R.layout.item_deliverylist, null);
        pay_tv_transtaly = (TextView) view.findViewById(R.id.pay_tv_transtaly);


        return view;
    }

    @Override
    public void bindData(Paymentbean data) {
        pay_tv_transtaly.setText(data.getItem());
    }
}
