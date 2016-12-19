package com.taolu.shop.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.taolu.shop.R;
import com.taolu.shop.activity.MainActivity;
import com.taolu.shop.activity.ThirdCategoryActivity;
import com.taolu.shop.adapter.CategoryAdapter;
import com.taolu.shop.adapter.CategoryAdapter2;
import com.taolu.shop.bean.CategoryResponse;
import com.taolu.shop.global.Api;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;


public class BrandFragment extends Fragment implements HttpLoader.HttpListener,MainActivity.OnRestartChangeListener{

    private ListView lv_category1;
    private ListView lv_category2;
    private ImageButton ib_search;
    private ImageButton ib_message;
    private ImageButton ib_saomiao;
    private CategoryAdapter2 adpter2;
    private  View currentView;
    private int mLeftPosition;
    private ArrayList<CategoryResponse.Category> firstCategoryList = new ArrayList<>();
    private ArrayList<CategoryResponse.Category> secondCategoryList = new ArrayList<>();
    private ArrayList<CategoryResponse.Category> thirdCategoryList = new ArrayList<>();
    private ArrayList<CategoryResponse.Category> childForFirstCategoryList = new ArrayList<>();
    private ArrayList<CategoryResponse.Category> childForSecondCategoryList = new ArrayList<>();

    private CategoryResponse.Category childForFirstCategory;
    private CategoryResponse.Category childForSecondCategory;
    private ArrayList<CategoryResponse.Category> mCategorys;

    public BrandFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return initView();

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }



    //初始化布局
    @NonNull
    private View initView() {
        View view = View.inflate(RedBabyApplication.context, R.layout.fragment_brand,null);
        lv_category1 = (ListView) view.findViewById(R.id.lv_category1);
        lv_category2 = (ListView) view.findViewById(R.id.lv_category2);
        ib_search = (ImageButton) view.findViewById(R.id.ib_search);
        ib_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RedBabyApplication.mViewPager != null) {
                    RedBabyApplication.mViewPager.setCurrentItem(1);
                }
            }
        });

        ib_message = (ImageButton) view.findViewById(R.id.ib_message);
        ib_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RedBabyApplication.context,"这里是系统发出的推送消息",Toast.LENGTH_SHORT).show();
            }
        });
        ib_saomiao = (ImageButton) view.findViewById(R.id.ib_saomiao);
        ib_saomiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RedBabyApplication.context,"扫描就送三鹿奶",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }



    //获取数据
    private void initData() {
        HttpLoader.getInstance(RedBabyApplication.context).get(Api.URL_CATEGORY, null, CategoryResponse.class,
                Api.REQUEST_CODE_CATEGORY, this, true);
    }
    private int currentPosition;
    //返回成功时回调
    private CategoryAdapter adapter;
    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        if (requestCode == Api.REQUEST_CODE_CATEGORY) {
            CategoryResponse CategoryResponse = (CategoryResponse) response;
            mCategorys = (ArrayList<CategoryResponse.Category>) CategoryResponse.category;
            firstCategoryList.clear();
            secondCategoryList.clear();
            thirdCategoryList.clear();
            for (CategoryResponse.Category data : mCategorys) {

                if (data.isLeafNode == true) {
                    thirdCategoryList.add(data);
                } else {
                    if (data.parentId == 0) {
                        firstCategoryList.add(data);

                    } else {
                        secondCategoryList.add(data);
                    }
                }
            }
            //添加假数据
            for (int i = 0; i < 7 ; i++) {
                CategoryResponse.Category cate = new CategoryResponse.Category();
                if (i == 1){
                    cate.name = "健康妈咪";
                    firstCategoryList.add(cate);
                }
                if (i == 2){
                    cate.name = "婴儿教育";
                    firstCategoryList.add(cate);
                }
                if (i == 3){
                    cate.name = "奶粉专区";
                    firstCategoryList.add(cate);
                }
                if (i == 4){
                    cate.name = "数码电器";
                    firstCategoryList.add(cate);
                }
                if (i == 5){
                    cate.name = "宝贝发饰";
                    firstCategoryList.add(cate);
                }
            }

            adapter = new CategoryAdapter(RedBabyApplication.context,firstCategoryList);
            lv_category1.setAdapter(adapter);

            lv_category1.post(new Runnable() {
                @Override
                public void run() {
                    View childAt = lv_category1.getChildAt(0);
                    if(childAt != null) {
                        currentView = childAt;
                        currentView.setBackgroundColor(Color.TRANSPARENT);
                    }
                    CategoryResponse.Category currentCategory = firstCategoryList.get(0);
                    currentCategory.isChecked = true;
                    currentPosition = 0;
                    adapter.notifyDataSetChanged();
                }
            });

            CategoryResponse.Category currentCategory = firstCategoryList.get(0);
            //LogUtil.e("nihao nihao nihao ","---==="+currentCategory.name);
            childForFirstCategoryList.clear();
            for (CategoryResponse.Category category : secondCategoryList){
                childForFirstCategory = category;
                if (childForFirstCategory.parentId == currentCategory.id){
                    childForFirstCategoryList.add(childForFirstCategory);
                }
            }
            adpter2 = new CategoryAdapter2(RedBabyApplication.context,childForFirstCategoryList);
            lv_category2.setAdapter(adpter2);
            adpter2.notifyDataSetChanged();

            adapter.notifyDataSetChanged();


            //获取item对应的2级分类所有商品childForFirstCategoryList
            lv_category1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    mLeftPosition = position;
                    //LogUtil.e("==========mLeftPosition======== ","---==="+mLeftPosition);
                    if(currentView != null) {
                        currentView.setBackgroundResource(R.drawable.list_item_bg_normal);
                    }
                    view.setBackgroundColor(Color.TRANSPARENT);
                    currentView = view;
                    CategoryResponse.Category currentCategory = firstCategoryList.get(position);
                    CategoryResponse.Category category1 = firstCategoryList.get(currentPosition);
                    category1.isChecked = false;
                    currentCategory.isChecked = true;
                    currentPosition = position;
                    //LogUtil.e("nihao nihao nihao ","---==="+currentCategory.name);
                    childForFirstCategoryList.clear();
                    for (CategoryResponse.Category category : secondCategoryList){
                        childForFirstCategory = category;
                        if (childForFirstCategory.parentId == currentCategory.id){
                            childForFirstCategoryList.add(childForFirstCategory);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    adpter2 = new CategoryAdapter2(RedBabyApplication.context,childForFirstCategoryList);
                    CategoryAdapter2 adpterAll = new CategoryAdapter2(RedBabyApplication.context,mCategorys);

                    if (mLeftPosition >= 5){
                        lv_category2.setAdapter(adpterAll);
                    } else {
                        lv_category2.setAdapter(adpter2);
                    }
                    adpter2.notifyDataSetChanged();
                    adpterAll.notifyDataSetChanged();

                }
            });


            lv_category2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //获取item对应的3级分类的所有商品childForSecondCategoryList
                    //LogUtil.e("==========mLeftPosition======== ","---==="+mLeftPosition);
                    if (mLeftPosition >= 4){
                        Toast.makeText(RedBabyApplication.context,"别点了,没有更多商品数据啦",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    CategoryResponse.Category currentCategory = childForFirstCategoryList.get(position);

                    LogUtil.e("================== ","---==="+currentCategory.name);
                    childForSecondCategoryList.clear();
                    for (CategoryResponse.Category category : thirdCategoryList){
                        childForSecondCategory = category;
                        if (childForSecondCategory.parentId == currentCategory.id){
                            childForSecondCategoryList.add(childForSecondCategory);
                        }
                    }
                    //跳转到商品列表，即3级分类的activity,将三级分类的集合传递过去
                    Intent intent = new Intent(RedBabyApplication.context,ThirdCategoryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("childForSecondCategoryList",childForSecondCategoryList);
                    RedBabyApplication.context.startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.gohome_in,R.anim.gohome_out);
                }
            });
        }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        Toast.makeText(RedBabyApplication.context, "数据获取失败", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onMianUiRestart() {
        initData();
    }
}
