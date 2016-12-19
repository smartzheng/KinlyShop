package com.taolu.shop.holder;

import android.view.View;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.comdetailBean;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;

/**
 * Created by ${ghx} on 2016/11/25 0025.
 */
public class detailedinformationHolder extends BaseHolder<comdetailBean> {

    private TextView com_tv_paystyle;
    private TextView com_tv_transstyle;
    private TextView com_saverequare;

    @Override
    public View initHolderView() {
        View view = View.inflate(RedBabyApplication.context, R.layout.tem_detailedinfor, null);
        com_tv_paystyle = (TextView) view.findViewById(R.id.com_tv_paystyle);
        com_tv_transstyle = (TextView) view.findViewById(R.id.com_tv_transstyle);
        com_saverequare = (TextView) view.findViewById(R.id.com_saverequare);

        LogUtil.e("zz","oooooooooooooooo");
        return view;
    }

    @Override
    public void bindData(comdetailBean data) {

        com_tv_paystyle.setText(data.getPaymoney());
        com_tv_transstyle.setText(data.getTranslatestyle());
        com_saverequare.setText(data.getProductrequare());

    }
}
