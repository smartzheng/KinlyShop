package com.taolu.shop.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.taolu.shop.R;
import com.taolu.shop.bean.HelpInfo;
import com.taolu.shop.global.Api;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 帮助中心activity
 */
public class HelpActivity extends AppCompatActivity implements View.OnClickListener {


    @InjectView(R.id.tv_help_center_gwzl)
    TextView mTvHelpCenterGwzl;
    @InjectView(R.id.rl_help_center_gwzl)
    RelativeLayout mRlHelpCenterGwzl;
    @InjectView(R.id.tv_help_center_shfw)
    TextView mTvHelpCenterShfw;
    @InjectView(R.id.rl_help_center_shfw)
    RelativeLayout mRlHelpCenterShfw;
    @InjectView(R.id.tv_help_center_psfs)
    TextView mTvHelpCenterPsfs;
    @InjectView(R.id.rl_help_center_psfs)
    RelativeLayout mRlHelpCenterPsfs;
    @InjectView(R.id.btn_help_center_phone)
    Button mBtnHelpCenterPhone;
    @InjectView(R.id.iv_help_center_cancel)
    ImageView mIvHelpCenterCancel;
    @InjectView(R.id.tv_help_center_cpgx)
    TextView mTvHelpCenterCpgx;
    @InjectView(R.id.rl_help_center_cpzx)
    RelativeLayout mRlHelpCenterCpzx;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_help_center);
        ButterKnife.inject(this);
        mRlHelpCenterGwzl.setOnClickListener(this);
        mIvHelpCenterCancel.setOnClickListener(this);
        mBtnHelpCenterPhone.setOnClickListener(this);
        initData();
    }

    /**
     * 从网络获取帮助列表信息
     */
    private void initData() {
        MyHelpListener listener = new MyHelpListener();
        HttpLoader.getInstance(this).get(Api.URL_help, new HttpParams(), HelpInfo.class, Api.REQUEST_CODE_help, listener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_help_center_cancel:
                finish();
                break;
            case R.id.rl_help_center_gwzl:
                Intent intent = new Intent(this, ShoppingGuideActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_help_center_phone:
                //一键呼叫摇一摇
                Intent intentPhone = new Intent();
                intentPhone.setAction(Intent.ACTION_DIAL);
                intentPhone.setData(Uri.parse("tel://13012345678"));
                startActivity(intentPhone);
                break;
        }
    }

    class MyHelpListener implements HttpLoader.HttpListener {

        @Override
        public void onGetResponseSuccess(int requestCode, IResponse response) {
            if (response != null && response instanceof HelpInfo) {
                HelpInfo infos = (HelpInfo) response;
                List<HelpInfo.HelpListBean> helpList = infos.getHelpList();

            }

        }

        @Override
        public void onGetResponseError(int requestCode, VolleyError error) {

        }
    }
}
