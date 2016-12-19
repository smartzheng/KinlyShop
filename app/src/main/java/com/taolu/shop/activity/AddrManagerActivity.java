package com.taolu.shop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.taolu.shop.R;

import butterknife.InjectView;

/**
 * Created by thinkpad on 2016/11/26.
 */
public class AddrManagerActivity extends AppCompatActivity {
    @InjectView(R.id.lv_addrs_manager)
    ListView mLvAddrsManager;
    @InjectView(R.id.btn_addrs)
    Button mBtnAddrs;
    //MyAddrsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addrs_manager_activity);
       /* initData();
        ButterKnife.inject(this);
        adapter = new MyAddrsAdapter();
        mLvAddrsManager.setAdapter(adapter);*/
    }

   /* *//**
     * 从网络获取
     *//*
    private void initData() {
        MyAddrsListListener listener = new MyAddrsListListener();
        HttpParams params = new HttpParams();
        String userid = PrefUtils.getString(this, "userId", "");
        params.addHeader("userid", userid);
        HttpLoader.getInstance(this).get(Api.URL_addresslist, params, AddsListInfo.class, Api.REQUEST_CODE_addresslist, listener);
    }

    class MyAddrsListListener implements HttpLoader.HttpListener {

        @Override
        public void onGetResponseSuccess(int requestCode, IResponse response) {
          *//*  if (response != null && response instanceof AddsListInfo) {
                AddsListInfo infos = (AddsListInfo) response;
            }*//*
        }

        @Override
        public void onGetResponseError(int requestCode, VolleyError error) {

        }
    }

    class MyAddrsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }*/
}
