package com.taolu.shop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.bean.PaymentResponse;
import com.taolu.shop.global.Api;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;

import org.senydevpkg.net.HttpLoader;

import java.util.ArrayList;

/**
 * Created by ${ghx} on 2016/11/23 0023.
 */
public class PayMentHolder extends BaseHolder<PaymentResponse.ProductListBean> {


    private final ArrayList<PaymentResponse.ProductListBean> list;
    private ImageView iv_image;
    private TextView pay_tv_descrip;
    private TextView pay_tv_size;
    private TextView pay_tv_price;
    private TextView pay_tv_num;
    public int position;
    public  PaymentResponse paymentResponse;
    private PaymentResponse.AddressInfoBean adressinfo;
//adress
    private TextView pay_tv_person;
    private TextView pay_tv_phone;
    private TextView pay_tv_adress;
   // private View view;
    private TextView pay_tv_sumproduct;
    private TextView pay_tv_summoney;
    private TextView pay_tv_translateMoney;
    private TextView pay_tv_favourMoeny;
    private TextView pay_tv_realMoney;


    public PayMentHolder(int position, PaymentResponse paymentResponse, ArrayList<PaymentResponse.ProductListBean> list) {
        this.position=position;
        this.paymentResponse =paymentResponse;
        this.list =list;
    }

    @Override
    public View initHolderView() {




        if(position==0){

            View view = View.inflate(RedBabyApplication.context, R.layout.item_adress, null);
            //地址相关
            pay_tv_person = (TextView)view. findViewById(R.id.pay_tv_person);
            pay_tv_phone = (TextView) view.findViewById(R.id.pay_tv_phone);
          //  pay_tv_adress = (TextView) view.findViewById(R.id.pay_tv_adress);
            return view;

        }else if(position==3){
            LogUtil.e("oooo","11111111111111111");
            View view = View.inflate(RedBabyApplication.context, R.layout.item_total,null);

            //总数相关
            pay_tv_sumproduct = (TextView)view. findViewById(R.id.pay_tv_sumproduct);
            pay_tv_summoney = (TextView) view.findViewById(R.id.pay_tv_summoney);
            pay_tv_translateMoney = (TextView)view. findViewById(R.id.pay_tv_translateMoney);
            pay_tv_favourMoeny = (TextView) view.findViewById(R.id.pay_tv_favourMoeny);
            pay_tv_realMoney = (TextView) view.findViewById(R.id.pay_tv_realMoney);
            return view;
        } else{
            LogUtil.e("oooo","2222222222222");


            View view = View.inflate(RedBabyApplication.context, R.layout.item_total, null);

            iv_image = (ImageView) view.findViewById(R.id.pay_iv_image);
            pay_tv_descrip = (TextView) view.findViewById(R.id.pay_tv_descrip);
            pay_tv_size = (TextView) view.findViewById(R.id.pay_tv_size);
            pay_tv_price = (TextView) view.findViewById(R.id.pay_tv_price);
            pay_tv_num = (TextView) view.findViewById(R.id.pay_tv_num);
              if(pay_tv_descrip==null){
                  LogUtil.e("www","....kong,,,,");

              }else{

                  LogUtil.e("www","....buweikong,,,,");

              }
            return view;

        }



    }

    @Override
    public void bindData(PaymentResponse.ProductListBean data) {

        if(position==0){
            LogUtil.e("oooo","wo;ao....");

            adressinfo = paymentResponse.getAddressInfo();

            String adressdetail=adressinfo.getProvince()+adressinfo.getCity()
                    +adressinfo.getAddressArea()+adressinfo.getAddressDetail();

            pay_tv_person.setText(adressdetail);
            pay_tv_phone.setText(adressinfo.getName());
           // pay_tv_adress.setText(adressinfo.getPhoneNumber()+"");

        }else if(position==3){

            PaymentResponse.CheckoutAddupBean checkoutAddup = paymentResponse.getCheckoutAddup();
            pay_tv_sumproduct.setText(checkoutAddup.getTotalCount()+"\t\t"+"件");
            pay_tv_favourMoeny.setText(checkoutAddup.getTotalPoint()+"\t"+"");
            pay_tv_summoney.setText(checkoutAddup.getTotalPrice()+"");
            pay_tv_translateMoney.setText(checkoutAddup.getFreight()+"\t"+"");

            pay_tv_realMoney.setText(checkoutAddup.getTotalPrice()-checkoutAddup.getTotalPoint()+"");


        }
        else {


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

           pay_tv_descrip.setText(name);
            pay_tv_size.setText(colorunit + ":" + "\t" + color + "\t\t\t" + sizeunit + "\t" + ":" + size);
            pay_tv_price.setText("价格:" + "\t" + price);
            pay_tv_num.setText("X" + prodNum);
            HttpLoader.getInstance(RedBabyApplication.context).display(iv_image, Api.URL_SERVER + picurl,
                    android.R.drawable.ic_menu_report_image, android.R.drawable.ic_menu_report_image,
                    0, 0, ImageView.ScaleType.CENTER_CROP);
            // PayMentActivity.setSumMoneyAndProduct(price,prodNum);


        }

    }




}
