package com.taolu.shop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.taolu.shop.R;
import com.taolu.shop.activity.MainActivity;
import com.taolu.shop.activity.PayMentActivity;
import com.taolu.shop.activity.SearchShowActivity;
import com.taolu.shop.bean.CartInfo;
import com.taolu.shop.global.CartApi;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;
import com.taolu.shop.utils.PrefUtils;
import com.taolu.shop.view.MyBottomDialog;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements View.OnClickListener,HttpLoader.HttpListener,MainActivity.OnRestartChangeListener{
    private boolean isEdit = false;

    private TextView cart_title_number;
    private ListView mListView;
    private ImageView cart_title_bianji;
    private RelativeLayout cart_nocommodity;
    private LinearLayout cartCard;//优惠信息
    private TextView tvPay,mTvDelete;
    private TextView tvTotalPrice,tv_hj;
    private ImageView checkAll;
    private boolean isAllCheck = false;//是否全部选中
    private View footView;//脚布局
    private LinearLayout ll_privilege_container;//优惠信息

    private CartInfo cartInfo;//购物车信息
    private ArrayList<CartInfo.CartBean> cartBeans;//购物车商品信息
    private Button reStart;
    private TextView tvNoOrError;
    private ImageView ivNoOrError;
    private MyAdapter mAdapter;
    private TextView tv_cart_pay;

    private boolean isLoading;//是否再试一次

    private RelativeLayout loading;//加载中的布局
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*TextView tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText(this.getClass().getSimpleName());
        tv.setTextSize(30);
        return tv;*/
      View view =   inflater.inflate(R.layout.fragment_cart, container, false);
        MainActivity mainUi = (MainActivity) getActivity();
        mainUi.addOnRestartChangeListener(this);
        initViews(view);
      return view;
    }


    @Override
    public void onMianUiRestart() {
        LogUtil.e("---------购物车加载数据---------","-------------");
        initData();
    }

    /**
     * 界面初始化
     */
    private void initViews(View view) {

        mListView = (ListView) view.findViewById(R.id.cart_listview);

        cart_nocommodity = (RelativeLayout) view.findViewById(R.id.cart_nocommodity);

        //购物车中没有商品,或者联网失败显示的界面
        reStart = (Button) view.findViewById(R.id.bt_reStart);
        tvNoOrError = (TextView) view.findViewById(R.id.tv_no_error);
        ivNoOrError = (ImageView) view.findViewById(R.id.iv_no_error);

        tvPay = (TextView) view.findViewById(R.id.tv_cart_pay);
        mTvDelete = (TextView) view.findViewById(R.id.tv_cart_delete);
        cart_title_bianji = (ImageView) view.findViewById(R.id.cart_title_bianji);
        cartCard = (LinearLayout) view.findViewById(R.id.cart_card);
        cart_title_number = (TextView) view.findViewById(R.id.cart_title_number);
        tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
        checkAll = (ImageView) view.findViewById(R.id.cart_check);

        loading = (RelativeLayout) view.findViewById(R.id.rl_loading);
        tv_hj = (TextView) view.findViewById(R.id.tv_hj);

        //显示支付的数量
        tv_cart_pay = (TextView) view.findViewById(R.id.tv_cart_pay);

        //添加MainActivity OnRestart监听
        MainActivity mainUi = (MainActivity) getActivity();
        mainUi.addOnRestartChangeListener(this);

        //cart_nocommodity.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.INVISIBLE);
        //return view;
        initFootView();
        cart_title_bianji.setOnClickListener(this);

        //支付按钮点击事件
        tvPay.setOnClickListener(this);
        //删除按钮点击事件
        mTvDelete.setOnClickListener(this);

        checkAll.setOnClickListener(this);

        reStart.setOnClickListener(this);


        //加载数据
        initData();
    }

    /**
     * 初始化脚布局
     */
    private  void initFootView() {
        footView = View.inflate(RedBabyApplication.context,R.layout.item_cart_card,null);
        ll_privilege_container = (LinearLayout) footView.findViewById(R.id.ll_privilege_container);
        mListView.addFooterView(footView);
    }

    /**
     * 界面点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_title_bianji://编辑
                if(cartBeans == null || cartBeans.size() == 0) {
                    Toast.makeText(getActivity(), "购物车中还没有商品快去选购吧!", Toast.LENGTH_SHORT).show();
                    return;
                }
                isEdit = !isEdit;
                if(isEdit) {
                    cart_title_bianji.setImageResource(R.drawable.cart_edit_ok);
                    tvPay.setVisibility(View.GONE);
                    mTvDelete.setVisibility(View.VISIBLE);
                    tv_hj.setVisibility(View.INVISIBLE);
                    tvTotalPrice.setVisibility(View.INVISIBLE);
                    footView.setVisibility(View.INVISIBLE);


                }else {
                    cart_title_bianji.setImageResource(R.drawable.cart_edit);
                    tvPay.setVisibility(View.VISIBLE);
                    mTvDelete.setVisibility(View.GONE);
                    tv_hj.setVisibility(View.VISIBLE);
                    tvTotalPrice.setVisibility(View.VISIBLE);
                    footView.setVisibility(View.VISIBLE);
                    tv_cart_pay.setText("结算(0)");
                    //点击完成,从新保存Id
                    savaCartId(false);

                }
                cancelAllCheck();
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.cart_check ://全选,反选
                if(cartBeans == null || cartBeans.size() <=0) {
                    return;
                }
                int totalPrice = 0;
                if(isAllCheck) {//如果全部没有选中,则设置为全部未选中
                    isAllCheck = false;
                    tv_cart_pay.setText("结算(0)");
                    for (CartInfo.CartBean cartBean : cartBeans) {
                        CartInfo.CartBean.ProductBean product = cartBean.getProduct();
                        product.setChecked(false);
                    }
                    checkAll.setImageResource(R.drawable.check_box_normal);
                    tvTotalPrice.setText("￥" + 0);
                }else {//
                    isAllCheck = true;
                    tv_cart_pay.setText("结算(" + cartBeans.size() + ")");
                    for (CartInfo.CartBean cartBean : cartBeans) {
                        CartInfo.CartBean.ProductBean product = cartBean.getProduct();
                        product.setChecked(true);
                        totalPrice += product.getPrice() * cartBean.getProdNum();
                    }
                    checkAll.setImageResource(R.drawable.check_box_pressed);
                    tvTotalPrice.setText("￥" + totalPrice);
                }

                //通知更新
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.tv_cart_delete://删除

                Iterator<CartInfo.CartBean> iterator = cartBeans.iterator();

                while (iterator.hasNext()) {
                    CartInfo.CartBean cartBean = iterator.next();
                    if(cartBean.getProduct().isChecked()) {
                        iterator.remove();
                    }
                }
                //点击完成,从新保存Id
                savaCartId(false);
                onEdit();
                break;

            case R.id.tv_cart_pay://支付
                LogUtil.e("--------AAAAAAAAAAA-------","结算被点击了");
                if(cartBeans == null || cartBeans.size() == 0) {
                    Toast.makeText(getActivity(), "购物车中还没有商品快去选购吧!", Toast.LENGTH_SHORT).show();
                    return;
                }
                pay();
                break;

            case  R.id.bt_reStart://再试一次
                cart_nocommodity.setVisibility(View.INVISIBLE);
                if(isLoading) {
                    initData();
                }else {
                    Intent intent = new Intent(getActivity(),SearchShowActivity.class);
                    startActivity(intent);
                }

                break;

        }
    }

    /**
     * 支付
     */
    private void pay() {
        savePayId();
        Intent intent = new Intent(RedBabyApplication.context, PayMentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        RedBabyApplication.context.startActivity(intent);
    }

    /**
     * 当删除之后的操作
     */
    public void onEdit() {
        mAdapter.notifyDataSetChanged();

        //设置图片
        checkAll.setImageResource(R.drawable.check_box_normal);
        isAllCheck = false;
        mTvDelete.setVisibility(View.GONE);
        tvPay.setVisibility(View.VISIBLE);

        //设置合计和价格显示
        tv_hj.setVisibility(View.VISIBLE);
        tvTotalPrice.setVisibility(View.VISIBLE);
        tvTotalPrice.setText("0.00");

        //退出编辑状态
        isEdit = false;
        cart_title_bianji.setImageResource(R.drawable.cart_edit);


        //设置标题数量
        cart_title_number.setText("购物车" + "(" + cartBeans.size() + ")");

        //当集合中没有数据
        if(cartBeans.size() == 0 ) {
            cart_nocommodity.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
            ll_privilege_container.removeAllViews();
            cart_title_number.setText("购物车");
        }

    }

    /**
     * 清除所有选中信息,通知界面更新
     */
    private void cancelAllCheck() {
        if(cartBeans != null) {
            for (CartInfo.CartBean cartBean : cartBeans) {
                CartInfo.CartBean.ProductBean product = cartBean.getProduct();
                product.setChecked(false);
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化数据
     */
    public void initData() {
        loading.setVisibility(View.VISIBLE);
        HttpParams params = new HttpParams();
        params.put("sku",getCartId());
        HttpLoader.getInstance(RedBabyApplication.context).post(CartApi.URL_CART,params,CartInfo.class,
        CartApi.CART_CODE,this);

    }

    /**
     * 获取购物车Id
     * @return
     */
    private String getCartId() {
        String oldCartId = PrefUtils.getString(RedBabyApplication.context,"cartId","");
        LogUtil.e("------------老的购物车ID---------",oldCartId);
        String paySuccessId = PrefUtils.getString(RedBabyApplication.context,"paySuccessId","");
        LogUtil.e("------------支付成功的ID---------",paySuccessId);

        //将老的购物车转换成集合
        String[] oldCartIds = oldCartId.split("\\|");
        ArrayList<String> oldCartList = new ArrayList<>();
        for (int i = 0; i < oldCartIds.length; i++) {
            oldCartList.add(oldCartIds[i]);
        }
        LogUtil.e("------购物车集合------",oldCartList.toString());

        //将支付成功的Id进行遍历
        String[] paySuccessIds = paySuccessId.split("\\|");

        for (int i = 0; i < paySuccessIds.length; i++) {
            if(oldCartList.contains(paySuccessIds[i])) {
                oldCartList.remove(paySuccessIds[i]);
            }
        }

        String cartId = "";
        //将集合重新遍历组合新的购物车Id;
        for (int i = 0; i < oldCartList.size(); i++) {
            cartId += oldCartList.get(i);
            if(i != oldCartList.size() - 1) {
                cartId += "|";
            }
        }

        //将支付成功的Id清空
        PrefUtils.putString(RedBabyApplication.context,"paySuccessId","");
        //存储新的购物车Id
        PrefUtils.putString(RedBabyApplication.context,"cartId",cartId);


        LogUtil.e("------------新的购物车ID---------",cartId);
        return cartId;
    }

    /**
     * 当数据请求成功
     * @param requestCode response对应的requestCode
     * @param response    返回的response
     */
    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        loading.setVisibility(View.INVISIBLE);
        LogUtil.e("AAAAA", "------");
        if(requestCode == 1) {
            cartInfo = (CartInfo) response;
            cartBeans = (ArrayList<CartInfo.CartBean>) cartInfo.getCart();
            LogUtil.e("cartbeans的长度:",cartBeans.size() + "");

            if(cartBeans != null &&cartBeans.size() > 0) {
                cart_nocommodity.setVisibility(View.INVISIBLE);
                mListView.setVisibility(View.VISIBLE);
                cart_title_number.setText("购物车" + "(" +cartBeans.size() + ")");
                if (mAdapter == null) {
                    mAdapter = new MyAdapter();
                    mListView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
                ll_privilege_container.removeAllViews();
                //将优惠信息用叫布局的形式添加
                List<String> prom = cartInfo.getProm();
                if(prom!= null && prom.size() > 0) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            80);

                    for (int i = 0; i < prom.size(); i++) {
                        View view = View.inflate(RedBabyApplication.context,R.layout.item_cart_privilege,null);
                        TextView tv = (TextView) view.findViewById(R.id.tv_privilege);
                        tv.setText(prom.get(i));
                        view.setLayoutParams(params);
                        setAnima(view);
                        ll_privilege_container.addView(view);
                    }
                }

            }else {
                cart_nocommodity.setVisibility(View.VISIBLE);
                reStart.setText("购物车中还没有商品");
                tvNoOrError.setText("随便逛逛");
                isLoading = false;
                ivNoOrError.setImageResource(R.drawable.network_defeat);
                mListView.setVisibility(View.INVISIBLE);
            }

        }
    }

    /**
     * 当数据请求失败
     * @param requestCode 请求码
     * @param error       异常详情
     */
    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        loading.setVisibility(View.INVISIBLE);
        LogUtil.e("网络连接失败","---------------");

        cart_nocommodity.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(getCartId())) {
            reStart.setText("再试一次");
            tvNoOrError.setText("联网失败了");
            ivNoOrError.setImageResource(R.drawable.network_defeat);
            isLoading = true;
        }
        mListView.setVisibility(View.INVISIBLE);
    }

    private void setAnima(final View view){
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                // ViewPropertyAnimator
                ViewHelper.setTranslationX(view,view.getMeasuredWidth());
                ViewPropertyAnimator.animate(view)
                        .translationXBy(-1 * view.getMeasuredWidth())
                        .setInterpolator(new OvershootInterpolator())
                        .setDuration(500)
                        .setStartDelay(300)
                        .start();
            }
        });

    }


    private int currentChecked = 0;
    //private boolean isLoader = false;
    private boolean isAllCancel;

    private int checkedNum = 0;

    private class MyAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return cartBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return cartBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
           /* CartHolderView holderView ;*/
            final CartHolderView holderView;
            if(convertView == null) {

                view = View.inflate(RedBabyApplication.context,R.layout.item_cart_list,null);
                holderView =new CartHolderView();

                //LogUtil.e("AAAAA", "--- new Holder ---" + holderView.toString());
                holderView.llEdit = (LinearLayout) view.findViewById(R.id.ll_cart_bianji);
                holderView.llShow = (LinearLayout) view.findViewById(R.id.ll_cart_show);
                holderView.tvDesc = (TextView) view.findViewById(R.id.tv_cart_desc);
                holderView.tvMsg = (TextView) view.findViewById(R.id.tv_cart_msg);
                holderView.tvPrice = (TextView) view.findViewById(R.id.tv_cart_price);
                holderView.checkBox = (CheckBox) view.findViewById(R.id.cart_checkBox);
                holderView.ivIcon = (ImageView) view.findViewById(R.id.iv_cart_icon);
                holderView.tvSub = (TextView) view.findViewById(R.id.tv_cart_sub);
                holderView.tvNum = (TextView) view.findViewById(R.id.tv_cart_number);
                holderView.tvAdd = (TextView) view.findViewById(R.id.tv_cart_add);
                holderView.tvEditMsg = (TextView) view.findViewById(R.id.tv_cart_edit_msg);
                holderView.ibMore = (ImageButton) view.findViewById(R.id.ib_cart_more);


                view.setTag(holderView);
            }else {
                view = convertView;
                holderView = (CartHolderView) view.getTag();
               // LogUtil.e("AAAAA", "--- getTag ---" + holderView.toString());
            }

            //设置商品
            final CartInfo.CartBean cartBean = cartBeans.get(position);
            final CartInfo.CartBean.ProductBean product = cartBean.getProduct();

            //判断是否是编辑界面
            if(isEdit) {
                holderView.llShow.setVisibility(View.GONE);
                holderView.llEdit.setVisibility(View.VISIBLE);
            }else {
                holderView.llShow.setVisibility(View.VISIBLE);
                holderView.llEdit.setVisibility(View.GONE);

            }

            if(product.isLoader() == false) {
                // 设置图片
                holderView.ivIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                HttpLoader.getInstance(RedBabyApplication.context).display(holderView.ivIcon,
                        CartApi.URL_Service + product.getPic(),R.drawable.search_instance,R.drawable.search_instance,
                        0,0, ImageView.ScaleType.CENTER_CROP);
                product.setLoader(true);
               // LogUtil.e("AAAAAAAAA","---position--" + position + "----" + product.getPic() + "----holder---" + holderView);
           }


            //设置描述信息--显示界面
            //LogUtil.e("商品名称",product.toString());
            holderView.tvDesc.setText(product.getName());
            //商品属性--显示界面
            List<CartInfo.CartBean.ProductBean.ProductPropertyBean> productProperty = product.getProductProperty();
            String msgStr = "";
            for (int i = 0; i < productProperty.size(); i++) {
                msgStr += productProperty.get(i).getK() + ":" + productProperty.get(i).getV() +",";
            }
            msgStr += "数量:" + cartBean.getProdNum();
            holderView.tvMsg.setText(msgStr);
            //商品价格
            holderView.tvPrice.setText("￥: " + product.getPrice());

            //商品属性--编辑界面
            holderView.tvEditMsg.setText(msgStr);
            //商品数量--编辑界面
            holderView.tvNum.setText(cartBean.getProdNum() + "");
            //显示是否被勾选
            boolean checked = product.isChecked();
            holderView.checkBox.setChecked(checked);

            //点击事件注册
            holderView.tvSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.e("--------AAAAAAAAAAAA-------","减" + position);
                    String currentNumStr;
                    int currentNum;
                    int newNum;
                    currentNumStr= holderView.tvNum.getText().toString().trim();
                    currentNum = Integer.parseInt(currentNumStr);
                    if(currentNum <= 1) {
                        return ;
                    }
                    newNum = currentNum - 1;
                    cartBean.setProdNum(newNum);
                    LogUtil.e("holderView.tvNum:-------------",holderView.tvNum + "");
                    holderView.tvNum.setText(newNum + "");
                    mAdapter.notifyDataSetChanged();
                }
            });
            holderView.tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.e("--------AAAAAAAAAAAA-------","加" + position);
                    String currentNumStr;
                    int currentNum;
                    int newNum;
                    currentNumStr = holderView.tvNum.getText().toString().trim();
                    currentNum = Integer.parseInt(currentNumStr);

                    newNum = currentNum + 1;
                    cartBean.setProdNum(newNum);
                    holderView.tvNum.setText(newNum + "");
                    mAdapter.notifyDataSetChanged();

                }
            });


            //选中监听
            holderView.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        checkedNum ++;
                        tv_cart_pay.setText("结算(" + checkedNum + ")");
                    }else {
                        checkedNum --;
                        tv_cart_pay.setText("结算(" + checkedNum + ")");
                    }
                   // getChoiceData(cartBean,isChecked);
                    product.setChecked(isChecked);

                    int totalPrice = 0;
                    int currentChecked = 0;
                    for (CartInfo.CartBean bean : cartBeans) {

                        CartInfo.CartBean.ProductBean product1 = bean.getProduct();
                        boolean checked1 = product1.isChecked();

                        if(checked1) {
                            totalPrice += (bean.getProdNum() * product1.getPrice());
                            currentChecked ++;
                        }else {
                            currentChecked--;
                        }
                    }

                    if(currentChecked == cartBeans.size() && cartBeans.size() > 0) {
                        LogUtil.e("当前条目数------------",cartBeans.size() +"");
                        isAllCheck = true;
                        checkAll.setImageResource(R.drawable.check_box_pressed);
                    }else {
                        isAllCheck = false;
                        checkAll.setImageResource(R.drawable.check_box_normal);
                    }

                    tvTotalPrice.setText("￥" + totalPrice);
                }


            });

            //显示更多编辑界面
            holderView.ibMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyBottomDialog dialog = new MyBottomDialog(getActivity(),(View) getActivityView().getParent());
                    dialog.setCartBean(cartBean);
                    dialog.setFragment(CartFragment.this);
                    dialog.setCartBeans(cartBeans);
                    dialog.show();
                }
            });
            return view;
        }



    }

    public static class CartHolderView {
        LinearLayout llShow;//商品显示布局
        LinearLayout llEdit;//商品编辑布局

        CheckBox checkBox;//商品是否被选中
        ImageView ivIcon;//商品图片

        TextView tvDesc;//商品描述信息
        TextView tvMsg;//商品颜色,型号,数量
        TextView tvPrice;//商品价格

        TextView tvSub;//减去商品数量
        TextView tvNum;//商品数量
        TextView tvAdd;//增加商品数量
        TextView tvEditMsg;//商品编辑界面颜色,型号,数量
        ImageButton ibMore;//更多编辑信息
    }

    /**
     * 获取选中的条目
     * @param cartBean
     * @param isChoice
     */
    public void getChoiceData(CartInfo.CartBean cartBean, boolean isChoice) {
        /*totalPrice = 0;
        cartBean.getProduct().setChecked(isChoice);
        for (CartInfo.CartBean bean : cartBeans) {
            CartInfo.CartBean.ProductBean product = bean.getProduct();
            boolean checked = product.isChecked();
            if(checked) {
                totalPrice +=  product.getPrice() * cartBean.getProdNum();
            }
        }

        tvTotalPrice.setText("￥" + totalPrice);*/
    }

    /**
     * 获取Activity的View
     */
    public View getActivityView() {
        MainActivity mainUi = (MainActivity) getActivity();
        return mainUi.mView;

    }

    /**
     * 保存商品的ID
     */
    private void savaCartId(boolean isPay) {
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
        if(isPay) {
            PrefUtils.putString(RedBabyApplication.context,"payId",cartid);
        }else {
            PrefUtils.putString(RedBabyApplication.context,"cartId",cartid);
        }

    }

    /**
     * 保存支付Id
     */
    private void savePayId() {
        String payId = "";
        ArrayList<CartInfo.CartBean> payBean = new ArrayList<>();
        for (int i = 0; i < cartBeans.size(); i++) {
            CartInfo.CartBean cartBean = cartBeans.get(i);
            //商品数量
            CartInfo.CartBean.ProductBean product1 = cartBean.getProduct();
            if(product1.isChecked()) {
              payBean.add(cartBean);
            }
        }
        for (int i = 0; i < payBean.size(); i++) {
            CartInfo.CartBean cartBean = payBean.get(i);
            //商品数量
            int num = cartBean.getProdNum();
            CartInfo.CartBean.ProductBean product1 = cartBean.getProduct();
            //商品Id
            int id1 = product1.getId();

            if(i == 0) {
                payId += id1 + ":" + num + ":";
            }else {
                payId += "|" +  id1 + ":" + num + ":";
            }
            //商品属性Id
            List<CartInfo.CartBean.ProductBean.ProductPropertyBean> productProperty1 = product1.getProductProperty();
            for (int j = 0; j < productProperty1.size(); j++) {
                CartInfo.CartBean.ProductBean.ProductPropertyBean productPropertyBean = productProperty1.get(j);
                int productPropertyId = productPropertyBean.getId();
                if(j != productProperty1.size() - 1) {
                    payId += productPropertyId + ",";
                }else {
                    payId += productPropertyId;
                }

            }
        }
        PrefUtils.putString(RedBabyApplication.context,"payId",payId);
        LogUtil.e("-------支付Id-------",payId);
    }
}
