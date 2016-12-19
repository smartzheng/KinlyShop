package com.taolu.shop.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;

/**
 * Created by RainLi on 2016/11/28.
 */
public class ProducListResponse implements IResponse{
    public int listCount;
    public ArrayList<ProducListFilter> listFilter;
    public ArrayList<ProducListInfo> productList;
    public String response;
    public static class ProducListFilter {
        public String key;
        ArrayList<ProducListFilterinfo> valueList;
    }

    public static class ProducListInfo{
        public int commentCount;
        public int id;
        public int marketPrice;
        public String name;
        public String pic;
        public String price;
    }
}
