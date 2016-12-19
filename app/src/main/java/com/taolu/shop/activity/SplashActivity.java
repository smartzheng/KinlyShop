package com.taolu.shop.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.taolu.shop.R;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.PrefUtils;

public class SplashActivity extends AppCompatActivity {

    private ImageView start;
    private ImageView shou;

    private ImageView imageview1, imageview2, imageview3;

    private String[] strs = {"六月", "我们来到黑马", "那时候的我们"};

    private boolean isFrist = true;//是否首次启动,默认True;

    private final int SHOW_ANIM = 1;

    private final int START_INTENT = 2;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_INTENT:
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(-1, -1);
                    finish();
                    break;

                case SET_OK:
                    start.setImageResource(R.drawable.start_ok2);
                    imageview1.setVisibility(View.INVISIBLE);
                    imageview2.setVisibility(View.INVISIBLE);
                    imageview3.setVisibility(View.INVISIBLE);
                    delay();
                    break;
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //动画起始点
        getHeight();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        start = (ImageView) findViewById(R.id.start);
        shou = (ImageView) findViewById(R.id.shou);


        imageview1 = (ImageView) findViewById(R.id.imageview1);
        imageview2 = (ImageView) findViewById(R.id.imageview2);
        imageview3 = (ImageView) findViewById(R.id.imageview3);

        isFrist = PrefUtils.getBoolean(RedBabyApplication.context, "firstRoot", true);

    }

    private void delay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(500);
                Message msg = new Message();
                msg.what = START_INTENT;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private float startHeight;
    private float shouHeight;
    private float moveHight;
    private float heightP;

    /**
     * 获取高度
     */
    private void getHeight() {
        start.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                start.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                startHeight = start.getY();
                shouHeight = shou.getY();
                moveHight = shouHeight - startHeight - (start.getMeasuredHeight());
                heightP = moveHight * 1f / startHeight;
                Log.e("------移动比例-----", "" + heightP);

                //当获得确切的高度,比例的时候,就开始执行动画
                startAnim();
            }
        });
    }

    private void startAnim() {
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, -heightP);
        ta.setDuration(2000);
        ta.setRepeatCount(0);
        shou.startAnimation(ta);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageview1.startAnimation(getAnimation(1));
                imageview2.startAnimation(getAnimation(2));
                imageview3.startAnimation(getAnimation(3));

                setOk();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private final int SET_OK = 3;
    private void setOk() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1500);
                Message msg = new Message();
                msg.what = SET_OK;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void showAnimation() {
        handler.sendEmptyMessageDelayed(SHOW_ANIM, ANIMATIONEACHOFFSET);
    }

    private final int ANIMATIONEACHOFFSET = 600;

    /**
     * 返回动画
     */
    private AnimationSet getAnimation(int num) {
        AnimationSet as = new AnimationSet(true);
        ScaleAnimation sa = null;
        if (num == 1) {
            sa = new ScaleAnimation(1f, 1.5f, 1f, 1.5f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        } else if (num == 2) {
            sa = new ScaleAnimation(1f, 2.0f, 1f, 2.0f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        } else if (num == 3) {
            sa = new ScaleAnimation(1f, 2.5f, 1f, 2.5f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        }

        if (sa != null) {
            sa.setDuration(100);
            sa.setRepeatCount(0);// 设置循环
        }
        AlphaAnimation aniAlp = new AlphaAnimation(1, 0.1f);
        aniAlp.setRepeatCount(0);// 设置循环
        as.setDuration(ANIMATIONEACHOFFSET * 3);
        as.addAnimation(sa);
        sa.setFillAfter(true);
        as.addAnimation(aniAlp);
        return as;
    }

    /**
     * 移除所有动画
     */
    private void removeAllAnim() {
        shou.clearAnimation();
        imageview1.clearAnimation();
        imageview2.clearAnimation();
        imageview3.clearAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
