package com.taolu.shop.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.taolu.shop.R;
import com.taolu.shop.activity.BabyProductActivity;
import com.taolu.shop.activity.PayMentActivity;
import com.taolu.shop.bean.BabyProductBean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;
import com.taolu.shop.utils.PrefUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.senydevpkg.net.HttpLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m1858 on 2016/11/25.
 */
public class ProductBottomDialog extends BottomBaseDialog<ProductBottomDialog> implements View.OnClickListener {
    private View mView;
    private Activity mActivity;
    //是否是支付
    private boolean isPay;
    //商品数据
    private BabyProductBean.ProductBean mProduct;

    //颜色的RadioGroup和RadioButton
    private RadioGroup mRgColor;
    private RadioButton color1;
    private RadioButton color2;
    private RadioButton color3;

    //尺寸的RadioGroup和RadioButton;
    private RadioGroup mRgSize;
    private RadioButton size1;
    private RadioButton size2;
    private RadioButton size3;

    //显示支付或者加入购物车
    private TextView pay_or_cart;

    //商品的图片
    private ImageView shop_icon;

    //商品的标题
    private TextView shop_name;

    //商品数量---减少
    private ImageButton shop_sub;

    //商品数量--显示
    private TextView shop_num;

    //商品数量--增加
    private ImageButton shop_add;

    //确定按钮
    private LinearLayout shop_sure;

    //当前颜色Id
    private int currentColorId;

    //当前数量
    private int currentNum = 1;

    //当前尺寸Id
    private int currentSizeId;

    //加载中
    private AVLoadingIndicatorView avLoading;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if(what == LOADING) {
                avLoading.hide();
                sure();
            }
        }
    };


    public ProductBottomDialog(Context context, View animateView) {
        super(context, animateView);
    }

    public ProductBottomDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        mView = View.inflate(RedBabyApplication.context, R.layout.item_shopping_edit, null);
        initViews(mView);
        return mView;
    }


    @Override
    public void setUiBeforShow() {

    }

    /**
     * 数据初始化
     */
    private void initViews(View view) {
        mRgColor = (RadioGroup) view.findViewById(R.id.rg_color);
        color1 = (RadioButton) view.findViewById(R.id.rb_color1);
        color2 = (RadioButton) view.findViewById(R.id.rb_color2);
        color3 = (RadioButton) view.findViewById(R.id.rb_color3);

        mRgSize = (RadioGroup) view.findViewById(R.id.rg_size);
        size1 = (RadioButton) view.findViewById(R.id.rb_size1);
        size2 = (RadioButton) view.findViewById(R.id.rb_size2);
        size3 = (RadioButton) view.findViewById(R.id.rb_size3);

        shop_icon = (ImageView) view.findViewById(R.id.shop_icon);
        shop_name = (TextView) view.findViewById(R.id.shop_name);

        shop_sub = (ImageButton) view.findViewById(R.id.shop_sub);
        shop_num = (TextView) view.findViewById(R.id.shop_num);
        shop_add = (ImageButton) view.findViewById(R.id.shop_add);

        shop_sure = (LinearLayout) view.findViewById(R.id.shop_sure);

        pay_or_cart = (TextView) view.findViewById(R.id.pay_or_cart);

        avLoading = (AVLoadingIndicatorView) view.findViewById(R.id.product_detail_loading);

        //根据当前是支付状态还是加入购物车状态
        if(isPay) {
            pay_or_cart.setText("立即支付");
        }else {
            pay_or_cart.setText("加入购物车");
        }

        shop_sub.setOnClickListener(this);
        shop_add.setOnClickListener(this);

        shop_sure.setOnClickListener(this);

        mRgColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_color1:
                        currentColorId = colors.get(0).getId();
                        break;
                    case R.id.rb_color2:
                        currentColorId = colors.get(1).getId();
                        break;
                    case R.id.rb_color3:
                        currentColorId = colors.get(2).getId();
                        break;
                }
                LogUtil.e("-------当前的颜色Id---------", "" + currentColorId);
            }
        });

        mRgSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_size1:
                        currentSizeId = sizes.get(0).getId();
                        break;
                    case R.id.rb_size2:
                        currentSizeId = sizes.get(1).getId();
                        break;
                    case R.id.rb_size3:
                        currentSizeId = sizes.get(2).getId();
                        break;
                }
                LogUtil.e("-------当前的尺寸Id---------", "" + currentSizeId);
            }
        });
        initData();
    }

    /**
     * 界面点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_sub://减少数量
                if (currentNum <= 1) {
                    return;
                }
                currentNum -= 1;
                shop_num.setText(currentNum + "");
                break;

            case R.id.shop_add://减少数量
                currentNum += 1;
                shop_num.setText(currentNum + "");
                break;

            case R.id.shop_sure://立即支付,判断用户是否登录,如果用户登录,跳转到支付界面,否则跳转到登录界面
                avLoading.show();
                delay();
                break;
        }
    }

    private ArrayList<BabyProductBean.ProductBean.ProductPropertyBean> colors = new ArrayList<>();
    private ArrayList<BabyProductBean.ProductBean.ProductPropertyBean> sizes = new ArrayList<>();

    /**
     * 数据的初始化
     */
    private void initData() {
        if (mProduct == null) {
            return;
        }
        //商品的属性集合
        List<BabyProductBean.ProductBean.ProductPropertyBean> productProperty = mProduct.getProductProperty();
        for (int i = 0; i < productProperty.size(); i++) {
            BabyProductBean.ProductBean.ProductPropertyBean productPropertyBean = productProperty.get(i);
            String k = productPropertyBean.getK();
            if (k.equals("颜色")) {
                colors.add(productPropertyBean);
            } else if (k.equals("大小") || k.equals("尺码") | k.equals("尺寸")) {
                sizes.add(productPropertyBean);
            }
        }
        //设置颜色选择的显示
        if (colors != null && colors.size() > 0) {
            color1.setVisibility(View.VISIBLE);
            color1.setText(colors.get(0).getV());
        }

        if (colors != null && colors.size() > 1) {
            color2.setVisibility(View.VISIBLE);
            color2.setText(colors.get(1).getV());
        }

        if (colors != null && colors.size() > 2) {
            color3.setVisibility(View.VISIBLE);
            color3.setText(colors.get(2).getV());
        }
        //设置尺寸的显示
        if (sizes != null && sizes.size() > 0) {
            size1.setVisibility(View.VISIBLE);
            size1.setText(sizes.get(0).getV());
        }

        if (sizes != null && sizes.size() > 1) {
            size2.setVisibility(View.VISIBLE);
            size2.setText(sizes.get(1).getV());
        }

        if (sizes != null && sizes.size() > 2) {
            size3.setVisibility(View.VISIBLE);
            size3.setText(sizes.get(2).getV());
        }

        List<String> pics = mProduct.getPics();

        if (pics != null && pics.size() > 0) {
            //显示商品的图片
            HttpLoader.getInstance(RedBabyApplication.context).display(shop_icon, HttpApi.URL_Service + pics.get(0),
                    R.drawable.search_instance, R.drawable.search_instance, 0, 0, ImageView.ScaleType.CENTER_CROP);
        }

        //设置商品名称
        shop_name.setText(mProduct.getName());

    }


    /**
     * 设置数据
     *
     * @param activity  所在Activity
     * @param product 商品信息
     * @param isPay 是否是支付
     */
    public void setData(Activity activity, BabyProductBean.ProductBean product,boolean isPay) {
        this.mProduct = product;
        this.mActivity = activity;
        this.isPay = isPay;
    }


    /**
     * 支付
     */
    private void sure() {
        if(currentSizeId == 0 || currentColorId == 0) {
            Toast.makeText(RedBabyApplication.context,"请选择颜色尺寸",Toast.LENGTH_SHORT).show();
            return;
        }

        String idStr = mProduct.getId() + ":" + currentNum + ":" + currentColorId + "," + currentSizeId;
        //根据是否是支付操作,还是加入购物车操作
        if(isPay) {
            LogUtil.e("--------支付Id--------", idStr);
            PrefUtils.putString(RedBabyApplication.context,
                    "payId", idStr);
       Intent intent = new Intent(RedBabyApplication.context, PayMentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        RedBabyApplication.context.startActivity(intent);

            this.dismissWithAnim();
            this.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    BabyProductActivity activity = (BabyProductActivity) mActivity;
                    activity.finish();
                }
            });

        }else {
           String cartId = PrefUtils.getString(RedBabyApplication.context, "cartId", "");
            if(!TextUtils.isEmpty(cartId)) {
                cartId += "|" + idStr;
            }else {
                cartId = idStr;
            }
            PrefUtils.putString(RedBabyApplication.context,"cartId",cartId);
            LogUtil.e("-------------加入购物车-------------",cartId);
            Toast.makeText(RedBabyApplication.context,"加入购物车成功",Toast.LENGTH_SHORT).show();
            this.dismissWithAnim();
        }
        LogUtil.e("-------------idStr-----------",idStr);
    }

    private final int LOADING = 1;
    /**
     * 延时操作
     */
    private void delay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                Message msg = new Message();
                msg.what = LOADING;
                handler.sendMessage(msg);
            }
        }).start();
    }
}
