package com.taolu.shop.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taolu.shop.R;
import com.taolu.shop.global.RedBabyApplication;


/**
 * Created by yy on 2016/11/22.
 */
public class HomeGridViewAdapter extends BaseAdapter {
    String[] tabs = {"限时抢购","促销快报","新品上架","热门单品","推荐品牌","呵呵呵呵"};
    int[] incos = {R.drawable.bigcar5,R.drawable.bigcar5,R.drawable.bigcar5,R.drawable.bigcar5,R.drawable.bigcar5,
            R.drawable.bigcar5,R.drawable.bigcar5,R.drawable.bigcar5,R.drawable.bigcar5,R.drawable.bigcar5,R.drawable.bigcar5,R.drawable.bigcar5};
    String[] dec = {"大美腿，限时抢购哦","修不好的比亚迪，撞不烂的帕萨帖","不要998，不要99.8，只要9.98，带回家慢慢玩",
    "我不是热门，我只是热门的搬运工","求带走，喜喜喜喜","呜呜呜呜呜uwwuwuuw"};
    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView =  View.inflate(RedBabyApplication.context, R.layout.home_griview_item,null);
            viewHolder = new ViewHolder();
            //viewHolder.mTextView = (TextView) convertView.findViewById(R.id.home_gridview_item_text);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.home_gridview_item_image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder.mTextView.setText(dec[position]);

       viewHolder.mImageView.setImageResource(incos[position]);
       viewHolder.mImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        return convertView;
    }
    class ViewHolder{
        TextView mTextView;
        ImageView mImageView;
    }
}
