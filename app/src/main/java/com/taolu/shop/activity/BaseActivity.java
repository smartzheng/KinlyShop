package com.taolu.shop.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.taolu.shop.R;
import com.taolu.shop.adapter.BaseActivityAdapter;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.CommonUtil;

import java.util.ArrayList;


public abstract class BaseActivity extends AppCompatActivity {
    protected PullToRefreshListView mPullRefreshListView;
    protected ListView mListView;
    protected TextView mTextView;
    protected ImageView mImageView;
    protected ImageView mJump;
    protected RadioGroup mRg_tab_menu;
    private int temp = 0;//记录点
    private View mPager;
    private ArrayList<ImageView> mList;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // mHandler.sendEmptyMessageDelayed(0,3000);
            mIb.setCurrentItem(mIb.getCurrentItem() + 1);
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
    };
    private BaseActivityAdapter mAdapter;
    private ViewPager mIb;
    private LinearLayout mBase_linearlayout;
    private int[] mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initView();
        initData();
    }

    /**
     * 下载数据
     */
    public void initData() {

        //请求数据
        getDada();
        //  刷新
        //设置初始化模式
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新的逻辑 上啦加载
                if (mPullRefreshListView.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_END) {
                    getDada();

                } else {//下拉刷新
                    getDada();

                }
            }
        });
        //获取内部的ListView
        mListView = mPullRefreshListView.getRefreshableView();
        //添加轮播大图
        mPager = View.inflate(BaseActivity.this, R.layout.head_view, null);
        mIb = (ViewPager) mPager.findViewById(R.id.imageview);
        mBase_linearlayout = (LinearLayout) mPager.findViewById(R.id.base_linearlayout);
        setItmeListener();
        setViewPager();


        //设置监听
        mIb.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mBase_linearlayout.getChildAt(position % 5).setEnabled(false);
                mBase_linearlayout.getChildAt(temp % 5).setEnabled(true);
                //记录前一个索引变量
                temp = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mAdapter = new BaseActivityAdapter(mList);
        CommonUtil.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                //这只Adapter,循环轮播
                mIb.setAdapter(mAdapter);
                mBase_linearlayout.getChildAt(temp).setEnabled(true);
                mIb.setCurrentItem(Integer.MAX_VALUE%mList.size()-1);

            }
        });
        mIb.setAdapter(mAdapter);
        mListView.addHeaderView(mPager);
    }

    private void setViewPager() {
        mList = new ArrayList<>();
        int[] mImages = {R.drawable.home_page1, R.drawable.home_page2, R.drawable.home_page3, R.drawable.home_page4, R.drawable.home_page5,};
        for (int i = 0; i < mImages.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(mImages[i]);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            View view = new View(this);
            view.setBackgroundResource(R.drawable.point_select);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            if (i != 0) {
                params.leftMargin = 8;
            }
            view.setLayoutParams(params);
            mBase_linearlayout.addView(view);
            mList.add(iv);
        }
    }

    /**
     * 初始化FindById
     */
    public void initView() {
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        mImageView = (ImageView) findViewById(R.id.home_title_hot);
        mJump = (ImageView) findViewById(R.id.home_title_jump);
        mTextView = (TextView) findViewById(R.id.tv_base);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RedBabyApplication.mViewPager != null) {
                    RedBabyApplication.mViewPager.setCurrentItem(1);
                    finish();
                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);

    }

    /**
     * 服务器请求数据
     */
    public abstract void getDada();

    public void setItmeListener() {
    }

}
