package com.taolu.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.taolu.shop.R;
import com.taolu.shop.adapter.CategoryAdapter2;
import com.taolu.shop.bean.CategoryResponse;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by RainLi on 2016/11/25.
 */
public class ThirdCategoryActivity extends Activity {
    private ListView lv_goodslist;
    private ArrayList<CategoryResponse.Category> mList;//传递过来的
    private ImageButton ib_categorylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_goodslist);

        mList = (ArrayList<CategoryResponse.Category>)getIntent().getSerializableExtra("childForSecondCategoryList");
        for (CategoryResponse.Category currentCategory : mList) {
            //LogUtil.e("===============", "===========mList===========" + mList);
        }
        ib_categorylist = (ImageButton) findViewById(R.id.ib_categorylist);
        ib_categorylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_goodslist = (ListView) findViewById(R.id.lv_goodslist);
        CategoryAdapter2 adpter2 = new CategoryAdapter2(RedBabyApplication.context,mList);
        lv_goodslist.setAdapter(adpter2);
        adpter2.notifyDataSetChanged();

        //条目点击事件
        lv_goodslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryResponse.Category currentCategory = mList.get(position);
                LogUtil.e("===============","===========currentCategory==========="+currentCategory.id);
                //将商品id传给BabyProductActivity
                Intent intent = new Intent(ThirdCategoryActivity.this,ProductListActivity.class);
                intent.putExtra("id",currentCategory.id);
                intent.putExtra("intentName","ThirdCategoryActivity");
                startActivity(intent);
                finish();
                ThirdCategoryActivity.this.overridePendingTransition(R.anim.gohome_in,R.anim.gohome_out);
            }
        });
    }
}

