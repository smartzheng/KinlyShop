package com.taolu.shop.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.blog.www.guideview.Component;
import com.blog.www.guideview.Guide;
import com.blog.www.guideview.GuideBuilder;
import com.taolu.shop.R;
import com.taolu.shop.activity.HotActivity;
import com.taolu.shop.activity.MainActivity;
import com.taolu.shop.activity.PanicActivity;
import com.taolu.shop.activity.RecommendActivity;
import com.taolu.shop.activity.SalesActivity;
import com.taolu.shop.activity.XinpinActivity;
import com.taolu.shop.adapter.HomeAdapter;
import com.taolu.shop.adapter.VerticalViewPagerAdapter;
import com.taolu.shop.bean.HomeBean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.HttpCode;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.guid.MutiComponent;
import com.taolu.shop.guid.SimpleComponent;
import com.taolu.shop.utils.CommonUtil;
import com.taolu.shop.utils.PrefUtils;
import com.taolu.shop.view.VerticalViewPager;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    private GridView mGridView;
    private ImageView home_title_search;
    private MainActivity mMainActivity;
    Guide guide;
    private MainActivity mActivity;
    private LinearLayout mHome_lennerlayout;
    private VerticalViewPager mVerticalViewPager;
    private ArrayList<String> mMVerticalViewPagerlist;
    private TextView mHome_time_pager;
    private Timer mTimer;


    private void setViewPager(ViewPager viewPager) {
        this.mMViewPager = viewPager;
    }
    //时间
    private int timer = 60;
    private View mView;
    private ViewPager mMViewPager;
    private TextView mTv_home_time;
    private TextView mTv_home_sales;
    private TextView mTv_home_recommend;
    private TextView mTv_home_hot;
    private TextView mTv_home_xinpin;
    private int temppision = 0;
    private String[] smsMessages={
            "勤奋工作莫迟延。愿你年后心情好，事业更上一层楼！",
            "发条短信问个好，财源广进吉星照。万事顺利开怀笑，羊年幸福乐逍遥。",
            "心情预报：今夜到明早想你，预计下午转为很想你",
            "用心的人就会幸福;想你的脸，心里就温暖;想你的嘴，笑容跟着灿烂！",
            "祝愿老板身体好，越过越开心，财富滚滚来，生活越来越美好.",
            "月月事事顺心，日日喜悦无忧，时时高兴欢喜，刻刻充满朝气！",
            "勇攀高峰不怕险，开辟新径铸辉煌。排除万难创业路。艰苦奋斗能胜天，幸福生活如意添"
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
// mHandler.sendEmptyMessageDelayed(0,3000);
            mVerticalViewPager.setCurrentItem(mVerticalViewPager.getCurrentItem()+1);
            mMViewPager.setCurrentItem(mMViewPager.getCurrentItem() + 1);
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
    };
    private Handler mHandler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            timer--;
            mHome_time_pager.setText("11:11:"+timer);
            if(timer > 0){
                Message message = mHandler1.obtainMessage(1);
                mHandler1.sendMessageDelayed(message, 1500);      // send message
            }else{
                mHome_time_pager.setVisibility(View.GONE);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initData();


        return mView;
    }


    private void initView() {
        //实现竖向滚动条目
        mVerticalViewPager = (VerticalViewPager) mView.findViewById(R.id.verticalViewPager);
        //添加点
        mHome_lennerlayout = (LinearLayout) mView.findViewById(R.id.home_linerLayout);
        //实现时间的动态显示
        mHome_time_pager = (TextView) mView.findViewById(R.id.home_time_pager);
        mMViewPager = (ViewPager) mView.findViewById(R.id.home_viewpager);
        mTv_home_time = (TextView) mView.findViewById(R.id.tv_home_time);
        mTv_home_sales = (TextView) mView.findViewById(R.id.tv_home_sales);
        mTv_home_recommend = (TextView) mView.findViewById(R.id.tv_home_recommend);
        mTv_home_hot = (TextView) mView.findViewById(R.id.tv_home_hot);
        mTv_home_xinpin = (TextView) mView.findViewById(R.id.tv_home_xinpin);
        // mGridView = (GridView) mView.findViewById(R.id.home_gridview);
        home_title_search = (ImageView) mView.findViewById(R.id.home_title_search);
        mActivity = (MainActivity) getActivity();
        mTv_home_recommend.setOnClickListener(this);
        mTv_home_time.setOnClickListener(this);
        mTv_home_sales.setOnClickListener(this);
        mTv_home_hot.setOnClickListener(this);
        mTv_home_xinpin.setOnClickListener(this);
        //点击跳转
        home_title_search.setOnClickListener(this);
        /**
         *
         */

        createWindow();
    }

    public void createWindow() {
        getActivity().getWindow()
                .getDecorView()
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            getActivity().getWindow()
                                    .getDecorView()
                                    .getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        } else {
                            getActivity().getWindow()
                                    .getDecorView()
                                    .getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                        String isBB = PrefUtils.getString(RedBabyApplication.context, "isBB", "");
                        if (TextUtils.isEmpty(isBB)) {
                            showGuideView();
                        }

                    }
                });
    }

    public void showGuideView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(mTv_home_hot)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetGraphStyle(Component.CIRCLE)

                .setHighTargetPadding(0)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
                showGuideView2();
            }
        });

        builder.addComponent(new MutiComponent());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(getActivity());
    }

    public void showGuideView2() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(mTv_home_sales)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(5)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {

            }

            @Override
            public void onDismiss() {
                showGuideView3();
            }
        });

        builder.addComponent(new MutiComponent());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(getActivity());
    }


    public void showGuideView3() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(mActivity.rbSearch)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10)
                .setHighTargetGraphStyle(Component.CIRCLE)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
                PrefUtils.putString(RedBabyApplication.context, "isBB", "4");
            }

            @Override
            public void onDismiss() {


            }
        });

        builder.addComponent(new SimpleComponent());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(getActivity());
    }


    /**
     * 链接网络获取数据
     */
    private void initData() {
        //添加点的轮播
        for (int i = 0; i < 5; i++) {
            View v = new View(RedBabyApplication.context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15,15);
            v.setLayoutParams(params);
            v.setBackgroundResource(R.drawable.point_select);
            if(i!=0){
                params.leftMargin = 5;
            }
            mHome_lennerlayout.addView(v);
        }
        mHome_lennerlayout.getChildAt(temppision).setEnabled(false);
        mMViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mHome_lennerlayout.getChildAt(temppision%5).setEnabled(true);
                mHome_lennerlayout.getChildAt(position%5).setEnabled(false);
                temppision = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //添加竖直滚动条目
        mMVerticalViewPagerlist = new ArrayList<>();
        for (int i = 0; i < smsMessages.length; i++) {
            mMVerticalViewPagerlist.add( smsMessages[i]);
        }
        //mVerticalViewPager
        CommonUtil.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                mVerticalViewPager.setAdapter(new VerticalViewPagerAdapter(mMVerticalViewPagerlist));
                mVerticalViewPager.setCurrentItem(2);
            }
        });

        HttpLoader.getInstance(RedBabyApplication.context).get(HttpApi.URL_HOME, null, HomeBean.class, HttpCode.CODE_HOME, new HomeHttoLoader(), true);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_home_time://限时抢购
                /**
                 * 限时抢购
                 */
                intent = new Intent(RedBabyApplication.context, PanicActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_home_sales://促销快报
                intent = new Intent(RedBabyApplication.context, SalesActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_home_xinpin://新品上架
                intent = new Intent(RedBabyApplication.context, XinpinActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_home_hot://热门单品
                intent = new Intent(RedBabyApplication.context, HotActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_home_recommend://推荐品牌
                intent = new Intent(RedBabyApplication.context, RecommendActivity.class);
                startActivity(intent);
                break;
            case R.id.home_title_search:

                if (RedBabyApplication.mViewPager != null) {
                    RedBabyApplication.mViewPager.setCurrentItem(1);

                }
                break;

        }
    }

    /**
     * Listener Home
     */
    class HomeHttoLoader implements HttpLoader.HttpListener {

        private List<HomeBean.HomeTopicBean> mHomeTopic;

        @Override
        public void onGetResponseSuccess(int requestCode, IResponse response) {
            if(response!=null){
                HomeBean home = (HomeBean) response;
                mHomeTopic = home.getHomeTopic();

                final HomeAdapter homeAdapter = new HomeAdapter((ArrayList) mHomeTopic);
                CommonUtil.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        //这只Adapter,循环轮播
                        mMViewPager.setAdapter(homeAdapter);
//                    mGridView.setAdapter(new HomeGridViewAdapter());
                        mMViewPager.setCurrentItem(1000);
                    }
                });
            }


        }

        @Override
        public void onGetResponseError(int requestCode, VolleyError error) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mHandler.sendEmptyMessageDelayed(0, 3000);
        mHandler1.sendEmptyMessageDelayed(0,1500);
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
        mHandler1.removeCallbacksAndMessages(null);

    }
}


