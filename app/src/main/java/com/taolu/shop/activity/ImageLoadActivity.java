package com.taolu.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.anim.AlphaAnimtor;
import com.taolu.shop.anim.PaddingBottomAnim;
import com.taolu.shop.anim.PaddingTopAnim;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;

import java.util.ArrayList;

public class ImageLoadActivity extends Activity {
    private LinearLayout mView;

    private RelativeLayout mUpView;

    private RelativeLayout mDownView;

    private ImageView ivUp;

    private TextView mTvUserName,commit,commit_time;

    private LinearLayout down_des;//描述部分

    private WindowManager.LayoutParams attributes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);

        //将窗体的颜色改变,使其和本Activity的颜色一致;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.image_theme));
        }
        attributes = window.getAttributes();

        //查找控件
        mView = (LinearLayout) findViewById(R.id.ll_view);
        mUpView = (RelativeLayout) findViewById(R.id.rl_up);
        mDownView = (RelativeLayout) findViewById(R.id.rl_down);
        ivUp = (ImageView) findViewById(R.id.iv_up);
        down_des = (LinearLayout) findViewById(R.id.down_des);
        mTvUserName = (TextView) findViewById(R.id.username);
        commit = (TextView) findViewById(R.id.commit);
        commit_time = (TextView) findViewById(R.id.commit_time);

        //注册一个View树的监听,获取各个部分的高度
        mUpView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mUpView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                upHeight = mUpView.getMeasuredHeight();
                downHeight = mDownView.getMeasuredHeight();
                desHeight = down_des.getMeasuredHeight();
            }
        });


        mView.getBackground().setAlpha(255);

        initData();
    }

    //上半部分的高度
    private float upHeight;
    //下半部分的高度
    private float downHeight;
    //秒速部分的高度
    private float desHeight;

    float downY = 0;
    float padding = 0;

    private final int STATE_CLOSE = 1;//应该合上
    private final int STATE_OPEN = 2;//应该打开

    private int current_state = STATE_CLOSE;

    float upY = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            //当手指按下的时候
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                break;

            //当手指移动的时候
            case MotionEvent.ACTION_MOVE:
                padding = Math.abs(event.getY() - downY);
                if(padding > desHeight / 2) {
                    current_state = STATE_OPEN;
                }else if(padding < desHeight / 2) {
                    current_state = STATE_CLOSE;
                }
                setPadding(padding);
                break;

            //当手指释放的时候
            case MotionEvent.ACTION_UP:
                upY = event.getY();
                onActionUp();
                break;
        }
        return super.onTouchEvent(event);
    }

    private int alphaValue;//当前的透明度

    private void setPadding(float move) {
        //设置透明度
        float alphaPercent = move * 1.0f / upHeight;
        float value = alphaPercent * 255;
        alphaValue = (int)(255 - value);

        if(downY != upY) {
            mView.getBackground().setAlpha(alphaValue);
        }


        mUpView.setPadding(mUpView.getPaddingLeft(), (int) -move,mUpView.getPaddingRight(),
                (int) move);
        mDownView.setPadding(mDownView.getPaddingLeft(),(int)move,mDownView.getPaddingRight(),
                (int)-move);

    }

    /**
     * 当手指释放的时候
     */
    private void onActionUp() {
        if(current_state == STATE_CLOSE) {
            startCloseAnim(mDownView,mUpView);
            startAlphaAnim(mView,false);
        }else  if(current_state == STATE_OPEN) {
            startOpenAnim(mUpView,mDownView);
            startAlphaAnim(mView,true);
        }
    }

    /**
     * 开始动画
     */
    public void startOpenAnim(View target1,View target2) {
        new PaddingBottomAnim(target1,target1.getPaddingBottom(), (int) upHeight).setOnAnimEndListener(new PaddingBottomAnim.onAnimEndListener() {
            @Override
            public void onAnimEnd() {
                ImageLoadActivity.this.finish();
                overridePendingTransition(-1,-1);
            }
        }).start(500);
        new PaddingTopAnim(target2,target2.getPaddingTop(), (int) downHeight).start(500);
    }

    public void startCloseAnim(View target1,View target2) {
        new PaddingBottomAnim(target1,target1.getPaddingBottom(), 0).start(500);
        new PaddingTopAnim(target2,target2.getPaddingTop(), 0).start(500);
    }

    public void startAlphaAnim(View target,boolean isOpen) {
        if(isOpen) {
            new AlphaAnimtor(target,alphaValue,0).start(500);
        }else {
            new AlphaAnimtor(target,alphaValue,255).start(500);
        }
    }

    /**
     * 当数据请求成功
     */
    private void initData() {
        Intent intent = getIntent();
        ArrayList<String> images = intent.getStringArrayListExtra("image");
        String productUserName = intent.getStringExtra("productUserName");
        String commitStr = intent.getStringExtra("commit");

        commit.setText(commitStr);
        mTvUserName.setText(productUserName);
        int imagePosition = intent.getIntExtra("imagePosition",0);
        HttpLoader.getInstance(RedBabyApplication.context).display(ivUp, HttpApi.URL_Service + images.get(imagePosition));
    }

}
