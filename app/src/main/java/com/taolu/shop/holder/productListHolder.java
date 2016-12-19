package com.taolu.shop.holder;

import android.graphics.PointF;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.activity.PayMentActivity;
import com.taolu.shop.adapter.PayMentAdapter;
import com.taolu.shop.bean.PaymentResponse;
import com.taolu.shop.global.Api;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.view.GooViewListener;

import org.senydevpkg.net.HttpLoader;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by ${ghx} on 2016/11/24 0024.
 */
public class productListHolder extends BaseHolder<PaymentResponse.ProductListBean> {
    private ImageView iv_image;
    private TextView pay_tv_descrip;
    private TextView pay_tv_size;
    private TextView pay_tv_price;
    private TextView pay_tv_num;
    private  ArrayList<Object> objects;
    private  int position;
    private  PayMentAdapter payMentAdapter;
    PayMentActivity payMentActivity;

    PayMentAdapter payMentAdapte;
    private TextView point;
    private TextView mUnreadView;
    HashSet<Integer> mRemoved = new HashSet<Integer>();

    public productListHolder(ArrayList<Object> list, int position, PayMentAdapter payMentAdapter, PayMentActivity payMentActivity) {
        objects=list;
        this.position=position;
     this.payMentAdapter  =payMentAdapter;
        this.payMentActivity=payMentActivity;

    }
    onlistschangelistener   listener;
    interface  onlistschangelistener{

        public void setonlistchange(ArrayList<Object> list);
    }

    public void setonlistschangelistener(onlistschangelistener listener){
        this.listener=listener;

    }
    private int prodNum;
    int price;

    @Override
    public View initHolderView() {


        View view = View.inflate(RedBabyApplication.context, R.layout.item_meyproductlist,null);

        iv_image = (ImageView) view.findViewById(R.id.pay_iv_image);
        pay_tv_descrip = (TextView) view.findViewById(R.id.pay_tv_descrip);
        pay_tv_size = (TextView) view.findViewById(R.id.pay_tv_size);
        pay_tv_price = (TextView) view.findViewById(R.id.pay_tv_price);
        pay_tv_num = (TextView) view.findViewById(R.id.pay_tv_num);
        View pay_item_delete = view.findViewById(R.id.pay_item_delete);

        mUnreadView = (TextView) view.findViewById(R.id.point);
        mUnreadView.setTag(position);


        pay_item_delete.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Object obj = objects.get(objects.size() - 4);
               Object removeobj = objects.remove(position);

                if(removeobj instanceof  PaymentResponse.ProductListBean){
                    PaymentResponse.ProductListBean removeolist = (PaymentResponse.ProductListBean) removeobj;
                    PaymentResponse.ProductListBean.ProductBean product = removeolist.getProduct();


                    prodNum =  removeolist.getProdNum();
                     price = removeolist.getProduct().getPrice();


                }


                if(obj instanceof PaymentResponse.CheckoutAddupBean){
                    PaymentResponse.CheckoutAddupBean AddupBean = (PaymentResponse.CheckoutAddupBean) obj;

                    int price = AddupBean.getTotalPrice() - productListHolder.this.price*prodNum;
                      AddupBean.setTotalPrice(price);


                    int product = AddupBean.getTotalCount() - prodNum;
                     AddupBean.setTotalCount(product);

                }

                 payMentActivity.setOnnotifyChanged(objects);
            }
        });




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
        mUnreadView.setTag(prodNum);
        mUnreadView.setText(prodNum+"");

        String picurl = product.getPic();

        pay_tv_descrip.setText(name);
        pay_tv_size.setText(colorunit + ":" + "\t" + color + "\t\t\t" + sizeunit + "\t" + ":" + size);
        pay_tv_price.setText("价格:" + "\t" + price);
        pay_tv_num.setText("数量:" + "\t"+prodNum);
        HttpLoader.getInstance(RedBabyApplication.context).display(iv_image, Api.PAYMENT_URL_SERVER + picurl,
                android.R.drawable.ic_menu_report_image, android.R.drawable.ic_menu_report_image,
                0, 0, ImageView.ScaleType.CENTER_CROP);

        setonAnimation();
    }


    private void setonAnimation() {

        GooViewListener mGooListener = new GooViewListener(payMentActivity, mUnreadView) {
            @Override
            public void onDisappear(PointF mDragCenter) {
                super.onDisappear(mDragCenter);
                mRemoved.add(position);
            }

            @Override
            public void onReset(boolean isOutOfRange) {
              super.onReset(isOutOfRange);

                if(isOutOfRange){

                    Object obj = objects.get(objects.size() - 4);
                    Object removeobj = objects.remove(position);

                    if(removeobj instanceof  PaymentResponse.ProductListBean){
                        PaymentResponse.ProductListBean removeolist = (PaymentResponse.ProductListBean) removeobj;
                        PaymentResponse.ProductListBean.ProductBean product = removeolist.getProduct();


                        prodNum =  removeolist.getProdNum();
                        price = removeolist.getProduct().getPrice();


                    }


                    if(obj instanceof PaymentResponse.CheckoutAddupBean){
                        PaymentResponse.CheckoutAddupBean AddupBean = (PaymentResponse.CheckoutAddupBean) obj;

                        int price = AddupBean.getTotalPrice() - productListHolder.this.price*prodNum;
                        AddupBean.setTotalPrice(price);


                        int product = AddupBean.getTotalCount() - prodNum;
                        AddupBean.setTotalCount(product);

                    }

                    //payMentAdapter.setonnotifaychanged(objects)
                    payMentActivity.setOnnotifyChanged(objects);

                }else{

                    mUnreadView.setVisibility(View.VISIBLE);
                }

                }

        };
        mUnreadView.setOnTouchListener(mGooListener);







    }
}
