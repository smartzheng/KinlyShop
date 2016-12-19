package com.taolu.shop.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.taolu.shop.R;
import com.taolu.shop.adapter.MainFragmentPagerAdapter;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup mRgMenu;
    private MainFragmentPagerAdapter mAdapter;
    public RadioButton rbSearch;
    private ViewPager mViewPager;
    public View mView;
    public RadioButton rb_cart;
    private RadioButton mHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = View.inflate(this, R.layout.activity_main, null);
        setContentView(mView);

        initViews();

    }

    /**
     * 界面初始化
     */
    private void initViews() {
        mRgMenu = (RadioGroup) findViewById(R.id.rg_menu);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        rbSearch = (RadioButton) findViewById(R.id.rb_search);
        rb_cart = (RadioButton) findViewById(R.id.rb_cart);
        mHome = (RadioButton) findViewById(R.id.rb_home);


        FragmentManager fm = getSupportFragmentManager();
        Log.e("Fragment-------------", fm + "");
        mAdapter = new MainFragmentPagerAdapter(fm);
        mViewPager.setAdapter(mAdapter);
        RedBabyApplication.mViewPager = this.mViewPager;
        mRgMenu.setOnCheckedChangeListener(this);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtil.e("--------当前被选中的position-------", position + "");
                if (position == 1) {
                    rbSearch.setChecked(true);
                } else if (position == 3) {
                    rb_cart.setChecked(true);
                } else if (position == 0) {
                    mHome.setChecked(true);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home://首页
                mViewPager.setCurrentItem(0, false);
                break;

            case R.id.rb_search://搜索
                mViewPager.setCurrentItem(1, false);
                break;

            case R.id.rb_brand://品牌
                mViewPager.setCurrentItem(2, false);
                break;

            case R.id.rb_cart://购物车
                mViewPager.setCurrentItem(3, false);
                break;

            case R.id.rb_me://个人中心
                mViewPager.setCurrentItem(4, false);
                break;
        }
    }

    /**
     * 界面重新可见
     */
    @Override
    protected void onRestart() {
        super.onRestart();

        if (listeners != null && listeners.size() > 0) {
            for (OnRestartChangeListener listener : listeners) {
                listener.onMianUiRestart();
            }
        }
    }

    private ArrayList<OnRestartChangeListener> listeners = new ArrayList<>();

    public interface OnRestartChangeListener {
        void onMianUiRestart();
    }

    public void addOnRestartChangeListener(OnRestartChangeListener listener) {
        listeners.add(listener);
    }

    //程序关闭间隔时间
    private final int EXIT_TIME = 2000;
    //上次按下的时间
    private long downTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - downTime > EXIT_TIME) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            } else {
                finish();
                System.exit(0);
            }

            downTime = currentTime;
        }

        return true;
    }

}
