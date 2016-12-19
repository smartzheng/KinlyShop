package com.taolu.shop.holder;

import android.view.View;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.payMoneyStyle;
import com.taolu.shop.global.RedBabyApplication;

/**
 * Created by ${ghx} on 2016/11/24 0024.
 */
public class paymentListHolder extends BaseHolder<payMoneyStyle> {
   private TextView pay_tv_givemoneystyle;
    @Override
    public View initHolderView() {
        View view = View.inflate(RedBabyApplication.context, R.layout.item_paylist, null);
        pay_tv_givemoneystyle = (TextView) view.findViewById(R.id.pay_tv_givemoneystyle);
        return view;
    }

    @Override
    public void bindData(payMoneyStyle data) {
        pay_tv_givemoneystyle.setText(data.getDefaultstyle());
    }
}
