package com.taolu.shop.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.taolu.shop.holder.BaseHolder;
import com.taolu.shop.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by ${ghx} on 2016/11/23 0023.
 */
public  abstract class BasicAdapter<T> extends BaseAdapter {
    protected ArrayList<T> list;
    //protected PaymentResponse paymentResponse;

    public BasicAdapter(ArrayList<T> list) {
        super();
        this.list = list;
    }

    public void notifyDataSetChanged(ArrayList<T> newData) {
        this.list = newData;

        notifyDataSetChanged();
    }



    @Override
    public int getCount() {

/*        if(list.get(0)instanceof PaymentResponse.AddressInfoBean){
            //商品的信息的集合
            List<PaymentResponse.ProductListBean> productList= (List<PaymentResponse.ProductListBean>) list.get(1);

            totalsize=totalsize+list.size();
        }*/
LogUtil.e("basicAdapter 集合的大小",list.size()+"..........");
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        BaseHolder<T> holder = null;
        if (convertView == null) {
            holder = getHolder(position);// 需要一个不固定的holder



        } else {
            holder = (BaseHolder) convertView.getTag();
    }
        // 3.绑定数据
        holder.bindData(list.get(position));
        // 4.增加炫酷动画
        // 一开始缩小
        ViewHelper.setScaleX(holder.getHolderView(), 0.5f);
        ViewHelper.setScaleY(holder.getHolderView(), 0.5f);
        // 执行放大动画
        ViewPropertyAnimator.animate(holder.getHolderView()).scaleX(1f).setDuration(400)
                .setInterpolator(new OvershootInterpolator()).start();
        ViewPropertyAnimator.animate(holder.getHolderView()).scaleY(1f).setDuration(400)
                .setInterpolator(new OvershootInterpolator()).start();


        return holder.getHolderView();
    }



    protected abstract BaseHolder<T> getHolder(int position);


}

