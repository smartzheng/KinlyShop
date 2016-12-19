package com.taolu.shop.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.taolu.shop.R;
import com.taolu.shop.adapter.PanicAdapter;
import com.taolu.shop.bean.PanicBean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.HttpCode;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.CommonUtil;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;

public class PanicActivity extends BaseActivity {
    private ArrayList mProductList;
    private PanicAdapter mPanicAdapter;
    private int i = 6;
    private int n = 1;

    @Override
    public void getDada() {
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", i + "");
        HttpLoader.getInstance(this).get(HttpApi.URL_LIMITBUY,
                params, PanicBean.class, HttpCode.CODE_Recommend, new BandListener());

    }

    class BandListener implements HttpLoader.HttpListener {

        @Override
        public void onGetResponseSuccess(int requestCode, IResponse response) {
            if(response!=null){
                final PanicBean panicBean = (PanicBean) response;
                CommonUtil.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mProductList = (ArrayList) panicBean.getProductList();
                        mPanicAdapter = new PanicAdapter(mProductList);
                        mPanicAdapter.setActivity(PanicActivity.this);
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
                Intent intent = new Intent(PanicActivity.this, BabyProductActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PanicBean.ProductListBean productListBean = (PanicBean.ProductListBean) mProductList.get(position - 2);
                intent.putExtra("id", productListBean.getId());
                intent.putExtra("AcitivityName", "Panic");
                RedBabyApplication.context.startActivity(intent);
                finish();
                PanicActivity.this.overridePendingTransition(R.anim.startactivity, R.anim.outactivity);

            }
        });
    }
}


    /*private ListView mListView;
    private ArrayList mProductList;
    private PullToRefreshListView mPullRefreshListView;
    private PanicAdapter mPanicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic);
        initView();
        initData();
    }

    private void initView() {

        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);


    }

    //加载数据
    private void initData() {
        //请求数据
        getDada();
    //  刷新
        //设置初始化模式
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新的逻辑 上啦加载
                if(mPullRefreshListView.getCurrentMode()== PullToRefreshBase.Mode.PULL_FROM_END){
                    getDada();

                }else{//下拉刷新
                    getDada();
                    i=8;
                }
            }
        });
        //获取内部的ListView
        mListView= mPullRefreshListView.getRefreshableView();

    }
    private int i = 6;
    private int n = 1;
    private void getDada() {
        HttpParams params = new HttpParams();
        params.put("page","1").put("pageNum",i+"");
        HttpLoader.getInstance(this).get(HttpApi.URL_LIMITBUY, params,PanicBean.class, HttpCode.CODE_Recommend, new BandListener());
    }


    class BandListener implements HttpLoader.HttpListener {

        @Override
        public void onGetResponseSuccess(int requestCode, IResponse response) {
        final PanicBean panicBean= (PanicBean) response;
            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    mProductList = (ArrayList) panicBean.getProductList();
                    mPanicAdapter = new PanicAdapter(mProductList);
                    mListView.setAdapter(mPanicAdapter);
                    i++;
                    n++;
                    mPanicAdapter.notifyDataSetChanged();
                    mPullRefreshListView.onRefreshComplete();





                }
            });



        }

        @Override
        public void onGetResponseError(int requestCode, VolleyError error) {

        }
    }*/

