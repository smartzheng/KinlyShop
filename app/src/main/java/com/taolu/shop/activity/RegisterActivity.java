package com.taolu.shop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.taolu.shop.R;
import com.taolu.shop.bean.RegisterErrorInfo;
import com.taolu.shop.bean.UserInfoResponse;
import com.taolu.shop.global.Api;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;
import com.taolu.shop.utils.PrefUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

/**
 * Created by thinkpad on 2016/11/23.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTv_register;
    private ImageView mIv_person_register_cancel;
    private ImageView mIv_cancel_register;
    private Button mBtn_register;
    private EditText mEt_register_name;
    private EditText mEt_register_psw;
    private EditText mEt_register_affrim_psw;
    private String mUsername;
    private String mPassword;
    private SharedPreferences mSp;
    private ProgressBar mProgressBar;
    private final int DELAY = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if(what == DELAY) {
                register();
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_register);
        mSp = getSharedPreferences("user", MODE_PRIVATE);
        initViews();

    }

    private void initViews() {
        mIv_cancel_register = (ImageView) findViewById(R.id.iv_person_register_cancel);
        mBtn_register = (Button) findViewById(R.id.btn_person_register);
        mEt_register_affrim_psw = (EditText) findViewById(R.id.et_register_affrim_psw);
        mEt_register_psw = (EditText) findViewById(R.id.et_register_psw);
        mEt_register_name = (EditText) findViewById(R.id.et_register_name);
        mProgressBar = (ProgressBar) findViewById(R.id.register_progressBar);
        mIv_cancel_register.setOnClickListener(this);
        mBtn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_person_register_cancel:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_person_register:
                mUsername = mEt_register_name.getText().toString().trim();
                mPassword = mEt_register_psw.getText().toString().trim();
                String pwd2 = mEt_register_affrim_psw.getText().toString().trim();
                if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword) || TextUtils.isEmpty(pwd2)) {
                    Toast.makeText(this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pwd2.equals(mPassword)) {
                    Toast.makeText(this, "两次密码不相等", Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                delay();
                break;
        }

    }

    private void register() {
        LogUtil.e("--------注册--------","----------");

        //获取数据
        HttpParams params = new HttpParams();
        params.put("username", mUsername);
        params.put("password", mPassword);
        MyListener listener = new MyListener();

        HttpLoader.getInstance(this).setErrorClazz(RegisterErrorInfo.class);

        HttpLoader.getInstance(this).post(Api.URL_register, params,
                UserInfoResponse.class, Api.REQUEST_CODE_register, listener);
    }

    class MyListener implements HttpLoader.HttpListener {

        @Override
        public void onGetResponseSuccess(int requestCode, IResponse response) {
            mProgressBar.setVisibility(View.INVISIBLE);
           // Log.e("AAAA", "---Success---rcode - " + requestCode);
            if (response instanceof RegisterErrorInfo) {
                RegisterErrorInfo errorResponse = (RegisterErrorInfo) response;
                Toast.makeText(RegisterActivity.this, errorResponse.getError(), Toast.LENGTH_SHORT).show();
                return;
            }
            //否者的话,就是没有注册
            UserInfoResponse info = (UserInfoResponse) response;
            String userid = info.userInfo.userid;
            SharedPreferences.Editor edit = mSp.edit();
            PrefUtils.putString(RedBabyApplication.context,"userId",userid);
            PrefUtils.putString(RedBabyApplication.context,"userName",mUsername);
            Toast.makeText(RedBabyApplication.context,"登录成功",Toast.LENGTH_SHORT).show();
            //跳转到个人中心
            finish();

        }

        @Override
        public void onGetResponseError(int requestCode, VolleyError error) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 跳转到个人中心页面,并且已经是登录状态
     */
    private void startLogin() {

    }

    /**
     * 延时操作
     */
    private void delay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                Message msg = Message.obtain();
                msg.what = DELAY;
                handler.sendMessage(msg);
            }
        }).start();
    }
}
