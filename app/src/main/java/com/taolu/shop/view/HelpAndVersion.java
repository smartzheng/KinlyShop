package com.taolu.shop.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.flyco.dialog.widget.popup.base.BasePopup;
import com.taolu.shop.R;
import com.taolu.shop.activity.HelpActivity;
import com.taolu.shop.activity.PersonConcerningActivity;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by thinkpad on 2016/11/26.
 */
public class HelpAndVersion extends BasePopup<HelpAndVersion> {
    @InjectView(R.id.ll_person_help_home)
    LinearLayout mLlPersonHelpHome;

    @InjectView(R.id.ll_person_help_help)
    LinearLayout mLlPersonHelpHelp;
    @InjectView(R.id.ll_person_help_guanyu)
    LinearLayout mLlPersonHelpGuanyu;



    public HelpAndVersion(Context context) {
        super(context);
    }

    @Override
    public View onCreatePopupView() {
        View view = View.inflate(getContext(), R.layout.person_help_version, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        mLlPersonHelpHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转回第一个页面
                if (RedBabyApplication.mViewPager != null) {
                    LogUtil.e("-----首页被点击--------", "----------");
                    RedBabyApplication.mViewPager.setCurrentItem(0, true);
                }
                HelpAndVersion.this.dismiss();

            }
        });
        mLlPersonHelpHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到帮助界面
                Intent intent = new Intent(getContext(), HelpActivity.class);
                getContext().startActivity(intent);
                HelpAndVersion.this.dismiss();
            }
        });
        mLlPersonHelpGuanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到关于界面
                Intent intent = new Intent(getContext(), PersonConcerningActivity.class);
                getContext().startActivity(intent);
                HelpAndVersion.this.dismiss();
            }
        });

    }
}

