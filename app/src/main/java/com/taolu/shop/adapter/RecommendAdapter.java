package com.taolu.shop.adapter;

import com.taolu.shop.holder.BaseHolder;
import com.taolu.shop.holder.MotherHolder;
import com.taolu.shop.holder.OtherHolder;

import java.util.ArrayList;

/**
 * Created by yy on 2016/11/23.
 */
public class RecommendAdapter extends BasicAdapter<Object> {
    public static final int MOTHER = 0;
    public static final int OTHER = 1;
    public RecommendAdapter(ArrayList list) {
        super(list);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
        Object o = list.get(position);
        if(o instanceof String){
            return MOTHER;

        }else{
            return OTHER;
        }
        //return super.getItemViewType(position);
    }

    @Override
    protected BaseHolder getHolder(int position) {
        BaseHolder holder = null;
        int type = getItemViewType(position);
       switch (type){
           case MOTHER:
               holder = new MotherHolder();
               break;
           case OTHER:
               holder = new OtherHolder();
               break;
       }
        return holder;
    }
}
