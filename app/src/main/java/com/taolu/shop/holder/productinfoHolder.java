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
public class productinfoHolder extends BaseHolder<PaymentResponse.ProductListBean> {

    private TextView com_tv_productcolor;
    private TextView com_tv_productname;
    private TextView com_tv_productnum;
    private TextView com_tv_productsize;
    private TextView com_tv_productprivice;
    private TextView com_tv_detaititle;
    private  int itposition;

    public productinfoHolder(int position) {
       this.itposition=position;
    }

    @Override
    public View initHolderView() {


        View view = View.inflate(RedBabyApplication.context, R.layout.item_productdetail, null);

        com_tv_detaititle = (TextView) view.findViewById(R.id.com_tv_detaititle);
        com_tv_productcolor = (TextView) view.findViewById(R.id.com_tv_productcolor);
        com_tv_productname = (TextView) view.findViewById(R.id.com_tv_productname);
        com_tv_productnum = (TextView) view.findViewById(R.id.com_tv_productnum);
        com_tv_productsize = (TextView) view.findViewById(R.id.com_tv_productsize);
        com_tv_productprivice = (TextView) view.findViewById(R.id.com_tv_productprivice);


        return view;
    }

    @Override
    public void bindData(PaymentResponse.ProductListBean data) {
        int prodNum = data.getProdNum();
        PaymentResponse.ProductListBean.ProductBean product = data.getProduct();
        int id = product.getId();
        String name = product.getName();
        int price = product.getPrice();
        String colorunit = product.getProductProperty().get(0).getK();
        String color = product.getProductProperty().get(0).getV();
        String sizeunit = product.getProductProperty().get(1).getK();
        String size = product.getProductProperty().get(1).getV();

        String picurl = product.getPic();

        com_tv_productname.setText(name);
        com_tv_productcolor.setText(colorunit + ":" + "\t" + color);

        com_tv_productsize.setText(   sizeunit + "\t" + ":" + size);
        com_tv_productprivice.setText("价格:" + "\t" + price);
        com_tv_productnum.setText("尺寸:" + "\t"+prodNum);
        LogUtil.e("zz",colorunit + ":" + "\t" + color+"价格:" + "\t" + price+"X" + "\t"+prodNum);

    }
}
