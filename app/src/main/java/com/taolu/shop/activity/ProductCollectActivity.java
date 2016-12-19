package com.taolu.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.taolu.shop.R;
import com.taolu.shop.adapter.MyFavoritesAdapter;
import com.taolu.shop.bean.CollectorBean;
import com.taolu.shop.utils.MySqliteHelper;
import com.taolu.shop.utils.RedBabyDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2016/11/27.
 */
public class ProductCollectActivity extends AppCompatActivity  {

    private LinearLayout mNo_resourse;
    private List<CollectorBean> mAll;
    private ImageView mDelete;
    private MyFavoritesAdapter mMyFavoritesAdapter;
    private ListView mLvFavorites;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_product_collect);
        mLvFavorites = (ListView) findViewById(R.id.lv_favorites);
        //返回
        findViewById(R.id.home_title_hot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mNo_resourse = (LinearLayout) findViewById(R.id.no_resourse);
        mDelete = (ImageView) findViewById(R.id.iv_delete);

        mLvFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductCollectActivity.this,BabyProductActivity.class);
                intent.putExtra("id",Integer.parseInt(mAll.get(position).getNumid()));
                intent.putExtra("AcitivityName","ProductCollectActivity");
                startActivity(intent);
                finish();
            }
        });
        initData();

    }





    private void initData() {
        MySqliteHelper my = new MySqliteHelper(this);
        RedBabyDao dao = new RedBabyDao(this);
        mAll = dao.findAll();
        if(mAll.size()<=0){
            mNo_resourse.setVisibility(View.VISIBLE);
            return;
        }else{
            mMyFavoritesAdapter = new MyFavoritesAdapter((ArrayList) mAll);
            if(mLvFavorites!=null){
                mLvFavorites.setAdapter(mMyFavoritesAdapter);
            }

        }


    }



}
