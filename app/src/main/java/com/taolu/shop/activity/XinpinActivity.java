package com.taolu.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.android.volley.VolleyError;
import com.huewu.pla.lib.MultiColumnListView;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.taolu.shop.R;
import com.taolu.shop.adapter.XinPinAdapter;
import com.taolu.shop.bean.XinPinBean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;

public class XinpinActivity extends AppCompatActivity implements HttpLoader.HttpListener{

    private MultiColumnListView mMListView;
    private XinPinBean mPanicBean;
    protected RadioGroup mRg_tab_menu;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinpin);
        initView();
        getDada();
        meData();
    }

    private void initView() {
         findViewById(R.id.home_title_hot).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
        findViewById(R.id.home_title_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RedBabyApplication.mViewPager != null) {
                    RedBabyApplication.mViewPager.setCurrentItem(1);
                    finish();
                }
            }
        });
        mMListView = (MultiColumnListView) findViewById(R.id.sales_xinpin);
        mRg_tab_menu = (RadioGroup) findViewById(R.id.rg_tab_menu);
        mMListView.setOnItemClickListener(new PLA_AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RedBabyApplication.context, BabyProductActivity.class);
                intent.putExtra("AcitivityName","XinpinAcyivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",mPanicBean.getProductList().get(position).getId());
                RedBabyApplication.context.startActivity(intent);
                finish();
                XinpinActivity.this .overridePendingTransition(R.anim.startactivity,R.anim.outactivity);
            }
        });
        findViewById(R.id.rbtn_one);
        findViewById(R.id.rbtn_two);
        findViewById(R.id.rbtn_three);
        findViewById(R.id.rbtn_four);
        findViewById(R.id.rbtn_five);

    }


    private ArrayList mProductList;
    private XinPinAdapter mPanicAdapter;



    public void getDada() {
        mRg_tab_menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbtn_one:
                     meData();
                    break;
                    case R.id.rbtn_two:
                        HttpParams param2 = new HttpParams();
                        param2.put("page", "1").put("pageNum", "17").put("orderby", "priceDown");
                        HttpLoader.getInstance(RedBabyApplication.context).get(HttpApi.URL_NEWPRODUCT,
                                param2, XinPinBean.class, 212, XinpinActivity.this);
                    break;
                    case R.id.rbtn_three:

                        HttpParams param1 = new HttpParams();
                        param1.put("page", "1").put("pageNum", "17").put("orderby", "saleDown");
                        HttpLoader.getInstance(RedBabyApplication.context).get(HttpApi.URL_NEWPRODUCT,
                                param1, XinPinBean.class, 211, XinpinActivity.this);
                        break;
                    case R.id.rbtn_four:
                        HttpParams param3 = new HttpParams();
                        param3.put("page", "1").put("pageNum", "17").put("orderby", "priceDown");
                        HttpLoader.getInstance(RedBabyApplication.context).get(HttpApi.URL_NEWPRODUCT,
                                param3, XinPinBean.class, 213, XinpinActivity.this);
                        break;
                    case R.id.rbtn_five:
                        HttpParams param4 = new HttpParams();
                        param4.put("page", "1").put("pageNum", "17").put("orderby", "shelvesDown");
                        HttpLoader.getInstance(RedBabyApplication.context).get(HttpApi.URL_NEWPRODUCT,
                                param4, XinPinBean.class, 212, XinpinActivity.this);
                        break;
                }
            }
        });



    }

    private void meData() {
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", "17").put("orderby", "priceUp");
        HttpLoader.getInstance(RedBabyApplication.context).get(HttpApi.URL_NEWPRODUCT,
                params, XinPinBean.class, 210, XinpinActivity.this);
    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        mPanicBean = (XinPinBean) response;

        mProductList = (ArrayList) mPanicBean.getProductList();


        switch (requestCode){
            case 210:

                mPanicAdapter = new XinPinAdapter(mProductList);
                mMListView.setAdapter(mPanicAdapter);
                mPanicAdapter.notifyDataSetChanged();
            break;
            case 211:
                mPanicAdapter = new XinPinAdapter(mProductList);
                mMListView.setAdapter(mPanicAdapter);
                mPanicAdapter.notifyDataSetChanged();
                break;
            case 212:
            mPanicAdapter = new XinPinAdapter(mProductList);
            mMListView.setAdapter(mPanicAdapter);
            mPanicAdapter.notifyDataSetChanged();
            break;
            case 213:
                mPanicAdapter = new XinPinAdapter(mProductList);
                mMListView.setAdapter(mPanicAdapter);
                mPanicAdapter.notifyDataSetChanged();
                break;
            case 214:
                mPanicAdapter = new XinPinAdapter(mProductList);
                mMListView.setAdapter(mPanicAdapter);
                mPanicAdapter.notifyDataSetChanged();
                break;

        }

    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }




}
