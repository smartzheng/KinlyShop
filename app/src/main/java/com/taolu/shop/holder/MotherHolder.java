package com.taolu.shop.holder;

import android.view.View;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.global.RedBabyApplication;

/**
 * Created by yy on 2016/11/23.
 */
public class MotherHolder extends BaseHolder<Object> {

    private TextView mTextView;

    @Override
    public View initHolderView() {
        View view = View.inflate(RedBabyApplication.context, R.layout.recommend_mother_item, null);
        mTextView = (TextView) view.findViewById(R.id.tv_recommend);
        return view;
    }

    @Override
    public void bindData( Object data) {
        String b = (String) data;
        mTextView.setText(b);
    }
}
