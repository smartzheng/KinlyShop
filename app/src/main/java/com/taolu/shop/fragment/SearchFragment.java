package com.taolu.shop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.activity.DialogShow;
import com.taolu.shop.activity.MainActivity;
import com.taolu.shop.activity.SearchShowActivity;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.CommonUtil;
import com.taolu.shop.utils.PrefUtils;
import com.taolu.shop.utils.ToastUtil;
import com.taolu.shop.view.FlowLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * 搜索主界面
 */
public class SearchFragment extends Fragment implements View.OnClickListener,
        MainActivity.OnRestartChangeListener {
    private ImageButton ib_find;
    //搜索对话框
    private EditText et_search_datatt;
    //浏览记录布局
    private LinearLayout llContainer;
    private FlowLayout mFlowLayout;
    private ImageButton ib_delete;
    //推荐搜索
    private LinearLayout frame_data;

    public SearchFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        //搜索点击事件
        ib_find = (ImageButton) view.findViewById(R.id.ib_find);
        ib_find.setOnClickListener(this);
        //历史记录显示框
        llContainer = (LinearLayout) view.findViewById(R.id.ll_container);
        // 删除历史记录
        ib_delete = (ImageButton) view.findViewById(R.id.ib_delete);
        ib_delete.setOnClickListener(this);
        //创建历史记录文本框
        mFlowLayout = new FlowLayout(getActivity());
        llContainer.addView(mFlowLayout);
        Vpadding = CommonUtil.getDimens(R.dimen.dp6);
        Hpadding = CommonUtil.getDimens(R.dimen.dp9);
        //推荐搜索
        frame_data = (LinearLayout) view.findViewById(R.id.frame_data);
        frame_data.setOnClickListener(this);
        //1.设置padding值
        int padding = CommonUtil.getDimens(R.dimen.dp15);
        //搜索框
        et_search_datatt = (EditText) view.findViewById(R.id.et_search_datatt);
        //注册MainActiviy监听回调
        MainActivity mainUi = (MainActivity) getActivity();
        mainUi.addOnRestartChangeListener(this);
        CreateData();
        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_find: //搜索界面
                String trim = et_search_datatt.getText().toString().trim();//获取输入框的值
                saveStr(trim);
                Intent intent = new Intent(RedBabyApplication.context, SearchShowActivity.class);
                intent.putExtra("hello", trim);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                RedBabyApplication.context.startActivity(intent);
                break;
            case R.id.ib_delete: //清除历史记录
                DialogShow dialogShow = new DialogShow(getActivity(), this);
                dialogShow.show();
                break;
            case R.id.frame_data: //推荐搜索
                Intent intent1 = new Intent(RedBabyApplication.context, SearchShowActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                RedBabyApplication.context.startActivity(intent1);
                break;
        }
    }

    @Override
    public void onMianUiRestart() {
        CreateData();
    }

    private ArrayList<String> strs = new ArrayList<>();

    /**
     * 保存浏览记录
     */
    private void saveStr(String str) {

        //如果搜索框中的数据是空,直接返回
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String oldStr = PrefUtils.getString(RedBabyApplication.context, "searchStr", "");
        String newStr = "";
        if (!TextUtils.isEmpty(oldStr)) {
            String[] split = oldStr.split(",");
            for (String s : split) {
                strs.add(s);
            }
            if (strs.contains(str)) {
                return;
            }
            newStr = oldStr + "," + str;
        } else {
            newStr += str;
        }


        PrefUtils.putString(RedBabyApplication.context, "searchStr", newStr);
    }

    private int Vpadding;
    private int Hpadding;

    /**
     * 数据初始化
     */
    public void CreateData() {
        mFlowLayout.removeAllViews();
        strs.clear();
        String Mtext = PrefUtils.getString(RedBabyApplication.context, "searchStr", "");
        if (!TextUtils.isEmpty(Mtext)) {
            String[] split = Mtext.split(",");
            for (String s : split) {
                strs.add(s);
            }
        }

        ShowHistoryData();
    }

    public void ShowHistoryData() {
        for (int i = 0; i < strs.size(); i++) {
            final TextView textView = new TextView(RedBabyApplication.context);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setTextSize(14);
            textView.setGravity(Gravity.CENTER);
            textView.setText(strs.get(i));
            textView.setTextColor(RedBabyApplication.context.getResources().getColor(R.color.search_gray));
            textView.setBackgroundResource(R.drawable.tv_gray_bg);
            textView.setPadding(Hpadding, Vpadding, Hpadding, Vpadding);
            textView.setLayoutParams(params);
            mFlowLayout.addView(textView);
            //设置点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(textView.getText().toString());
                }
            });
        }
    }


}
