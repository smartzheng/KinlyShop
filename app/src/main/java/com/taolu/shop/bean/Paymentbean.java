package com.taolu.shop.bean;

import java.util.List;

/**
 * Created by ${ghx} on 2016/11/24 0024.
 */
public class Paymentbean {
    public   String  deaultitem="送货方式";
  public     List<PaymentResponse.DeliveryListBean>  list;

    public  String getItem() {
        return deaultitem;
    }

    public  void setItem(String item) {
        this.deaultitem = item;
    }

    public  List<PaymentResponse.DeliveryListBean> getList() {
        return list;
    }

    public  void setList(List<PaymentResponse.DeliveryListBean> list) {
        this.list = list;
    }
}




