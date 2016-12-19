package com.taolu.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huewu.pla.lib.MultiColumnListView;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.taolu.shop.R;
import com.taolu.shop.adapter.SearchShowHorizontalAdapter;
import com.taolu.shop.adapter.SearchShowVerticalAdapter;
import com.taolu.shop.bean.SearchShow;
import com.taolu.shop.global.Api;
import com.taolu.shop.global.RedBabyApplication;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/11/23.
 * 商品展示详情界面
 */
public class SearchShowActivity extends Activity implements HttpLoader.HttpListener {

    private PullToRefreshListView mPullToRefreshListView;
    private ListView lv_reach_listview;
    private MultiColumnListView lv_reach_gridview;
    private RadioGroup mRg_tab_menu;
    private List<SearchShow.ProductListBean> mProductList;
    boolean isShow = true;
    private ImageButton mButton;
    private SearchShowHorizontalAdapter mSearchHorizontalAdapter;
    private SearchShowVerticalAdapter mShowVerticalAdapter;
    private EditText et_search_data; //搜索框
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_category);
        initView();
        initData();
    }

    private void initView() {
        lv_reach_listview = (ListView) findViewById(R.id.lv_reach_listview);
        lv_reach_gridview = (MultiColumnListView) findViewById(R.id.lv_reach_gridview);
        mRg_tab_menu = (RadioGroup) findViewById(R.id.rg_tab_menu);
        mButton = (ImageButton) findViewById(R.id.ib_change);
        findViewById(R.id.rbtn_one);
        findViewById(R.id.rbtn_two);
        findViewById(R.id.rbtn_three);
        findViewById(R.id.rbtn_four);
        findViewById(R.id.rbtn_five);
        //切换视图显示
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    lv_reach_listview.setVisibility(View.INVISIBLE);
                    lv_reach_gridview.setVisibility(View.VISIBLE);
                    mButton.setImageResource(R.drawable.search_zutu2);
                    isShow = false;
                } else {
                    lv_reach_listview.setVisibility(View.VISIBLE);
                    lv_reach_gridview.setVisibility(View.INVISIBLE);
                    mButton.setImageResource(R.drawable.search_zutu1);
                    isShow = true;
                }
            }
        });
        //返回搜索页面
        findViewById(R.id.ib_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        //点击搜索框进行搜索所搜页面
/*        findViewById(R.id.et_search_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        //发送数据 list
        lv_reach_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchShowActivity.this, BabyProductActivity.class);
                intent.putExtra("id", mProductList.get(position).getId());
                intent.putExtra("AcitivityName","SearchShowActivity");
                startActivity(intent);
                finish();

            }
        });
        lv_reach_gridview.setOnItemClickListener(new PLA_AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchShowActivity.this, BabyProductActivity.class);
                intent.putExtra("id", mProductList.get(position).getId());
                startActivity(intent);
                finish();
            }
        });

        mRg_tab_menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_one:  //销量
                        HttpParams params = new HttpParams();
                        params.put("keyword", "新款");
                        params.put("page", "1");
                        params.put("pageNum", "20");
                        params.put("orderby", "saleDown");
                        HttpLoader.getInstance(RedBabyApplication.context).get(Api.URL_SEARCH_LIST, params,
                                SearchShow.class, 400, SearchShowActivity.this, true);
                        break;
                    case R.id.rbtn_two: //降序
                        HttpParams params1 = new HttpParams();
                        params1.put("keyword", "新款");
                        params1.put("page", "1");
                        params1.put("pageNum", "20");
                        params1.put("orderby", "priceDown");
                        HttpLoader.getInstance(RedBabyApplication.context).get(Api.URL_SEARCH_LIST, params1,
                                SearchShow.class, 401, SearchShowActivity.this, true);

                        break;
                    case R.id.rbtn_three: //升序
                        HttpParams params2 = new HttpParams();
                        params2.put("keyword", "新款");
                        params2.put("page", "1");
                        params2.put("pageNum", "20");
                        params2.put("orderby", "priceUp");
                        HttpLoader.getInstance(RedBabyApplication.context).get(Api.URL_SEARCH_LIST, params2,
                                SearchShow.class, 402, SearchShowActivity.this, true);

                        break;
                    case R.id.rbtn_four: //好评
                        HttpParams params3 = new HttpParams();
                        params3.put("keyword", "新款");
                        params3.put("page", "1");
                        params3.put("pageNum", "20");
                        params3.put("orderby", "saleDown");
                        HttpLoader.getInstance(RedBabyApplication.context).get(Api.URL_SEARCH_LIST, params3,
                                SearchShow.class, 403, SearchShowActivity.this, true);

                        break;
                    case R.id.rbtn_five: // 时间
                        HttpParams params4 = new HttpParams();
                        params4.put("keyword", "新款");
                        params4.put("page", "1");
                        params4.put("pageNum", "20");
                        params4.put("orderby", "shelvesDown");
                        HttpLoader.getInstance(RedBabyApplication.context).get(Api.URL_SEARCH_LIST, params4,
                                SearchShow.class, 404, SearchShowActivity.this, true);

                        break;
                }
            }
        });
    }

    /**
     * 请求数据
     */
    private void initData() {
        HttpParams params = new HttpParams();
        params.put("keyword", "新款");
        params.put("page", 1 + "");
        params.put("pageNum", "20");
        params.put("orderby", "saleDown");
        HttpLoader.getInstance(this).get(Api.URL_SEARCH_LIST, params,
                SearchShow.class, 400, this, true);

    }


    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        SearchShow searchShow = (SearchShow) response;
        mProductList = searchShow.getProductList();
        mSearchHorizontalAdapter = new SearchShowHorizontalAdapter((ArrayList) mProductList);
        mShowVerticalAdapter = new SearchShowVerticalAdapter((ArrayList) mProductList);
        switch (requestCode) {
            case 400:
                lv_reach_listview.setAdapter(mSearchHorizontalAdapter);
                lv_reach_gridview.setAdapter(mShowVerticalAdapter);
                mSearchHorizontalAdapter.notifyDataSetChanged();
                break;

            case 401:
                lv_reach_listview.setAdapter(mSearchHorizontalAdapter);
                lv_reach_gridview.setAdapter(mShowVerticalAdapter);
                mSearchHorizontalAdapter.notifyDataSetChanged();
                break;

            case 402:
                lv_reach_listview.setAdapter(mSearchHorizontalAdapter);
                lv_reach_gridview.setAdapter(mShowVerticalAdapter);
                mSearchHorizontalAdapter.notifyDataSetChanged();
                break;

            case 403:
                lv_reach_listview.setAdapter(mSearchHorizontalAdapter);
                lv_reach_gridview.setAdapter(mShowVerticalAdapter);
                mSearchHorizontalAdapter.notifyDataSetChanged();
                break;

            case 404:
                lv_reach_listview.setAdapter(mSearchHorizontalAdapter);
                lv_reach_gridview.setAdapter(mShowVerticalAdapter);
                mSearchHorizontalAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }
}