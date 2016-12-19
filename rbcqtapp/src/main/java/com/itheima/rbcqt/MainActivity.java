package com.itheima.rbcqt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.rbcqt.activity.TopicActivity;
import com.itheima.rbcqt.bean.LoginResponse;
import com.itheima.rbcqt.global.Api;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements HttpLoader.HttpListener {

    @InjectView(R.id.btn_topic)
    Button mBtnTopic;
    @InjectView(R.id.btn_login)
    Button mBtnLogin;
    @InjectView(R.id.btn_address)
    Button mBtnAddress;

    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }


    @OnClick({R.id.btn_topic, R.id.btn_login, R.id.btn_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_topic:


                /*
                    get请求示例
                 */
                startActivity(new Intent(this, TopicActivity.class));
                break;
            case R.id.btn_login:

                /*
                     获取Userid
                 */
                HttpParams params = new HttpParams();
                params.put("username", "test").put("password", "test");
                App.mLoader.post(Api.URL_LOGIN, params, LoginResponse.class,
                        Api.REQUEST_CODE_LOGIN, this).setTag(this);

                break;
            case R.id.btn_address:

                /*
                     如何添加请求头
                 */
                HttpParams params1 = new HttpParams();
                params1.addHeader("userid", userid);
                HttpLoader.getInstance(this).get(Api.URL_ADDRESSLIST, params1, null,
                        Api.REQUEST_CODE_ADDRESSLIST, this).setTag(this);

                break;
        }
    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        switch (requestCode) {
            case Api.REQUEST_CODE_LOGIN:
                LoginResponse loginResponse = (LoginResponse) response;
                userid = loginResponse.userInfo.userid;
                Toast.makeText(this, "userid = " + userid, Toast.LENGTH_SHORT).show();
                break;
            case Api.REQUEST_CODE_ADDRESSLIST:
                //......
                break;
        }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }

    @Override
    protected void onDestroy() {
        App.mLoader.cancelRequest(this);
        super.onDestroy();
    }
}
