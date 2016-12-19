package com.taolu.shop.adapter;

import com.taolu.shop.activity.PayMentActivity;
import com.taolu.shop.bean.CheckouyParmBeen;
import com.taolu.shop.bean.PaymentResponse;
import com.taolu.shop.bean.Paymentbean;
import com.taolu.shop.bean.payMoneyStyle;
import com.taolu.shop.holder.AddressInfoHolder;
import com.taolu.shop.holder.BaseHolder;
import com.taolu.shop.holder.CheckoutAddupHolder;
import com.taolu.shop.holder.CheckoutPromHolder;
import com.taolu.shop.holder.DeliveryListHolder;
import com.taolu.shop.holder.paymentListHolder;
import com.taolu.shop.holder.productListHolder;
import com.taolu.shop.utils.LogUtil;
import com.taolu.shop.view.SwipeLayout;

import java.util.ArrayList;

/**
 * Created by ${ghx} on 2016/11/23 0023.
 */
public class PayMentAdapter extends BasicAdapter<Object> {

    public final int ITEM_addressInfo = 0; //地址信息
    public final int ITEM_productList = 1; //商品集合
    public final int ITEM_checkoutAddup = 2;//购买商品总数
    public final int ITEM_checkoutProm = 3;//促销信息
    public final int ITEM_deliveryList = 4;//发货时间
    public final int ITEM_paymentList = 5;//支付信息
     public  PayMentActivity  payMentActivity;
    private ArrayList<SwipeLayout> openedItems=new ArrayList<>();

    public PayMentAdapter(ArrayList<Object> list, PayMentActivity payMentActivity) {
        super(list);
        this.payMentActivity=payMentActivity;


    }

    @Override
    protected BaseHolder<Object> getHolder(final int position) {
        BaseHolder holder = null;
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case ITEM_addressInfo:
                holder=new AddressInfoHolder();

                break;
            case ITEM_productList:
                LogUtil.e("aaa",payMentActivity+"有值吗");
                holder=new productListHolder(list,position,this,payMentActivity);
                SwipeLayout sl = (SwipeLayout) holder.getHolderView();

                sl.setOnSwipeListener(new SwipeLayout.OnSwipeListener() {
                    @Override
                    public void onClose(SwipeLayout layout) {
                        openedItems.remove(layout);
                    }

                    @Override
                    public void onOpen(SwipeLayout layout) {
                        openedItems.add(layout);
                    }

                    @Override
                    public void onStartOpen(SwipeLayout layout) {
                        // 关闭所有已经打开的条目
                        for (int i = 0; i < openedItems.size(); i++) {
                            openedItems.get(i).close(true);
                        }

                        openedItems.clear();
                    }

                    @Override
                    public void onStartClose(SwipeLayout layout) {

                    }

                });
                break;
            case ITEM_checkoutAddup:
                holder=new CheckoutAddupHolder();
                break;
            case ITEM_checkoutProm:
                holder=new CheckoutPromHolder();
                break;
           case ITEM_deliveryList:

                holder=new DeliveryListHolder();
                break;
            case ITEM_paymentList:
                holder=new paymentListHolder();
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        Object object = list.get(position);
        if (object instanceof PaymentResponse.AddressInfoBean) {
            return ITEM_addressInfo;
        } else if (object instanceof PaymentResponse.ProductListBean) {
            return ITEM_productList;
        } else if (object instanceof PaymentResponse.CheckoutAddupBean) {
            return ITEM_checkoutAddup;
        }else if(object instanceof CheckouyParmBeen){
            return ITEM_checkoutProm;
        }else if(object instanceof Paymentbean){
            return ITEM_deliveryList;
        }else if(object instanceof payMoneyStyle){
            return ITEM_paymentList;
        }
        return super.getItemViewType(position);
    }


    @Override
    public int getViewTypeCount() {
        return 6;
    }

    public void setonnotifaychanged(ArrayList<Object> objects) {
     this.list=objects;
        notifyDataSetChanged();
    }
}
