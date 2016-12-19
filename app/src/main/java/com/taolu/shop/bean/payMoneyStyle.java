package com.taolu.shop.bean;

import java.util.List;

/**
 * Created by ${ghx} on 2016/11/24 0024.
 */
public class payMoneyStyle {

    List<PaymentResponse.PaymentListBean> list;
   public String defaultstyle="post 机付款";

    public List<PaymentResponse.PaymentListBean> getList() {
        return list;
    }

    public void setList(List<PaymentResponse.PaymentListBean> list) {
        this.list = list;
    }

    public String getDefaultstyle() {
        return defaultstyle;
    }

    public void setDefaultstyle(String defaultstyle) {
        this.defaultstyle = defaultstyle;
    }
}
