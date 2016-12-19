package com.taolu.shop.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.taolu.shop.R;
import com.taolu.shop.adapter.HotAdapter;
import com.taolu.shop.bean.HotBean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.HttpCode;
import com.taolu.shop.utils.CommonUtil;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;

public class HotActivity extends BaseActivity {
    private ArrayList mProductList;
    private HotAdapter mPanicAdapter;
    private HotBean panicBean;
    private int i = 6;
    private int n = 1;
    @Override
    public void getDada() {
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", i + "").put("orderby", "saleDown");
        HttpLoader.getInstance(this).get(HttpApi.URL_HOT, params,
                HotBean.class, HttpCode.CODE_Recommend, new BandListener());

    }

    class BandListener implements HttpLoader.HttpListener {

        @Override
        public void onGetResponseSuccess(int requestCode, IResponse response) {
            if(response!=null){
                panicBean = (HotBean) response;
                CommonUtil.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        int id = panicBean.getProductList().get(0).getId();

                        mProductList = (ArrayList) panicBean.getProductList();
                        mPanicAdapter = new HotAdapter(mProductList);
                        mPanicAdapter.setAcyivity(HotActivity.this);
                        mListView.setAdapter(mPanicAdapter);
                        i++;
                        n++;
                        mPanicAdapter.notifyDataSetChanged();
                        mPullRefreshListView.onRefreshComplete();
                    }
                });
            }
            }

        @Override
        public void onGetResponseError(int requestCode, VolleyError error) {

        }
    }

    @Override
    public void setItmeListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HotActivity.this, BabyProductActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", panicBean.getProductList().get(position-2).getId());
                intent.putExtra("AcitivityName","HotActivity");
                HotActivity.this.startActivity(intent);
                finish();
                overridePendingTransition(R.anim.startactivity,R.anim.outactivity);

            }
        });
    }
}

