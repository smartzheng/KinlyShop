package com.taolu.shop.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.taolu.shop.R;
import com.taolu.shop.adapter.PayMentAdapter;
import com.taolu.shop.bean.CheckouyParmBeen;
import com.taolu.shop.bean.PaymentResponse;
import com.taolu.shop.bean.Paymentbean;
import com.taolu.shop.bean.payMoneyStyle;
import com.taolu.shop.global.Api;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;
import com.taolu.shop.utils.PrefUtils;
import com.taolu.shop.utils.ToastUtil;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

public class PayMentActivity extends AppCompatActivity implements HttpLoader.HttpListener, View.OnClickListener {

    private TextView tv_return;
    private ListView payinformationList;
    private PayMentAdapter payMentAdapter;
    //从服务器获取的bean对象
    private PaymentResponse paymentResponse;
    private PaymentResponse.AddressInfoBean addressInfo;
    private PaymentResponse.CheckoutAddupBean checkoutAddup;
    private List<String> checkoutProm;
    private List<PaymentResponse.DeliveryListBean> deliveryList;
    private List<PaymentResponse.PaymentListBean> paymentList;
    private List<PaymentResponse.ProductListBean> productList;

    ArrayList<Object> objects = new ArrayList<>();
    private Paymentbean paymentbean;
    private payMoneyStyle paymoneystyle;
    private CheckouyParmBeen chectoutPrombeen;
    private Button pay_bt_post;
    private PopupWindow pop;
    private View pay_mainlayout;
    private int width;
    private int height;
    private SharedPreferences sp;
    private ImageView imageloading;
    private HttpParams params1;
    private TextView pay_tv_loading;
    private String mUserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ment);
        inintView();
        inintData();
        sp = getSharedPreferences("paymentinfo", MODE_PRIVATE);
    }

    /**
     * 初始化布局
     */
    private void inintView() {
        pay_mainlayout = findViewById(R.id.pay_mainlayout);
        //获取屏幕的宽高
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        width = defaultDisplay.getWidth();
        height = defaultDisplay.getHeight();
        //网络加载进度
        pay_tv_loading =(TextView) findViewById(R.id.pay_tv_loading);
        imageloading = (ImageView) findViewById(R.id.sucess_loading);

        pay_bt_post = (Button) findViewById(R.id.pay_bt_post);
        //标题栏返回
        tv_return = (TextView) findViewById(R.id.pay_tv_return);


        payinformationList = (ListView) findViewById(R.id.Pay_list);

        pay_bt_post.setOnClickListener(this);
        tv_return.setOnClickListener(this);
        pay_bt_post.setVisibility(View.GONE);

        payinformationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = objects.get(position);
                isinstanseof(object, view, position);
            }
        });


    }

    /**
     * 初始化数据
     */
    private void inintData() {
        mUserid = PrefUtils.getString(RedBabyApplication.context, "userId", "");
        LogUtil.e("userid:", mUserid + "................8");
        // String userName = PrefUtils.getString(RedBabyApplication.context, "userName", "");
        String pId = PrefUtils.getString(RedBabyApplication.context, "payId", "");

        LogUtil.e("pId", "数据获取" + pId);


        if (mUserid.equals("")) {

            Toast.makeText(RedBabyApplication.context, "请先登录..", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("isPay",true);
            startActivity(intent);
            finish();

            return;

        }
        if (TextUtils.isEmpty(pId)) {
            Toast.makeText(RedBabyApplication.context, "还没有商品", Toast.LENGTH_SHORT).show();
            imageloading.clearAnimation();
            imageloading.setVisibility(View.INVISIBLE);
            return;
        }

        params1 = new HttpParams();
        params1.addHeader("userid", mUserid);
        params1.put("sku", pId);
        imageloading.setVisibility(View.VISIBLE);
        //加载进度条动画
        imageloading.setBackgroundResource(R.drawable.loading_animator);
        AnimationDrawable animator = (AnimationDrawable) imageloading.getBackground();
        animator.start();


        HttpLoader.getInstance(PayMentActivity.this).post(Api.URL_checkout, params1, PaymentResponse.class,
                Api.REQUEST_CODE_checkout, PayMentActivity.this).setTag(this);


    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {

        switch (requestCode) {
            case Api.REQUEST_CODE_checkout:
                pay_bt_post.setVisibility(View.VISIBLE);
                //隐藏动画
               imageloading.clearAnimation();
                imageloading.setVisibility(View.GONE);
                pay_tv_loading.setVisibility(View.GONE);

                if(!(response instanceof  PaymentResponse)){

                    Intent intentlog=new Intent(this,LoginActivity.class);
                    startActivity(intentlog);
                    finish();

                    return;
                }

                paymentResponse = (PaymentResponse) response;
                if (paymentResponse != null) {
                    //地址对象
                    addressInfo = paymentResponse.getAddressInfo();
                    //总数
                    checkoutAddup = paymentResponse.getCheckoutAddup();
                    //促销信息
                    checkoutProm = paymentResponse.getCheckoutProm();
                    chectoutPrombeen = new CheckouyParmBeen();
                    chectoutPrombeen.setList(checkoutProm);
                    //发货时间
                    deliveryList = paymentResponse.getDeliveryList();
                    paymentbean = new Paymentbean();
                    paymentbean.setList(deliveryList);
                    //支付方式
                    paymentList = paymentResponse.getPaymentList();
                    paymoneystyle = new payMoneyStyle();
                    paymoneystyle.setList(paymentList);
                    //购物信息
                    productList = paymentResponse.getProductList();

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
                    if (checkoutProm != null) {
                        objects.add(chectoutPrombeen);
                    }
                    if (deliveryList != null) {
                        objects.add(paymentbean);
                    }
                    if (paymentList != null) {
                        objects.add(paymoneystyle);
                    }
                    payMentAdapter = new PayMentAdapter(objects,this);

                    payinformationList.setAdapter(payMentAdapter);


                }

        }
    }



    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        LogUtil.e("PayMent:", "数据获取失败...");
           ToastUtil.showToast("数据获取失败...");
        //隐藏动画
        imageloading.clearAnimation();
        imageloading.setVisibility(View.VISIBLE);
        imageloading.setBackgroundResource(R.drawable.nowife);
        pay_tv_loading.setVisibility(View.VISIBLE);
        pay_tv_loading.setText("网络连接失败...");
        pay_bt_post.setVisibility(View.GONE);





    }

    @Override
    protected void onDestroy() {
        HttpLoader.getInstance(this).cancelRequest(this);
        super.onDestroy();
        if (pop != null) {
            pop.dismiss();
            pop = null;

        }
        if (imageloading.getAnimation() != null) {
            imageloading.clearAnimation();
            imageloading.setVisibility(View.GONE);
            pay_tv_loading.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_tv_return:


                Intent intentreturn = new Intent(this, MainActivity.class);
                startActivity(intentreturn);
                finish();


                break;

            case R.id.pay_bt_post:
                //  商品ID:数量:属性ID|商品ID:数量:属性ID
                // 	1:3:1,2,3,4|2:2:2,3

               if(objects!=null){

                   if(productList==null){

                       pay_bt_post.setVisibility(View.GONE);
                           return;
                   }else{
                       pay_bt_post.setVisibility(View.VISIBLE);

                   }
                   productList.clear();

                   for(int i=0;i<objects.size();i++){


                       Object ob = objects.get(i);


                       if(ob instanceof PaymentResponse.ProductListBean){

                           PaymentResponse.ProductListBean bean=    ( PaymentResponse.ProductListBean) ob;
                           productList.add(bean);

                       }

                   }


               }



                if (productList.size() ==0) {
                    imageloading.clearAnimation();
                    imageloading.setVisibility(View.INVISIBLE);
                   ToastUtil.showToast("还没有选择商品,请先选择商品");

                    return;
                }
                String product = "";
                for (int y = 0; y < productList.size(); y++) {
                    PaymentResponse.ProductListBean bean = productList.get(y);
                    if (y != 0) {
                        product = product + "|";
                    }

                    int id = bean.getProduct().getId();
                    product = product + id + ":";
                    int prodNum = bean.getProdNum();
                    product = product + prodNum + ":";
                    List<PaymentResponse.ProductListBean.ProductBean.ProductPropertyBean> productProperty
                            = bean.getProduct().getProductProperty();


                    for (int x = 0; x < productProperty.size(); x++) {

                        if (x == productProperty.size() - 1) {
                            product = product + productProperty.get(x).getId();
                        } else {
                            product = product + productProperty.get(x).getId() + ",";
                        }

                    }


                }

                SharedPreferences.Editor edit = sp.edit();
                edit.putString("product", product);
                edit.commit();
                if (addressInfo == null) {
                    ToastUtil.showToast("请输入地址");
                    return;
                }

                String name = addressInfo.getName();
                String province = addressInfo.getProvince();
                String phoneNumber = addressInfo.getPhoneNumber();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(province)) {
                    ToastUtil.showToast("地址相关信息不能为空");
                    return;
                }

                Intent intent = new Intent(this, PaymentSuccess.class);
                intent.putExtra("sumproduct", checkoutAddup.getTotalCount() + "");
                intent.putExtra("realMoney", checkoutAddup.getTotalPrice() - checkoutAddup.getTotalPoint() + "");
                intent.putExtra("style", paymoneystyle.getDefaultstyle());

                SharedPreferences.Editor edit1 = sp.edit();
                edit1.putString("realMoney", checkoutAddup.getTotalPrice() - checkoutAddup.getTotalPoint() + "");
                edit1.putString("style", paymoneystyle.getDefaultstyle());
                edit1.putString("requare", paymentbean.deaultitem);
                if (payMoneyListener != null) {
                    payMoneyListener.onPayformoney("000");
                }

                   //支付成功
                String products = PrefUtils.getString(RedBabyApplication.context, mUserid+"product", "");
                 if(TextUtils.isEmpty(products)){
                     products=product;

                 }else{


                     products=products+"|"+product;
                 }


                PrefUtils.putString(RedBabyApplication.context,mUserid+"product",products);
                  PrefUtils.putString(RedBabyApplication.context, "paySuccessId", product);
                  PrefUtils.putString(RedBabyApplication.context,"payId","");
                String payId = PrefUtils.getString(RedBabyApplication.context, "payId", "");
                LogUtil.e("zhifu",payId+"支付后");
                startActivity(intent);
                finish();
        }
    }


    public void isinstanseof(Object object, View view, int position) {

        if (object instanceof PaymentResponse.AddressInfoBean) {

        } else {
            if (object instanceof PaymentResponse.ProductListBean) {

                PaymentResponse.ProductListBean productinfo = (PaymentResponse.ProductListBean) object;
                View inflate = View.inflate(RedBabyApplication.context, R.layout.item_payinfo, null);

                RadioButton radiocheck = (RadioButton) inflate.findViewById(R.id.pay_Rb_itempay);


                radiocheck.setChecked(false);
                if (radiocheck.isChecked()) {


                } else {
                    PaymentResponse.CheckoutAddupBean checkoutAddupBean = (PaymentResponse.CheckoutAddupBean) objects.get(objects.size() - 4);
                    int toal = checkoutAddupBean.getTotalCount() - productinfo.getProdNum();
                    int money = checkoutAddup.getTotalPrice() - productinfo.getProduct().getPrice() + checkoutAddup.getTotalPoint();
                    checkoutAddup.setTotalCount(toal);
                    checkoutAddup.setTotalPrice(money);



                    objects.remove(productinfo);

                    payMentAdapter.setonnotifaychanged(objects);

                }

            } else if (object instanceof PaymentResponse.CheckoutAddupBean) {

            } else if (object instanceof CheckouyParmBeen) {


                CheckouyParmBeen payMoneyStyle = (CheckouyParmBeen) object;
                List<String> list = payMoneyStyle.getList();
                String[] arr = new String[list.size()];
                for (int x = 0; x < list.size(); x++) {
                    arr[x] = list.get(x);
                }
                setAletDialog(arr, "优惠活动", chectoutPrombeen);

            } else if (object instanceof Paymentbean) {
                Paymentbean payMoneyStyle = (Paymentbean) object;
                List<PaymentResponse.DeliveryListBean> list = payMoneyStyle.getList();
                String[] arr = new String[list.size()];
                for (int x = 0; x < list.size(); x++) {
                    PaymentResponse.DeliveryListBean paymentListBean = list.get(x);

                    String s = paymentListBean.getType() + paymentListBean.getDes();

                    arr[x] = s;
                }


                setAletDialog(arr, "送货方式", paymentbean);

            } else if (object instanceof payMoneyStyle) {

                payMoneyStyle payMoneyStyle = (payMoneyStyle) object;
                List<PaymentResponse.PaymentListBean> list = payMoneyStyle.getList();
                String[] arr = new String[list.size()];
                for (int x = 0; x < list.size(); x++) {
                    PaymentResponse.PaymentListBean paymentListBean = list.get(x);

                    String s = paymentListBean.getType() + paymentListBean.getDes();

                    arr[x] = s;
                }


                setAletDialog(arr, "支付方式", payMoneyStyle);


            }
        }


    }

    private int mCurrentWhich = 0;
    private int choicetitem;

    public void setAletDialog(final String[] items, String title, final Object object) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);


        builder.setSingleChoiceItems(items, mCurrentWhich, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                choicetitem = which;
            }

        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String PayMoneyStyle = null;


                switch (choicetitem) {
                    case 0:
                        PayMoneyStyle = items[0];
                        // paymoneystyle.setDefaultstyle(PayMoneyStyle);
                        break;
                    case 1:
                        PayMoneyStyle = items[1];
                        // paymoneystyle.setDefaultstyle(PayMoneyStyle);
                        break;
                    case 2:
                        PayMoneyStyle = items[2];
                        break;
                }


                if (object instanceof PaymentResponse.AddressInfoBean) {

                } else if (object instanceof PaymentResponse.ProductListBean) {

                } else if (object instanceof PaymentResponse.CheckoutAddupBean) {

                } else if (object instanceof CheckouyParmBeen) {
                    chectoutPrombeen.setDeaultitem(PayMoneyStyle);


                } else if (object instanceof Paymentbean) {
                    paymentbean.setItem(PayMoneyStyle);

                } else if (object instanceof payMoneyStyle) {


                    paymoneystyle.setDefaultstyle(PayMoneyStyle);
                }


                payMentAdapter.notifyDataSetChanged();

                mCurrentWhich = choicetitem;

            }


        });

        builder.setNegativeButton("取消", null);
        builder.show();

    }

    PayMoneyListener payMoneyListener;
    PaymentResponse.CheckoutAddupBean obean1;

    public void setOnnotifyChanged(ArrayList<Object> list) {



        for(int x=0;x<list.size();x++){

            Object obean = list.get(x);

            if(obean instanceof PaymentResponse.CheckoutAddupBean){
               obean1 = (PaymentResponse.CheckoutAddupBean) obean;


            }

            if(obean instanceof PaymentResponse.ProductListBean ){

                continue;
            }

            if(x==list.size()-1){

                    obean1.setFreight(0);
                obean1.setTotalPoint(0);

            }


        }
        objects=list;

        payMentAdapter.setonnotifaychanged(objects);

    }


    interface PayMoneyListener {
        public abstract void onPayformoney(String productproperty);

    }




}


