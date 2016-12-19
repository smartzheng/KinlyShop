package com.taolu.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.taolu.shop.R;
import com.taolu.shop.adapter.RecommendAdapter;
import com.taolu.shop.bean.Bandbean;
import com.taolu.shop.global.HttpApi;
import com.taolu.shop.global.HttpCode;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecommendActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<Object> mList;
    private Bandbean mBand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        initView();
        initData();

    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.recommend_listview);
       ImageView iv = (ImageView) findViewById(R.id.home_title_hot);
        findViewById(R.id.home_title_serch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RedBabyApplication.mViewPager != null) {
                    RedBabyApplication.mViewPager.setCurrentItem(1);
                    finish();
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(RecommendActivity.this,BabyProductActivity.class);
                intent.putExtra("AcitivityName","RecommendActivity");
                Random random = new Random();
                int i = random.nextInt(5)+1;
                intent.putExtra("id",i);
                startActivity(intent);
                finish();
                RecommendActivity.this.overridePendingTransition(R.anim.startactivity,R.anim.outactivity);
            }
        });
        findViewById(R.id.home_title_hot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       /* Jump2.Jump(mImageView);
        finish();*/



    }

    //加载数据
    private void initData() {
        HttpLoader.getInstance(this).get(HttpApi.URL_BAND, null,
                Bandbean.class, HttpCode.CODE_Recommend, new BandListener());

    }


    class BandListener implements HttpLoader.HttpListener {



        @Override
        public void onGetResponseSuccess(int requestCode, IResponse response) {
            if(response!=null){
                //下载成功
                mBand = (Bandbean) response;
            }



            mList = new ArrayList<>();
            //  ArrayList<String> title = new ArrayList<>();

            ArrayList<Bandbean.BrandBean> brand = (ArrayList) mBand.getBrand();



            for (int i = 0; i < brand.size(); i++) {

                List<Bandbean.BrandBean.ValueBean> value = brand.get(i).getValue();
                if(value.size()==0){
                    break;
                }
                mList.add(brand.get(i).getKey());
                for (int j = 0; j < value.size(); j++) {
                   mList.add(value.get(j));
                }

            }


            mListView.setAdapter(new RecommendAdapter(mList));


        }

        @Override
        public void onGetResponseError(int requestCode, VolleyError error) {

        }
    }
}
