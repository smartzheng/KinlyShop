package com.taolu.shop.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.utils.PrefUtils;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by thinkpad on 2016/11/24.
 */
public class UserManagerActivity extends AppCompatActivity implements View.OnClickListener {


    @InjectView(R.id.iv_person_register_cancel)
    ImageView mIvPersonRegisterCancel;
    @InjectView(R.id.iv_person_mana_head)
    ImageView mIvPersonManaHead;
    @InjectView(R.id.rl_person_mana_touxiang)
    RelativeLayout mRl_person_mana_touxiang;
    @InjectView(R.id.rl_person_mana_name)
    RelativeLayout mRlPersonManaName;
    @InjectView(R.id.rl_person_mana_alias)
    RelativeLayout mRlPersonManaAlias;
    @InjectView(R.id.rl_person_mana_sexs)
    RelativeLayout mRlPersonManaSexs;
    @InjectView(R.id.rl_person_mana_data)
    RelativeLayout mRlPersonManaData;
    @InjectView(R.id.rl_person_mana_vip)
    RelativeLayout mRlPersonManaVip;
    @InjectView(R.id.rl_person_mana_plus)
    RelativeLayout mRlPersonManaPlus;
    @InjectView(R.id.rl_person_mana_addrs)
    RelativeLayout mRlPersonManaAddrs;
    @InjectView(R.id.rl_person_mana_sex)
    RelativeLayout mRlPersonManaSex;
    @InjectView(R.id.rl_person_mana_safety)
    RelativeLayout mRlPersonManaSafety;
    final int DATE_DIALOG = 1;
    int mYear, mMonth, mDay;
    @InjectView(R.id.tv_person_data)
    TextView mTvPersonData;
    @InjectView(R.id.username)
    TextView mUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_manager_name);
        ButterKnife.inject(this);
        mIvPersonRegisterCancel.setOnClickListener(this);
        mRl_person_mana_touxiang.setOnClickListener(this);
        mRlPersonManaData.setOnClickListener(this);
        String year = PrefUtils.getString(this, "year", "");
        String mouth = PrefUtils.getString(this, "mouth", "");
        String day = PrefUtils.getString(this, "day", "");
        mTvPersonData.setText(year + "年" + mouth + "月" + day + "日");
        String username = PrefUtils.getString(this, "userName", "");
        mUsername.setText(username + "@qq.com");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_person_register_cancel:
                finish();
                break;
            case R.id.rl_person_mana_touxiang:
                initDialog();
                break;
            case R.id.rl_person_mana_data:
                //开启一个选择生日选择器
                showDialog(DATE_DIALOG);
                final Calendar ca = Calendar.getInstance();
                mYear = ca.get(Calendar.YEAR);
                mMonth = ca.get(Calendar.MONTH);
                mDay = ca.get(Calendar.DAY_OF_MONTH);
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        mTvPersonData.setText(new StringBuffer().append(mYear).append("年").append(mMonth + 1).append("月").append(mDay).append("日"));
        PrefUtils.putString(this, "year", mYear + "");
        PrefUtils.putString(this, "mouth", mMonth + 1 + "");
        PrefUtils.putString(this, "day", mDay + "");
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };

    /**
     * 创建Dialog
     */
    protected void initDialog() {
        View view = View.inflate(this, R.layout.item_person_head, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.show();
    }
}
