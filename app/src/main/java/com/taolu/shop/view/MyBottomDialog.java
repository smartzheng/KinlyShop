package com.taolu.shop.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.taolu.shop.R;
import com.taolu.shop.bean.CartInfo;
import com.taolu.shop.fragment.CartFragment;
import com.taolu.shop.global.CartApi;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;
import com.taolu.shop.utils.PrefUtils;

import org.senydevpkg.net.HttpLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m1858 on 2016/11/25.
 */
public class MyBottomDialog extends BottomBaseDialog<MyBottomDialog> {
    private View view;
    private ImageView bianji_icon;//商品图片
    private TextView bianji_name;//商品标题
    private TextView bianji_msg;//商品属性
    private ImageButton bianji_sub;//减少商品数量
    private TextView tv_bianji_num;//显示商品数量
    private ImageButton bianji_add;//增加商品数量
    private LinearLayout cart_bianji_sure;//确定按钮
    private ArrayList<CartInfo.CartBean> cartBeans;
    private CartInfo.CartBean cartBean;


    public MyBottomDialog(Context context, View animateView) {
        super(context, animateView);
    }

    public MyBottomDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        view = View.inflate(mContext,R.layout.item_cart_edit,null);
        bianji_icon = (ImageView) view.findViewById(R.id.bianji_icon);
        bianji_name = (TextView) view.findViewById(R.id.cart_bianji_name);
        bianji_msg = (TextView) view.findViewById(R.id.bianji_msg);
        bianji_sub = (ImageButton) view.findViewById(R.id.bianji_sub);
        tv_bianji_num = (TextView) view.findViewById(R.id.tv_bianji_num);
        bianji_add = (ImageButton) view.findViewById(R.id.bianji_add);
        cart_bianji_sure = (LinearLayout) view.findViewById(R.id.cart_bianji_sure);
        return view;
    }



    @Override
    public void setUiBeforShow() {
        if(cartBean == null) {
            return;
        }
        //设置显示的数据
        CartInfo.CartBean.ProductBean product = cartBean.getProduct();
        //图片
        HttpLoader.getInstance(RedBabyApplication.context).display(bianji_icon,
                CartApi.URL_Service + product.getPic());
       //标题
        bianji_name.setText(product.getName());
        //属性
        List<CartInfo.CartBean.ProductBean.ProductPropertyBean> productProperty = product.getProductProperty();
        String msgStr = "";
        for (int i = 0; i < productProperty.size(); i++) {
            if(i != productProperty.size() - 1) {
                msgStr += productProperty.get(i).getK() + ":" + productProperty.get(i).getV() +",";
            }else {
                msgStr += productProperty.get(i).getK() + ":" + productProperty.get(i).getV();
            }
        }
        bianji_msg.setText(msgStr);
        //数量
        tv_bianji_num.setText(cartBean.getProdNum() + "");


        //减少商品数量点击事件
        bianji_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(tv_bianji_num.getText().toString());
                if(i <=1) {
                    return;
                }
                int newNum = i - 1;
                tv_bianji_num.setText(newNum + "");
                cartBean.setProdNum(newNum);
            }
        });

        //增加商品数量点击事件
        bianji_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(tv_bianji_num.getText().toString());
                int newNum = i + 1;
                tv_bianji_num.setText(newNum + "");

            }
        });

        //确定按钮
        cart_bianji_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("确认按钮被点击了吗--------","-----------");
                int newNum = Integer.parseInt(tv_bianji_num.getText().toString());
                cartBean.setProdNum(newNum);

                if(fragment != null) {
                    String cartid = "";
                    for (int i = 0; i < cartBeans.size(); i++) {
                        CartInfo.CartBean cartBean = cartBeans.get(i);
                        //商品数量
                        int num = cartBean.getProdNum();
                        CartInfo.CartBean.ProductBean product1 = cartBean.getProduct();
                        //商品Id
                        int id1 = product1.getId();

                        if(i == 0) {
                            cartid += id1 + ":" + num + ":";
                        }else {
                            cartid += "|" +  id1 + ":" + num + ":";
                        }
                        //商品属性Id
                        List<CartInfo.CartBean.ProductBean.ProductPropertyBean> productProperty1 = product1.getProductProperty();
                        for (int j = 0; j < productProperty1.size(); j++) {
                            CartInfo.CartBean.ProductBean.ProductPropertyBean productPropertyBean = productProperty1.get(j);
                            int productPropertyId = productPropertyBean.getId();
                            if(j != productProperty1.size() - 1) {
                                cartid += productPropertyId + ",";
                            }else {
                                cartid += productPropertyId;
                            }

                        }

                    }
                    LogUtil.e("-------存储的商品ID---------",cartid);
                    PrefUtils.putString(RedBabyApplication.context,"cartId",cartid);
                    fragment.initData();
                    fragment.onEdit();
                    dismiss();
                }
            }
        });
    }

    private BaseAnimatorSet mWindowInAs;
    private BaseAnimatorSet mWindowOutAs;

    @Override
    protected BaseAnimatorSet getWindowInAs() {
        if (mWindowInAs == null) {
            mWindowInAs = new WindowsInAs();
        }
        return mWindowInAs;
    }

    @Override
    protected BaseAnimatorSet getWindowOutAs() {
        if (mWindowOutAs == null) {
            mWindowOutAs = new WindowsOutAs();
        }
        return mWindowOutAs;
    }

    class WindowsInAs extends BaseAnimatorSet {
        @Override
        public void setAnimation(View view) {
            ObjectAnimator rotationX = ObjectAnimator.ofFloat(view, "rotationX", 10, 0f).setDuration(150);
            rotationX.setStartDelay(200);
            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.8f).setDuration(350),
                    ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.8f).setDuration(350),
//                    ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.5f).setDuration(350),
                    ObjectAnimator.ofFloat(view, "rotationX", 0f, 10).setDuration(200),
                    rotationX,
                    ObjectAnimator.ofFloat(view, "translationY", 0, -0.1f * mDisplayMetrics.heightPixels).setDuration(350)
            );
        }
    }

    class WindowsOutAs extends BaseAnimatorSet {
        @Override
        public void setAnimation(View view) {
            ObjectAnimator rotationX = ObjectAnimator.ofFloat(view, "rotationX", 10, 0f).setDuration(150);
            rotationX.setStartDelay(200);
            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1.0f).setDuration(350),
                    ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1.0f).setDuration(350),
//                    ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.5f).setDuration(350),
                    ObjectAnimator.ofFloat(view, "rotationX", 0f, 10).setDuration(200),
                    rotationX,
                    ObjectAnimator.ofFloat(view, "translationY", -0.1f * mDisplayMetrics.heightPixels, 0).setDuration(350)
            );
        }
    }

    /**
     * 设置数据
     * @param cartBean
     */
    public void setCartBean(CartInfo.CartBean cartBean) {
        this.cartBean = cartBean;
    }

    /**
     * 设置Fragment
     * @param fragment
     */
    private CartFragment fragment;
    public void setFragment (CartFragment fragment) {
        this.fragment = fragment;
    }
    public void setCartBeans(ArrayList<CartInfo.CartBean> cartBeans) {
        this.cartBeans = cartBeans;
    }



}
