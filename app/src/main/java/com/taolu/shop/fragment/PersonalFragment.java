package com.taolu.shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideTopExit;
import com.taolu.shop.R;
import com.taolu.shop.activity.AddrManagerActivity;
import com.taolu.shop.activity.Commodityorder;
import com.taolu.shop.activity.LoginActivity;
import com.taolu.shop.activity.MainActivity;
import com.taolu.shop.activity.PersonSettingActivity;
import com.taolu.shop.activity.ProductCollectActivity;
import com.taolu.shop.activity.UserManagerActivity;
import com.taolu.shop.bean.AccountInfo;
import com.taolu.shop.bean.RegisterErrorInfo;
import com.taolu.shop.global.Api;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.PrefUtils;
import com.taolu.shop.view.HelpAndVersion;
import com.taolu.shop.view.droggridview.LogUtil;
import com.taolu.shop.view.droggridview.MyAdapter;
import com.taolu.shop.view.droggridview.depend.DragCallback;
import com.taolu.shop.view.droggridview.depend.DragGridView;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment implements View.OnClickListener, MainActivity.OnRestartChangeListener, HttpLoader.HttpListener {


    private LinearLayout mLl_person_login;//整个用户信息的头布局,点击根据情况,如果用户没有登录,则进入登录界面,登录直接return

    private TextView mTv_name;//显示用户的名称,默认显示:登录/注册

    private String mUsername;

    private TextView tv_person_setting; //设置按钮

    private ImageView mIcon;//用户图标

    private MainActivity mActivity;

    private TextView mOrder;//显示用户的订单数量

    private TextView shoucang;//显示用户的收藏数量

    private ImageButton ibt_person_version_help;
    private FrameLayout fl_person_myOrder;  //我的订单
    private FrameLayout fl_person_addrs_manager;  //地址管理
    /**
     * 显示积分的布局默认隐藏,登录成功以后,设置积分,显示积分界面
     */
    private LinearLayout mLlScore;//积分的布局
    private TextView mTvScore;//显示积分
    private FrameLayout fl_person_product_collect;//收藏页面

    private DragGridView mGridView;
    private List<Integer> datas=new ArrayList<>();
    private MyAdapter adapter;

    public PersonalFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        initViews(view);
        mActivity = (MainActivity) getActivity();
        mActivity.addOnRestartChangeListener(this);
        return view;

    }

    /**
     * 界面的初始化
     *
     * @param view
     */
    private void initViews(View view) {
        //收藏页面
        fl_person_product_collect = (FrameLayout) view.findViewById(R.id.fl_person_product_collect);
        //help和更新版本
        ibt_person_version_help = (ImageButton) view.findViewById(R.id.ibt_person_version_help);
        //登录
        mLl_person_login = (LinearLayout) view.findViewById(R.id.ll_person_login);
        //我的订单
        fl_person_myOrder = (FrameLayout) view.findViewById(R.id.fl_person_myOrder);
        //地址管理
        fl_person_addrs_manager = (FrameLayout) view.findViewById(R.id.fl_person_addrs_manager);

        //填充假数据的界面控件
        mGridView = (DragGridView) view.findViewById(R.id.gridview);

        //设置按钮
        tv_person_setting = (TextView) view.findViewById(R.id.tv_person_setting);

        //用户的昵称,默认显示 登录/注册
        mTv_name = (TextView) view.findViewById(R.id.tv_person_name);

        //积分的控件查找
        mLlScore = (LinearLayout) view.findViewById(R.id.ll_person_jifen);
        mTvScore = (TextView) view.findViewById(R.id.tv_person_jifen);

        //用户图标
        mIcon = (ImageView) view.findViewById(R.id.iv_person_icon);
        //订单数量
        mOrder = (TextView) view.findViewById(R.id.tv_person_order);
        //收藏数量
        shoucang = (TextView) view.findViewById(R.id.tv_person_shoucang);

        adapter = new MyAdapter(RedBabyApplication.context);
        for(int i=0;i<16;i++)
        {
            datas.add(i);
        }
        mGridView.setAdapter(adapter);
        adapter.setDatas(datas);
        mGridView.setDragCallback(new DragCallback() {
             @Override
             public void startDrag(int position) {
                 LogUtil.i("start drag at " + position);

             }

             @Override
             public void endDrag(int position) {
                 LogUtil.i("end drag at " + position);
             }
         });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                mGridView.clicked(position);
            }
        });
        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                mGridView.startDrag(position);
                return false;
            }
        });

        //设置点击事件
        mLl_person_login.setOnClickListener(this);
        fl_person_myOrder.setOnClickListener(this);
        tv_person_setting.setOnClickListener(this);
        ibt_person_version_help.setOnClickListener(this);
        fl_person_addrs_manager.setOnClickListener(this);//地址管理
        fl_person_product_collect.setOnClickListener(this);//收藏页面
        //初始化数据
        initData();
    }

    /**
     * 确定跳转的界面
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_person_myOrder://我的订单被点击
                Intent intent = new Intent(RedBabyApplication.context, Commodityorder.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.tv_person_setting:  //进入设置界面
                Intent intent3 = new Intent(RedBabyApplication.context, PersonSettingActivity.class);
                intent3.putExtra("username", mUsername);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent3);
                break;
            case R.id.ll_person_login://进入登陆界面
                if (TextUtils.isEmpty(mUsername)) {
                    Intent intent1 = new Intent(RedBabyApplication.context, LoginActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                } else {
                    Intent intent2 = new Intent(RedBabyApplication.context, UserManagerActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                }
                break;
            case R.id.ibt_person_version_help: //进入更新和help界面的入口
                //弹出一个popupwindow
                HelpAndVersion popupwindow = new HelpAndVersion(getContext());
                popupwindow.anchorView(ibt_person_version_help)
                        .offset(-10, 5)
                        .gravity(Gravity.BOTTOM)
                        .showAnim(new BounceTopEnter())
                        .dismissAnim(new SlideTopExit())
                        .dimEnabled(false)
                        .show();
                break;
            case R.id.fl_person_addrs_manager://地址管理
                Intent intent4 = new Intent(RedBabyApplication.context, AddrManagerActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent4);

                break;

            case R.id.fl_person_product_collect://收藏页面
                Intent intent5 = new Intent(RedBabyApplication.context, ProductCollectActivity.class);
                intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent5);
                break;
        }


    }

    /**
     * 数据的初始化
     */
    public void initData() {
        String userId = PrefUtils.getString(RedBabyApplication.context, "userId", "");
        mUsername = PrefUtils.getString(RedBabyApplication.context, "userName", "");

        if (!TextUtils.isEmpty(userId)) {
            mIcon.setImageResource(R.drawable.login_touxiang90);
            mTv_name.setText(mUsername);
            mLlScore.setVisibility(View.VISIBLE);

            //请求数据
            HttpParams params = new HttpParams();
            params.addHeader("userid", userId);
            HttpLoader.getInstance(RedBabyApplication.context).setErrorClazz(RegisterErrorInfo.class);
            HttpLoader.getInstance(RedBabyApplication.context).post(Api.URL_ACCOUNT, params, AccountInfo.class,
                    Api.CODE_ACCOUNT, this);
        } else {
            mIcon.setImageResource(R.drawable.person_touxiang);
            mLlScore.setVisibility(View.INVISIBLE);
            mTv_name.setText("登录/注册");
            mOrder.setVisibility(View.INVISIBLE);
            shoucang.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 当请求数据成功
     *
     * @param requestCode response对应的requestCode
     * @param response    返回的response
     */
    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {


        if (response != null && response instanceof AccountInfo) {
            AccountInfo accountInfo = (AccountInfo) response;
            LogUtil.e("------个人中心数据请求------", "--------Success----------");
            //会员积分
            int bonus = accountInfo.getUserInfo().getBonus();
            mTvScore.setText("" + bonus);
            //订单数量
            int orderCount = accountInfo.getUserInfo().getOrderCount();
            //收藏总数
            int favoritesCount = accountInfo.getUserInfo().getFavoritesCount();

            if (orderCount > 0) {
                mOrder.setVisibility(View.VISIBLE);
                mOrder.setText("" + orderCount);
            }

            if (favoritesCount > 0) {
                shoucang.setVisibility(View.VISIBLE);
                shoucang.setText("" + favoritesCount);
            }
        }
    }

    /**
     * 当请求数据失败
     *
     * @param requestCode 请求码
     * @param error       异常详情
     */
    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }
    /**
     * 当MainActivity重新可见的时候
     */
    @Override
    public void onMianUiRestart() {
        //LogUtil.e("----------个人中心------------","MainActivity----OnRestart");
        initData();
    }
}
