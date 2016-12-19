package com.taolu.shop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.taolu.shop.R;
import com.taolu.shop.adapter.CommodProductAdapter;
import com.taolu.shop.bean.PaymentResponse;
import com.taolu.shop.bean.comdetailBean;
import com.taolu.shop.global.Api;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;
import com.taolu.shop.utils.PrefUtils;
import com.taolu.shop.utils.ToastUtil;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class Commodityorder extends AppCompatActivity implements HttpLoader.HttpListener, View.OnClickListener {

    private ListView commod_lv_order;
    ArrayList<Object> objects = new ArrayList<>();
    private PaymentResponse paymentResponse;
    private PaymentResponse.AddressInfoBean addressInfo;
    private PaymentResponse.CheckoutAddupBean checkoutAddup;
    private List<PaymentResponse.ProductListBean> productList;
    private SharedPreferences sp;
    private CommodProductAdapter adapter;
    private comdetailBean bean;
    private Button bt_cancle;
    private TextView pop_title;
    private Button pop_yes;
    private Button pop_no;
    private View view1;
    private PopupWindow pop;
    private String productid;
    private LinearLayout iv_nodata;
    private TextView pay_tv_return;
    private LinearLayout lldata;
    private LinearLayout commodity_container;
    private Button comm_returnmain;
    private String pId;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodityorder);
        innitview();
        inintData();
        // addlistViewListener();

    }

    private void addlistViewListener() {

        commod_lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (position == 0 || position == objects.size() - 1) {
                    return;
                } else {
                    if (view1 == null) {
                        view1 = view.inflate(RedBabyApplication.context, R.layout.item_popwindom, null);
                        pop_title = (TextView) view1.findViewById(R.id.pop_title);
                        pop_yes = (Button) view1.findViewById(R.id.pop_yes);
                        pop_no = (Button) view1.findViewById(R.id.pop_no);
                        pop_yes.setOnClickListener(Commodityorder.this);
                        pop_no.setOnClickListener(Commodityorder.this);
                    }
                    if (pop != null) {// 检查屏幕上是否已经有了悬浮窗体，有的话就立刻关闭
                        pop.dismiss();
                        pop = null;
                    }
                    pop = new PopupWindow(view1, -1, -2);
                    int[] location = new int[2];
                    view.getLocationInWindow(location);
                    pop.showAtLocation(parent, Gravity.LEFT + Gravity.TOP, 60, location[1]);
                    pop.setBackgroundDrawable(new ColorDrawable(
                            Color.argb(55, 00, 0XAB, 0xCE)));
                    int x = 65;//单位dip
                    int px = DensityUtil.dip2px(getApplicationContext(), x);

                    pop.showAtLocation(parent, Gravity.LEFT + Gravity.TOP,
                            px, location[1]);
                    // 指定缩放动画
                    ScaleAnimation sa = new ScaleAnimation(0.3f, 1.0f, 0.3f, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, 0.5f);
                    sa.setDuration(250);
                    view1.startAnimation(sa);

                    pop_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (pop != null) {

                                pop.dismiss();
                                pop = null;
                            }
                        }
                    });

                    pop_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (pop != null) {

                                pop.dismiss();
                                pop = null;

                            }
                        }
                    });
                }
            }
        });


    }


    private void innitview() {
        sp = getSharedPreferences("paymentinfo", MODE_PRIVATE);
        productid = sp.getString("product", "");
       // String product = PrefUtils.getString(RedBabyApplication.context, "product", "");

        //String productinfamation = getSharedPreferences("paymentinfo", MODE_PRIVATE).getString("product", "");

        commod_lv_order = (ListView) findViewById(R.id.commod_lv_order);

        bt_cancle = (Button) findViewById(R.id.bt_cancle);
        iv_nodata = (LinearLayout) findViewById(R.id.noda);
        pay_tv_return = (TextView) findViewById(R.id.pay_tv_return);
        commodity_container = (LinearLayout) findViewById(R.id.commodity_container);
        comm_returnmain = (Button) findViewById(R.id.comm_returnmain);

        pay_tv_return.setOnClickListener(this);
        bt_cancle.setOnClickListener(this);
        comm_returnmain.setOnClickListener(this);


    }

    private void inintData() {

        //productid
        userid = PrefUtils.getString(RedBabyApplication.context, "userId", "");
        pId = PrefUtils.getString(RedBabyApplication.context, userid+"product", "");
        LogUtil.e("commodiyorder", "数据" + pId);
        if (TextUtils.isEmpty(pId)) {
            iv_nodata.setVisibility(View.VISIBLE);
            commodity_container.setVisibility(View.GONE);
            return;
        }
        iv_nodata.setVisibility(View.GONE);
        commodity_container.setVisibility(View.VISIBLE);

        HttpParams params1 = new HttpParams();
        params1.addHeader("userid", userid);
        params1.put("sku", pId);
        HttpLoader.getInstance(this).post(Api.URL_checkout, params1, PaymentResponse.class,
                Api.REQUEST_CODE_checkout, this).setTag(this);
    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {

        switch (requestCode) {
            case Api.REQUEST_CODE_checkout:
                paymentResponse = (PaymentResponse) response;
                if (paymentResponse != null) {
                    //地址对象
                    addressInfo = paymentResponse.getAddressInfo();
                    //总数
                    checkoutAddup = paymentResponse.getCheckoutAddup();

                    //购物信息
                    productList = paymentResponse.getProductList();
                    String style = sp.getString("style", "");
                    bean = new comdetailBean();
                    bean.setTranslatestyle(style);
                    String realmoney = sp.getString(("realMoney"), "");
                    bean.setPaymoney(realmoney);
                    String requare = sp.getString(("requare"), "");
                    bean.setProductrequare(requare);
                    if (addressInfo != null) {
                        objects.add(addressInfo);
                    } else {
                        LogUtil.e("aaaddressInfo", "........addressInfo..null............");
                    }
                    if (productList != null) {
                        objects.addAll(productList);
                    }
                    if (checkoutAddup != null) {
                        objects.add(checkoutAddup);
                    }
                    adapter = new CommodProductAdapter(objects);
                    commod_lv_order.setAdapter(adapter);


                }
        }
    }


    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
                      LogUtil.e("ppp","获取失败..");
        ToastUtil.showToast("网络请求错误");
        bt_cancle.setVisibility(View.GONE);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pop != null) {
            pop.dismiss();
            pop = null;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_cancle://取消订单

                objects.clear();
                //情况已经购物的所有数据
                PrefUtils.putString(RedBabyApplication.context, userid+"product", "");
                iv_nodata.setVisibility(View.VISIBLE);
                bt_cancle.setVisibility(View.GONE);

                if(adapter==null){

                  return;
                }
                adapter.setonnotifaychanged(objects);
                break;
            case R.id.pay_tv_return://主题栏的返回首页
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.comm_returnmain://购物订单为空点击返回首页
              /*  Intent intentmain = new Intent(this, MainActivity.class);
                startActivity(intentmain);*/
                finish();
                break;


        }
    }
}
