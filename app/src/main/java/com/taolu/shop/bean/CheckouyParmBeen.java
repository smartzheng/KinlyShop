package com.taolu.shop.bean;

import java.util.List;

/**
 * Created by ${ghx} on 2016/11/24 0024.
 */
public class CheckouyParmBeen {
    public   String  deaultitem="可以享受到的优惠活动信息(现金劵 满减)";
    List<String >list;

    public  String getDeaultitem() {
        return deaultitem;
    }

    public  void setDeaultitem(String deaultitem) {
        this.deaultitem = deaultitem;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
