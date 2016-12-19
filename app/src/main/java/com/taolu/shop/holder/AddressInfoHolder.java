package com.taolu.shop.holder;

import android.view.View;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.PaymentResponse;
import com.taolu.shop.global.RedBabyApplication;

/**
 * Created by ${ghx} on 2016/11/24 0024.
 */
public class AddressInfoHolder extends BaseHolder<PaymentResponse.AddressInfoBean> {
    //adress
    private TextView pay_tv_person;
    private TextView pay_tv_phone;
    private TextView pay_tv_adress;

    @Override
    public View initHolderView() {
        View view = View.inflate(RedBabyApplication.context, R.layout.item_adress, null);
        //地址相关
        pay_tv_person = (TextView)view. findViewById(R.id.pay_tv_person);
        pay_tv_phone = (TextView) view.findViewById(R.id.pay_tv_phone);
       pay_tv_adress = (TextView) view.findViewById(R.id.pay_tv_adress);


        return view;
    }

    @Override
    public void bindData(PaymentResponse.AddressInfoBean data) {
        String adressdetail=data.getProvince()+data.getCity()
                +data.getAddressArea()+data.getAddressDetail();
        pay_tv_person.setText(data.getName());
        pay_tv_phone.setText(data.getPhoneNumber()+"");
       pay_tv_adress.setText(adressdetail);


    }
}
