package com.taolu.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.VolleyError;
import com.huewu.pla.lib.MultiColumnListView;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.taolu.shop.R;
import com.taolu.shop.adapter.SalesAdapter;
import com.taolu.shop.bean.SalesBean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.HttpCode;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;

public class SalesActivity extends AppCompatActivity {

    private MultiColumnListView mSales_listview;
    private SalesBean mSalebean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        initView();
        initData();
    }
    private void initView() {
        mSales_listview = (MultiColumnListView) findViewById(R.id.sales_listview);
        findViewById(R.id.home_title_hot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.iv_serch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RedBabyApplication.mViewPager != null) {
                    RedBabyApplication.mViewPager.setCurrentItem(1);
                   finish();
                }
            }
        });
       //DADO:传递数据
        mSales_listview.setOnItemClickListener(new PLA_AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RedBabyApplication.context, BabyProductActivity.class);
                intent.putExtra("AcitivityName","SalesActivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",mSalebean.topic.get(position).id);
                RedBabyApplication.context.startActivity(intent);
                finish();
                SalesActivity.this.overridePendingTransition(R.anim.startactivity,R.anim.outactivity);
            }
        });

    }
    private void initData() {
        //mSales_listview.setAdapter();
        //请求数据
        HttpParams parrams = new HttpParams();
        parrams.put("page","1").put("pageNum","10");
        HttpLoader.getInstance(this).get(HttpApi.URL_SALES,parrams,
                SalesBean.class, HttpCode.CODE_SALES,new SalesListener(),true);
    }
    class SalesListener implements HttpLoader.HttpListener{

        @Override
        public void onGetResponseSuccess(int requestCode, IResponse response) {
            if(response!=null){
                mSalebean = (SalesBean) response;
                // System.out.println(salebean.response+"1111111111111111111111111111111111");
            }
            mSales_listview.setAdapter(new SalesAdapter((ArrayList) mSalebean.topic));

        }

        @Override
        public void onGetResponseError(int requestCode, VolleyError error) {

        }
    }

}
