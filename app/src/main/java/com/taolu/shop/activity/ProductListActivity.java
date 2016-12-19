package com.taolu.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;

import com.android.volley.VolleyError;
import com.taolu.shop.R;
import com.taolu.shop.adapter.ProduclistAdapter;
import com.taolu.shop.bean.ProducListResponse;
import com.taolu.shop.global.Api;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;

/**
 * Created by RainLi on 2016/11/28.
 */
public class ProductListActivity extends Activity implements HttpLoader.HttpListener,View.OnClickListener{
    private ListView lv_product;
    private ImageButton ib_goback;
    private ImageButton ib_search;
    private ImageButton ib_filter;
    private RadioButton rb_bt;
    private RadioButton rb_bt1;
    private DrawerLayout drawerlayout;
    private ArrayList<ProducListResponse.ProducListInfo> mProducListInfoList;
    private int mInt;
    private int mId;
    private HttpParams mParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        initView();
        initData();
    }

    private void initView() {
        lv_product = (ListView) findViewById(R.id.lv_product);
        ib_goback = (ImageButton) findViewById(R.id.ib_goback);
        ib_search = (ImageButton) findViewById(R.id.ib_search);
        ib_filter = (ImageButton) findViewById(R.id.ib_filter);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        rb_bt = (RadioButton) findViewById(R.id.rb_bt);
        rb_bt1 = (RadioButton) findViewById(R.id.rb_bt1);

        ib_goback.setOnClickListener(this);
        ib_search.setOnClickListener(this);
        ib_filter.setOnClickListener(this);
        rb_bt.setOnClickListener(this);
        rb_bt1.setOnClickListener(this);
        gotoDetails();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_goback://返回到商品3级分类
                    finish();
                    ProductListActivity.this.overridePendingTransition(R.anim.gohome_in,R.anim.gohome_out);
                break;
            case R.id.ib_search:

                if(RedBabyApplication.mViewPager != null) {
                    RedBabyApplication.mViewPager.setCurrentItem(1);
                }
                finish();
                ProductListActivity.this.overridePendingTransition(R.anim.gohome_in,R.anim.gohome_out);
                break;

            case R.id.ib_filter:
                if (drawerlayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerlayout.closeDrawer(Gravity.RIGHT);
                }else{
                    drawerlayout.openDrawer(Gravity.RIGHT);
                }
                break;

            case R.id.rb_bt :
                drawerlayout.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.rb_bt1 :
                drawerlayout.closeDrawer(Gravity.RIGHT);
                break;
        }
    }

    /**
     * 跳转至商品详情页
     */
    private void gotoDetails() {
        lv_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProducListResponse.ProducListInfo producListInfo = mProducListInfoList.get(position);
                Intent intent = new Intent(ProductListActivity.this,BabyProductActivity.class);
                intent.putExtra("AcitivityName","ProductListActivity");
                intent.putExtra("id",producListInfo.id);
                startActivity(intent);
                finish();
                ProductListActivity.this.overridePendingTransition(R.anim.gohome_in,R.anim.gohome_out);
            }
        });
    }


    private void initData() {
        mParams = new HttpParams();

//        LogUtil.e("===========mId=========",""+mId);
        mParams.put("page", "2").put("pageNum", "20").put("cId",""+142);
        HttpLoader.getInstance(this).get(Api.URL_PRODUCLIST, mParams,ProducListResponse.class,
                Api.REQUEST_CODE_PRODUCLIST, this, true);
    }


    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        if (requestCode == Api.REQUEST_CODE_PRODUCLIST){
            ProducListResponse producListResponse = (ProducListResponse) response;
            mProducListInfoList = producListResponse.productList;
            ProduclistAdapter adapter = new ProduclistAdapter(this, mProducListInfoList);
            lv_product.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }



    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }

}
