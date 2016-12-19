package com.taolu.shop.view.droggridview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.global.RedBabyApplication;
import com.taolu.shop.view.droggridview.depend.DragAdapterInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aspros on 16/3/18.
 */
public class MyAdapter extends BaseAdapter implements DragAdapterInterface {
    private int[] icons = new int[]{R.drawable.icon_0, R.drawable.icon_1, R.drawable.icon_2, R.drawable.icon_3, R.drawable.icon_4
            , R.drawable.icon_5, R.drawable.icon_6, R.drawable.icon_7, R.drawable.icon_8, R.drawable.icon_9};
    private List<Integer> datas = new ArrayList<>();
    private Context context;
    String[] titles = {"收藏宝贝", "收藏内容", "关注", "足迹", "卡券包", "会员中心", "我的小蜜", "我要换钱", "我的通信", "我的快递", "主题换肤", "支付宝","我的圈子","我的分享","问大家","我的评价"};
    public MyAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<Integer> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int bean = datas.get(position);
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(RedBabyApplication.context, R.layout.view_item, null);
            holder.deleteImg = (ImageView) convertView.findViewById(R.id.delete_img);
            holder.iconImg = (ImageView) convertView.findViewById(R.id.icon_img);
            holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.container = convertView.findViewById(R.id.item_container);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.deleteImg.setVisibility(View.GONE);
        holder.iconImg.setImageResource(icons[bean % 10]);

        holder.nameTv.setText(titles[position]);
        holder.container.setBackgroundColor(Color.WHITE);
        return convertView;
    }

    class Holder {
        public ImageView deleteImg;
        public ImageView iconImg;
        public TextView nameTv;
        public View container;
    }

    @Override
    public void reOrder(int startPosition, int endPosition) {
        if (endPosition < datas.size()) {
            Object object = datas.remove(startPosition);
            datas.add(endPosition, (Integer) object);
            notifyDataSetChanged();
        }
    }
}
