package com.taolu.shop.holder;

import android.view.View;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.PaymentResponse;
import com.taolu.shop.global.RedBabyApplication;

/**
 * Created by ${ghx} on 2016/11/24 0024.
 */
public class CheckoutAddupHolder extends BaseHolder<PaymentResponse.CheckoutAddupBean> {
    private TextView pay_tv_sumproduct;
    private TextView pay_tv_summoney;
    private TextView pay_tv_translateMoney;
    private TextView pay_tv_favourMoeny;
    private TextView pay_tv_realMoney;
    @Override
    public View initHolderView() {

        View view = View.inflate(RedBabyApplication.context, R.layout.item_chectoutaddup, null);

        pay_tv_sumproduct = (TextView) view.findViewById(R.id.pay_tv_sumproduct);
        pay_tv_summoney = (TextView) view.findViewById(R.id.pay_tv_summoney);
        pay_tv_translateMoney = (TextView) view.findViewById(R.id.pay_tv_translateMoney);
        pay_tv_favourMoeny = (TextView) view.findViewById(R.id.pay_tv_favourMoeny);
        pay_tv_realMoney = (TextView) view.findViewById(R.id.pay_tv_realMoney);

        return view;
    }

    @Override
    public void bindData(PaymentResponse.CheckoutAddupBean data) {
        pay_tv_sumproduct.setText(data.getTotalCount()+"\t\t"+"件");
        pay_tv_favourMoeny.setText(data.getTotalPoint()+"\t"+"");
        pay_tv_summoney.setText(data.getTotalPrice()*data.getTotalCount()+"");
       //LogUtil.e("pppp",data.getTotalPrice()+"");
        pay_tv_translateMoney.setText(data.getFreight()+"\t"+"");
        //data.setTotalPrice(data.getTotalPrice());


        pay_tv_realMoney.setText(data.getTotalPrice()*data.getTotalCount()+"");


    }
}
