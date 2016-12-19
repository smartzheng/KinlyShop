package com.taolu.shop.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.taolu.shop.R;
import com.taolu.shop.bean.RegisterErrorInfo;
import com.taolu.shop.bean.UserInfoResponse;
import com.taolu.shop.global.Api;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.PrefUtils;
import com.taolu.shop.view.JellyInterpolator;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

/**
 * Created by thinkpad on 2016/11/23.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener,HttpLoader.HttpListener{

    private TextView mTv_register;
    private ImageView mIv_login_cancel;
    private SharedPreferences mSp;

    private TextView mTvUsername;
    private TextView mTvPassword;
    private String mUsername;
    private String mPassword;
    private TextView mBtnLogin;

    private float mWidth, mHeight;

    private LinearLayout mName, mPsw;

    private View progress;

    private LinearLayout ll_container_login;


    private View mInputLayout;

    //是否支付登录
    private boolean isPay;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if(what == DELAY) {
                login(mUsername,mPassword);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R. layout.activity_login);
        isPay = getIntent().getBooleanExtra("isPay",false);
        mSp = RedBabyApplication.context.getSharedPreferences("user", RedBabyApplication.context.MODE_PRIVATE);
        initView();
    }

    /**
     * 找到控件
     */
    private void initView() {
        mTv_register = (TextView) findViewById(R.id.tv_person_register);
        mIv_login_cancel = (ImageView) findViewById(R.id.iv_person_login_cancel);
        mTvUsername = (TextView) findViewById(R.id.et_login_name);
        mTvPassword = (TextView) findViewById(R.id.et_login_password);
        ll_container_login = (LinearLayout) findViewById(R.id.ll_container_login);
        mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName = (LinearLayout) findViewById(R.id.input_layout_name);
        mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);
        progress.setVisibility(View.INVISIBLE);

        mTv_register.setOnClickListener(this);
        mIv_login_cancel.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

    /**
     * 点击进入注册页面
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_person_register:
                Intent intent = new Intent(RedBabyApplication.context, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_person_login_cancel:
                finish();
                break;
            case R.id.main_btn_login: //点击登录

                mUsername = mTvUsername.getText().toString().trim();
                mPassword = mTvPassword.getText().toString().trim();

                if(TextUtils.isEmpty(mUsername)) {
                   loginAnim(ll_container_login);
                    mTvUsername.setHint("帐号不能为空!");
                    mTvUsername.setHintTextColor(Color.RED);
                    return;
                }

                if(TextUtils.isEmpty(mPassword)) {
                   loginAnim(ll_container_login);
                    mTvPassword.setHint("密码不能为空!");
                    mTvPassword.setHintTextColor(Color.RED);
                    return;
                }

                mWidth = mBtnLogin.getMeasuredWidth();
                mHeight = mBtnLogin.getMeasuredHeight();

                mName.setVisibility(View.INVISIBLE);
                mPsw.setVisibility(View.INVISIBLE);

                inputAnimator(mInputLayout, mWidth, mHeight);

                break;
        }

    }

    /**
     * 帐号,密码登录框动画
     * @param view
     */
    private void loginAnim(View view) {

        TranslateAnimation animation = new TranslateAnimation(0, -10, 0, 0);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(100);
        animation.setRepeatCount(3);
        view.startAnimation(animation);
    }

    /**
     * 登录
     * @param username
     * @param password
     */
    private void login(String username,String password) {

        HttpParams params = new HttpParams();
        params.put("username", username);
        params.put("password", password);
        HttpLoader.getInstance(this).setErrorClazz(RegisterErrorInfo.class);
        HttpLoader.getInstance(this).post(Api.URL_login,params, UserInfoResponse.class,
                Api.REQUEST_CODE_login,this);
    }

    /**
     * 当数据请求成功
     * @param requestCode response对应的requestCode
     * @param response    返回的response
     */
    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        progress.setVisibility(View.INVISIBLE);
        if(response instanceof  UserInfoResponse) {
            UserInfoResponse userinfo = (UserInfoResponse) response;
            if(userinfo != null) {
                String responseStr = userinfo.response;
                Toast.makeText(RedBabyApplication.context,responseStr,Toast.LENGTH_SHORT).show();
                loginSuccess(userinfo);
            }
        }else {
            Toast.makeText(RedBabyApplication.context,"帐号或者密码错误",Toast.LENGTH_SHORT).show();
            inputOutAnimator(mInputLayout, mWidth, mHeight);
        }
    }

    /**
     * 当数据请求失败
     * @param requestCode 请求码
     * @param error       异常详情
     */
    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        progress.setVisibility(View.INVISIBLE);
        mInputLayout.setVisibility(View.VISIBLE);
        inputOutAnimator(mInputLayout, mWidth, mHeight);
        Toast.makeText(RedBabyApplication.context,"网络异常",Toast.LENGTH_SHORT).show();
    }


    /**
     * 延时操作
     */
    private final int DELAY = 1;
    private void delay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                Message msg = new Message();
                msg.what = DELAY;
                handler.sendMessage(msg);
            }
        }).start();
    }

    /**
     * 当登录成功之后
     */
    private void loginSuccess(UserInfoResponse userInfoResponse) {
        UserInfoResponse.UserInfoBean userinfo = userInfoResponse.userInfo;
        String userid = userinfo.userid;
        PrefUtils.putString(RedBabyApplication.context,"userId",userid);
        PrefUtils.putString(RedBabyApplication.context,"userName",mUsername);

        if(isPay) {
            Intent intent = new Intent(RedBabyApplication.context,PayMentActivity.class);
            startActivity(intent);
            finish();
        }else{
            finish();
        }

    }


    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();
        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });


        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(500);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);
                delay();

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

    }
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }

    private void inputOutAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();
        ValueAnimator animator = ValueAnimator.ofFloat(w, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });


        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 0.5f, 1.0f);
        set.setDuration(500);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {

               /* progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);*/
                mInputLayout.setVisibility(View.VISIBLE);
                mName.setVisibility(View.VISIBLE);
                mPsw.setVisibility(View.VISIBLE);
                //delay();

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

    }
}
