package com.itheima.rbcqt.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.rbcqt.R;
import com.itheima.rbcqt.adapter.TopicAdapter;
import com.itheima.rbcqt.bean.TopicResponse;
import com.itheima.rbcqt.global.Api;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TopicActivity extends Activity implements HttpLoader.HttpListener {

    @InjectView(R.id.lv_topic)
    ListView mLvTopic;
    private TopicAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.inject(this);
        initViews();
        initData();
    }

    private void initData() {
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", "8");
        HttpLoader.getInstance(this).get(Api.URL_TOPIC, params, TopicResponse.class,
                Api.REQUEST_CODE_TOPIC, this, true);
    }

    private void initViews() {

    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        TopicResponse topicResponse = (TopicResponse) response;

        if (mAdapter == null) {
            mAdapter = new TopicAdapter(this, topicResponse.topic);
            mLvTopic.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged(topicResponse.topic);
        }

    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        Toast.makeText(TopicActivity.this, "error", Toast.LENGTH_SHORT).show();
    }
}
