package com.taolu.shop.activity;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.Attention.Swing;
import com.flyco.dialog.widget.base.BaseDialog;
import com.taolu.shop.R;
import com.taolu.shop.fragment.SearchFragment;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.utils.PrefUtils;

/**
 * Created by ${ZM} on 2016/11/28.
 */
public class DialogShow extends BaseDialog<DialogShow> {

    private TextView tv_save_dialog;
    private TextView tv_clean_dialog;
    private SearchFragment searchFragment;
    public DialogShow(Context context,SearchFragment fragment) {
        super(context);
        this.searchFragment = fragment;
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(RedBabyApplication.context, R.layout.dialog_show, null);
        widthScale(0.85f);
        showAnim(new Swing());
        tv_save_dialog = (TextView) view.findViewById(R.id.tv_save_dialog);

        tv_clean_dialog = (TextView) view.findViewById(R.id.tv_clean_dialog);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        tv_save_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
        tv_clean_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                PrefUtils.putString(RedBabyApplication.context,"searchStr","");
                searchFragment.CreateData();
                Toast.makeText(RedBabyApplication.context, "浏览记录已经清空啦~", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
