package com.taolu.shop.holder;

import android.view.View;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.PaymentResponse;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;

/**
 * Created by ${ghx} on 2016/11/25 0025.
 */
public class comAdressinfo extends BaseHolder<PaymentResponse.AddressInfoBean> {

    private TextView com_tv_adress;
    private TextView com_tv_person;
    private TextView com_tv_phone;

    @Override
    public View initHolderView() {
        View view = View.inflate(RedBabyApplication.context, R.layout.tem_comadress, null);
        com_tv_adress = (TextView) view.findViewById(R.id.com_tv_adress);
        com_tv_person = (TextView) view.findViewById(R.id.com_tv_person);
        com_tv_phone = (TextView) view.findViewById(R.id.com_tv_phone);
        LogUtil.e("zz",".............rr");
        return view;
    }

    @Override
    public void bindData(PaymentResponse.AddressInfoBean data) {
        String adressdetail=data.getProvince()+data.getCity()
                +data.getAddressArea()+data.getAddressDetail();
        com_tv_adress.setText(adressdetail);

        com_tv_person.setText(data.getName());
        com_tv_person.setText(data.getPhoneNumber());
        LogUtil.e("zz",adressdetail+data.getName()+data.getPhoneNumber());

    }
}
