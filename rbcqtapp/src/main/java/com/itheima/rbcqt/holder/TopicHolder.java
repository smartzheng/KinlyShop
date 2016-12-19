package com.itheima.rbcqt.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.rbcqt.R;
import com.itheima.rbcqt.bean.TopicResponse;
import com.itheima.rbcqt.global.Api;

import org.senydevpkg.base.BaseHolder;
import org.senydevpkg.net.HttpLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiongmc on 2016/11/21.
 */
public class TopicHolder extends BaseHolder<TopicResponse.TopicBean> {

    @InjectView(R.id.tv_topic)
    TextView mTvTopic;
    @InjectView(R.id.iv_topic)
    ImageView mIvTopic;

    public TopicHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.item_topic, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void bindData(TopicResponse.TopicBean data) {
        mTvTopic.setText(data.name);
        HttpLoader.getInstance(mContext).display(mIvTopic, Api.URL_SERVER + data.pic,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, 0, 0, ImageView.ScaleType.CENTER_CROP);
    }
}
