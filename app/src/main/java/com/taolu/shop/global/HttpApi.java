package com.taolu.shop.global;

/**
 * Created by yy on 2016/11/22.
 */
public class HttpApi {
    public static final String URL_Service = Api.URL_SERVER;
    public static final String URL_HOME = URL_Service + "/home";
    public static final String URL_BAND = URL_Service + "/brand";//推荐品牌
    public static final String URL_SALES = URL_Service + "/topic";//促销快报
    public static final String URL_NEWPRODUCT = URL_Service + "/newproduct";//新品上架
    public static final String URL_LIMITBUY = URL_Service + "/limitbuy";//限时抢购
    public static final String URL_HOT = URL_Service + "/hotproduct";//热门单品
    //详情页的假数据
    public static final String URL_PRODCT = URL_Service + "/product";
    //   /product/comment?pId=9&page=1&pageNum=5
    public static final String URL_Commit = URL_Service + "/product/comment";
}
