package com.taolu.shop.adapter;

import com.taolu.shop.bean.PaymentResponse;
import com.taolu.shop.bean.comdetailBean;
import com.taolu.shop.holder.BaseHolder;
import com.taolu.shop.holder.CheckoutAddupHolder;
import com.taolu.shop.holder.comAdressinfo;
import com.taolu.shop.holder.detailedinformationHolder;
import com.taolu.shop.holder.productinfoHolder;

import java.util.ArrayList;

/**
 * Created by ${ghx} on 2016/11/25 0025.
 */
public class CommodProductAdapter extends BasicAdapter<Object> {
    public final int ITEM_addressInfow = 0; //地址信息
    public final int ITEM_productListw = 1; //商品集合
    public final int ITEM_checkoutAddupw = 2;//购买商品总数
    public final int ITEM_comdetailBeanw = 3;//支付信息



    public CommodProductAdapter(ArrayList<Object> list) {
        super(list);
    }

    @Override
    protected BaseHolder<Object> getHolder(int position) {

        BaseHolder holder = null;
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case ITEM_addressInfow:
                holder=new comAdressinfo();
                break;
            case ITEM_productListw:
                holder=new productinfoHolder(position);
                break;
            case ITEM_checkoutAddupw:

                holder=new CheckoutAddupHolder();

                break;

            case ITEM_comdetailBeanw:
               // ProductListBean--.bean.comdetailBean
                holder=new detailedinformationHolder();
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {

        Object object = list.get(position);
        if (object instanceof PaymentResponse.AddressInfoBean) {

            return ITEM_addressInfow;

        } else if (object instanceof PaymentResponse.ProductListBean) {

            return ITEM_productListw;
        }

        else if (object instanceof PaymentResponse.CheckoutAddupBean) {

            return ITEM_checkoutAddupw;

        }
        else if(object instanceof comdetailBean){

            return ITEM_comdetailBeanw;
        }
        return super.getItemViewType(position);



    }
    public void setonnotifaychanged(ArrayList<Object> objects) {
        this.list=objects;
        notifyDataSetChanged();



    }

    @Override
    public int getViewTypeCount() {

        return 4;
    }
}
