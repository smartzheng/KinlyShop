package com.taolu.shop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.taolu.shop.R;
import com.taolu.shop.bean.HelpDetailInfo;
import com.taolu.shop.global.Api;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by thinkpad on 2016/11/26.
 */
public class ShoppingGuideActivity extends AppCompatActivity {
    @InjectView(R.id.iv_new_hand_cancel)
    ImageView mIvNewHandCancel;
    private ListView lv_new_hand;
    private List<HelpDetailInfo.HelpDetailListBean> mHelpDetailList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_guide);
        ButterKnife.inject(this);
        mIvNewHandCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_new_hand = (ListView) findViewById(R.id.lv_new_hand);
        initData();

    }

    /**
     * 从网络获取数据
     */
    private void initData() {
        MyHelpDetailListener listener = new MyHelpDetailListener();
        HttpParams params = new HttpParams();
        params.put("id", "1");
        HttpLoader.getInstance(this).get(Api.URL_helpDetail, params, HelpDetailInfo.class,
                Api.REQUEST_CODE_helpDetail, listener);
    }

    class MyHelpDetailListener implements HttpLoader.HttpListener {


        @Override
        public void onGetResponseSuccess(int requestCode, IResponse response) {
            if (response != null && response instanceof HelpDetailInfo) {
                HelpDetailInfo infos = (HelpDetailInfo) response;
                if (infos != null) {
                    mHelpDetailList = infos.getHelpDetailList();
                    lv_new_hand.setAdapter(new MyNewHandAdapter());
                }
            }
        }

        @Override
        public void onGetResponseError(int requestCode, VolleyError error) {

        }
    }

    class MyNewHandAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(mHelpDetailList!= null) {
                int size = mHelpDetailList.size();
                return size;
            }
           return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NewHandHolder holder;
            if (convertView == null) {
                convertView = View.inflate(ShoppingGuideActivity.this, R.layout.item_new_hand, null);
                holder = new NewHandHolder();
                holder.title = (TextView) convertView.findViewById(R.id.tv_new_hand_title);
                holder.content = (TextView) convertView.findViewById(R.id.tv_new_hand_content);
                convertView.setTag(holder);
            } else {
                holder = (NewHandHolder) convertView.getTag();
            }
            HelpDetailInfo.HelpDetailListBean list = mHelpDetailList.get(position);
            holder.title.setText(list.getTitle());
            holder.content.setText(list.getContent());
            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        class NewHandHolder {
            TextView title;
            TextView content;
        }
    }
}
;