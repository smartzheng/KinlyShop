package com.taolu.shop.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.taolu.shop.R;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.PrefUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by thinkpad on 2016/11/24.
 */
public class PersonSettingActivity extends AppCompatActivity implements View.OnClickListener, HttpLoader.HttpListener {
    @InjectView(R.id.iv_person_setting_cancel)
    ImageView mIvPersonSettingCancel;
    @InjectView(R.id.btn_person_setting_exit)
    Button mBtnPersonSettingExit;
    @InjectView(R.id.person_setting_account)
    TextView tvAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_setting);
        ButterKnife.inject(this);
        mIvPersonSettingCancel.setOnClickListener(this);
        mBtnPersonSettingExit.setOnClickListener(this);
        String userName = PrefUtils.getString(RedBabyApplication.context, "userName", "");
        if (!TextUtils.isEmpty(userName)) {
            tvAccount.setText(userName);
        }else{
            mBtnPersonSettingExit.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_person_setting_cancel:
                finish();
                break;
            case R.id.btn_person_setting_exit:
                //退出登录,弹出dialog,取消保留在此界面,确认,返回到个人中心界面
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("您确定要退出账号吗");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PrefUtils.putString(RedBabyApplication.context, "userId", "");
                        PrefUtils.putString(RedBabyApplication.context, "userName", "");
                        finish();
                    }
                });
                builder.show();
                break;
        }
    }

    /**
     * 当访问服务器成功调用的方法
     *
     * @param requestCode response对应的requestCode
     * @param response    返回的response
     */
    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {

    }

    /**
     * 当访问服务器失败调用的方法
     *
     * @param requestCode 请求码
     * @param error       异常详情
     */
    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }

}
